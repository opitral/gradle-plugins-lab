package com.opitral;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Task №1: генерує Java-клас com.opitral.generated.BuildInfo з метаданими
 * збірки (назва, версія, час). Згенерована директорія підключається як
 * джерело до main sourceSet, тож код доступний застосунку.
 */
public abstract class BuildInfoTask extends DefaultTask {

    @Input
    public abstract Property<String> getProjectName();

    @Input
    public abstract Property<String> getProjectVersion();

    @OutputDirectory
    public abstract DirectoryProperty getOutputDir();

    @TaskAction
    public void generate() throws IOException {
        File pkgDir = new File(getOutputDir().get().getAsFile(), "com/opitral/generated");
        pkgDir.mkdirs();

        String buildTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String source = ""
                + "package com.opitral.generated;\n\n"
                + "/** Автозгенеровано task-ом generateBuildInfo. Не редагувати вручну. */\n"
                + "public final class BuildInfo {\n"
                + "    public static final String NAME = \"" + getProjectName().get() + "\";\n"
                + "    public static final String VERSION = \"" + getProjectVersion().get() + "\";\n"
                + "    public static final String BUILD_TIME = \"" + buildTime + "\";\n\n"
                + "    private BuildInfo() {\n"
                + "    }\n"
                + "}\n";

        File out = new File(pkgDir, "BuildInfo.java");
        Files.write(out.toPath(), source.getBytes(StandardCharsets.UTF_8));

        getLogger().lifecycle("Згенеровано BuildInfo.java (версія "
                + getProjectVersion().get() + ", " + buildTime + ")");
    }
}
