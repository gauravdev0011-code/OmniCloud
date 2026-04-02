package com.omnicloud.utils;

import java.util.List;

public class CRDT {

    // Merge multiple edits, highlight conflicts
    public static String mergeEdits(List<String> edits) {
        if (edits.size() == 1) return edits.get(0);
        StringBuilder sb = new StringBuilder();
        for (String edit : edits) {
            sb.append("<<").append(edit).append(">> ");
        }
        return sb.toString().trim();
    }
}