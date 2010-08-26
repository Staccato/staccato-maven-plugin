package com.readytalk.staccato.maven.plugin;

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.easymock.EasyMock;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author jhumphrey
 */
public class CreateScriptTest {

  @Test
  public void testExecute() throws MojoExecutionException, IOException {

    String migrationsDir = "target";
    String projectVersion = "1.0";

    CreateScript plugin = new CreateScript();
    plugin.setMigrationsDir(migrationsDir);

    MavenProject project = EasyMock.createStrictMock(MavenProject.class);
    EasyMock.expect(project.getVersion()).andReturn(projectVersion);
    EasyMock.replay(project);

    plugin.setProject(project);
    plugin.execute();

    File migrationFile = plugin.getMigrationFile();

    Assert.assertNotNull(migrationFile);
    Assert.assertTrue(migrationFile.exists());
    Assert.assertTrue(migrationFile.isFile());
  }
}
