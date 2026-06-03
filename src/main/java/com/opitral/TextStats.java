package com.opitral;

import java.util.Arrays;

/**
 * Проста утиліта для підрахунку статистики тексту:
 * кількість слів, символів та речень.
 */
public final class TextStats {

    private TextStats() {
    }

    public static int wordCount(String text) {
        if (text == null || text.isBlank()) {
            return 0;
        }
        return (int) Arrays.stream(text.trim().split("\\s+"))
                .filter(w -> !w.isBlank())
                .count();
    }

    public static int charCount(String text) {
        if (text == null) {
            return 0;
        }
        return text.replaceAll("\\s", "").length();
    }

    public static int sentenceCount(String text) {
        if (text == null || text.isBlank()) {
            return 0;
        }
        return (int) Arrays.stream(text.split("[.!?]+"))
                .filter(s -> !s.isBlank())
                .count();
    }
}
