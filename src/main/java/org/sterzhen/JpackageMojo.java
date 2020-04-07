package org.sterzhen;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static java.lang.System.Logger.Level.ERROR;
import static java.lang.System.Logger.Level.INFO;

/**
 * The class create package
 */
@Mojo(name = "createpackage", defaultPhase = LifecyclePhase.VALIDATE, threadSafe = true)
public class JpackageMojo extends AbstractMojo {

    private System.Logger logger = System.getLogger(JpackageMojo.class.getName());

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
            logger.log(INFO, command);
            Process proc = Runtime.getRuntime().exec(command);

            logger.log(INFO, Arrays.toString(proc.getInputStream().readAllBytes()));
            proc.waitFor();
            proc.destroy();
        } catch (IOException e) {
            logger.log(ERROR, "Error create package", e);
        } catch (InterruptedException e) {
            logger.log(ERROR, "Error create package", e);
            Thread.currentThread().interrupt();
        }
        logger.log(INFO, "End create package");
    }
}
