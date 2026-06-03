package com.opitral;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Task №2: рахує кількість Java-файлів та рядків коду у src/main/java,
 * друкує звіт у консоль і зберігає його у build/reports/loc/loc.txt.
 */
public abstract class CountLinesTask extends DefaultTask {

    @InputDirectory
    public abstract DirectoryProperty getSourceDir();

    @OutputFile
    public abstract RegularFileProperty getReportFile();

    @TaskAction
    public void count() throws IOException {
        File root = getSourceDir().get().getAsFile();

        List<File> javaFiles = new ArrayList<>();
        try (Stream<java.nio.file.Path> paths = Files.walk(root.toPath())) {
            paths.filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".java"))
                    .map(java.nio.file.Path::toFile)
                    .forEach(javaFiles::add);
        }
        javaFiles.sort(Comparator.comparing(File::getName));

        int totalLines = 0;
        int nonBlankLines = 0;
        StringBuilder report = new StringBuilder();
        report.append("Звіт про обсяг коду — ").append(root.getPath()).append("\n");
        report.append("=".repeat(50)).append("\n");

        for (File file : javaFiles) {
            List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            long nonBlank = lines.stream().filter(l -> !l.isBlank()).count();
            totalLines += lines.size();
            nonBlankLines += nonBlank;
            report.append(file.getName()).append(": ").append(lines.size())
                    .append(" рядків (непорожніх: ").append(nonBlank).append(")\n");
        }

        report.append("=".repeat(50)).append("\n");
        report.append("Файлів: ").append(javaFiles.size()).append("\n");
        report.append("Усього рядків: ").append(totalLines).append("\n");
        report.append("Непорожніх рядків: ").append(nonBlankLines).append("\n");

        File out = getReportFile().get().getAsFile();
        out.getParentFile().mkdirs();
        Files.write(out.toPath(), report.toString().getBytes(StandardCharsets.UTF_8));

        getLogger().lifecycle(report.toString());
        getLogger().lifecycle("Звіт збережено: " + out.getPath());
    }
}
