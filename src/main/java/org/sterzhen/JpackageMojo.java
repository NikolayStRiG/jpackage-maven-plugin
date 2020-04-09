package org.sterzhen;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * The class create package
 */
@Mojo(name = "createpackage", defaultPhase = LifecyclePhase.VALIDATE, threadSafe = true)
public class JpackageMojo extends AbstractMojo {

    public static final String ERROR_CREATE_PACKAGE = "Error create package";

    @Parameter(defaultValue = "${project.build.directory}")
    private File buildDirectory;

    @Parameter( defaultValue = "${project}", readonly = true )
    private MavenProject project;

    @Override
    public void execute() {
        var name = project.getArtifactId();
        var path = buildDirectory.getPath();
        var main = project.getArtifact().getFile().getName();
        var dest = path + "\\dest";

        try {
            String command  = "jpackage.exe --name " + name + " --input "
                    + path + " --main-jar " + main + " --dest " + dest
                    + " --win-menu --win-shortcut";
            getLog().debug(command);
            Process proc = Runtime.getRuntime().exec(command);
            getLog().info(Arrays.toString(proc.getInputStream().readAllBytes()));
            proc.waitFor();
            proc.destroy();
        } catch (IOException e) {
            getLog().error(ERROR_CREATE_PACKAGE, e);
        } catch (InterruptedException e) {
            getLog().error(ERROR_CREATE_PACKAGE, e);
            Thread.currentThread().interrupt();
        }
    }
}
