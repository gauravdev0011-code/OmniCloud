package com.omnicloud.utils;

import java.util.List;

public class CRDT {

    // Simple merge logic for concurrent edits
    public static String mergeEdits(List<String> edits) {
        // Spicy: highlight conflicts with brackets
        StringBuilder result = new StringBuilder();
        for (String edit : edits) {
            result.append("[").append(edit).append("] ");
        }
        return result.toString().trim();
    }
}