#!/usr/bin/env python3
"""Concurrent HTTP load test for the OmniCloud REST API."""

from __future__ import annotations

import argparse
import json
import statistics
import time
from concurrent.futures import ThreadPoolExecutor, as_completed
from urllib.error import HTTPError, URLError
from urllib.request import Request, urlopen


def request(method: str, url: str, payload: dict | None = None) -> tuple[float, int]:
    body = None
    headers = {}
    if payload is not None:
        body = json.dumps(payload).encode("utf-8")
        headers["Content-Type"] = "application/json"

    started = time.perf_counter()
    try:
        with urlopen(
            Request(url, data=body, headers=headers, method=method),
            timeout=10,
        ) as response:
            response.read()
            status = response.status
    except (HTTPError, URLError) as exc:
        status = exc.code if isinstance(exc, HTTPError) else 0

    elapsed_ms = (time.perf_counter() - started) * 1000.0
    return elapsed_ms, status


def percentile(values: list[float], p: float) -> float:
    ordered = sorted(values)
    index = min(len(ordered) - 1, round((len(ordered) - 1) * p))
    return ordered[index]


def main() -> None:
    parser = argparse.ArgumentParser(
        description="Benchmark OmniCloud API latency under concurrent requests"
    )
    parser.add_argument("--base-url", default="http://localhost:8080")
    parser.add_argument("--path", default="/api/tasks")
    parser.add_argument("--requests", type=int, default=100)
    parser.add_argument("--workers", type=int, default=20)
    args = parser.parse_args()

    if args.requests <= 0 or args.workers <= 0:
        raise SystemExit("--requests and --workers must be positive")

    url = f"{args.base_url.rstrip('/')}/{args.path.lstrip('/')}"
    print(f"Target: {url}")
    print(f"Requests: {args.requests}")
    print(f"Workers: {args.workers}")

    started = time.perf_counter()
    samples: list[float] = []
    statuses: list[int] = []

    with ThreadPoolExecutor(max_workers=args.workers) as executor:
        futures = [
            executor.submit(request, "GET", url)
            for _ in range(args.requests)
        ]
        for future in as_completed(futures):
            latency, status = future.result()
            samples.append(latency)
            statuses.append(status)

    elapsed = time.perf_counter() - started
    successful = sum(status == 200 for status in statuses)

    print("\n=== OmniCloud API Load Test ===")
    print(f"Successful responses : {successful}/{len(statuses)}")
    print(f"Wall-clock time      : {elapsed:.4f} s")
    print(f"Throughput           : {len(statuses) / elapsed:.2f} requests/s")
    print(f"Mean latency         : {statistics.mean(samples):.2f} ms")
    print(f"p50 latency          : {percentile(samples, 0.50):.2f} ms")
    print(f"p95 latency          : {percentile(samples, 0.95):.2f} ms")
    print(f"p99 latency          : {percentile(samples, 0.99):.2f} ms")
    print(f"Max latency          : {max(samples):.2f} ms")


if __name__ == "__main__":
    main()
