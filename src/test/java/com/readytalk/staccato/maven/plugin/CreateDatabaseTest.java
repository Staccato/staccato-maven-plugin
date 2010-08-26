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
    String dbName = "database_manager_test";
    String jdbcUrl = "jdbc:postgresql://localhost:5432/" + dbName;
    String dbUsername = "database_manager";
    String dbPassword = "database_manager";
    String dbType = "SCHEMA_UP";

    CreateDatabase plugin = new CreateDatabase();

    MavenProject project = EasyMock.createStrictMock(MavenProject.class);
    EasyMock.expect(project.getName()).andReturn(projectName);
    EasyMock.expect(project.getVersion()).andReturn(projectVersion);
    EasyMock.replay(project);
    plugin.setProject(project);

    plugin.setJdbcUrl(jdbcUrl);
    plugin.setDbName(dbName);
    plugin.setDbUsername(dbUsername);
    plugin.setDbPassword(dbPassword);
    plugin.setMigrationType(dbType);

    plugin.execute();

  }
}