package com.opitral;

import com.opitral.generated.BuildInfo;

/**
 * Точка входу. Виводить метадані збірки (генеруються плагіном
 * у task-у generateBuildInfo) та демонструє роботу TextStats.
 */
public class App {

    public static void main(String[] args) {
        System.out.println("=== " + BuildInfo.NAME + " ===");
        System.out.println("Версія: " + BuildInfo.VERSION);
        System.out.println("Зібрано: " + BuildInfo.BUILD_TIME);
        System.out.println();

        String text = args.length > 0
                ? String.join(" ", args)
                : "Gradle автоматизує збірку. Плагін додає task-и! Зручно?";

        System.out.println("Текст: " + text);
        System.out.println("Слів: " + TextStats.wordCount(text));
        System.out.println("Символів (без пробілів): " + TextStats.charCount(text));
        System.out.println("Речень: " + TextStats.sentenceCount(text));
    }
}
