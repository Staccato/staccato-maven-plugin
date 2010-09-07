package com.readytalk.staccato.maven.plugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.easymock.EasyMock;
import org.testng.annotations.Test;

/**
 * @author jhumphrey
 */
public class CreateDatabaseTest {

  @Test
  public void testExecute() throws MojoExecutionException {

    String projectName = "staccato-plugin";
    String projectVersion = "1.0";
    String dbName = "staccato";
    String jdbcUrl = "jdbc:postgresql://localhost:5432/";
    String dbUsername = "staccato";
    String dbPassword = "staccato";
    String rootDbName = "staccato_root";

    CreateDatabase plugin = new CreateDatabase();

    MavenProject project = EasyMock.createStrictMock(MavenProject.class);
    EasyMock.expect(project.getName()).andReturn(projectName);
    EasyMock.expect(project.getVersion()).andReturn(projectVersion);
    EasyMock.expect(project.getName()).andReturn(projectName);
    EasyMock.expect(project.getVersion()).andReturn(projectVersion);
    EasyMock.replay(project);
    plugin.setProject(project);

    plugin.setJdbcUrl(jdbcUrl);
    plugin.setDbName(dbName);
    plugin.setDbUsername(dbUsername);
    plugin.setDbPassword(dbPassword);
    plugin.setRootDbName(rootDbName);
    plugin.setRootDbUsername(dbUsername);
    plugin.setRootDbPassword(dbPassword);

    plugin.execute();

  }
}
