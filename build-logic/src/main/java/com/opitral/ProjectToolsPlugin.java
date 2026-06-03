package com.opitral;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.TaskProvider;

/**
 * Власний плагін проєкту. Реєструє два task-и у групі "project tools"
 * та підключає згенеровані джерела до компіляції.
 */
public class ProjectToolsPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        TaskProvider<BuildInfoTask> buildInfo = project.getTasks()
                .register("generateBuildInfo", BuildInfoTask.class, task -> {
                    task.setGroup("project tools");
                    task.setDescription("Генерує клас BuildInfo з метаданими збірки");
                    task.getProjectName().set(project.provider(project::getName));
                    task.getProjectVersion().set(project.provider(() -> project.getVersion().toString()));
                    task.getOutputDir().set(
                            project.getLayout().getBuildDirectory().dir("generated/sources/buildinfo"));
                });

        project.getTasks().register("countLinesOfCode", CountLinesTask.class, task -> {
            task.setGroup("project tools");
            task.setDescription("Рахує кількість файлів і рядків Java-коду у src/main/java");
            task.getSourceDir().set(project.getLayout().getProjectDirectory().dir("src/main/java"));
            task.getReportFile().set(project.getLayout().getBuildDirectory().file("reports/loc/loc.txt"));
        });

        // Згенерований BuildInfo має компілюватися разом з кодом застосунку.
        project.getPlugins().withId("java", plugin -> {
            SourceSetContainer sourceSets = project.getExtensions().getByType(SourceSetContainer.class);
            sourceSets.getByName("main").getJava()
                    .srcDir(buildInfo.flatMap(BuildInfoTask::getOutputDir));
            project.getTasks().named("compileJava").configure(t -> t.dependsOn(buildInfo));
        });
    }
}
