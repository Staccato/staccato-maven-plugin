package com.readytalk.staccato.maven.plugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.easymock.EasyMock;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author jhumphrey
 */
public class MigrateTest {

  @Test
  public void testBasicExecute() throws MojoExecutionException, MojoFailureException {

    String projectName = "staccato-plugin";
    String projectVersion = "1.0";
    String dbName = "staccato";
    String jdbcUrl = "jdbc:postgresql://localhost:5432/" + dbName;
    String dbUsername = "staccato";
    String dbPassword = "staccato";
    String dbType = "SCHEMA_UP";

    Migrate plugin = new Migrate();

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

  @Test
  public void testFailedExecute() throws MojoExecutionException, MojoFailureException {

    String projectName = "staccato-plugin";
    String projectVersion = "1.0";
    String dbName = "staccato";
    String jdbcUrl = "jdbc:postgresql://localhost:5432/" + dbName;
    String dbUsername = "staccato";
    String dbPassword = "staccato";
    String dbType = "SCHEMA_UP";
    String migrateScript = "Foo.groovy";
    String migrateFromDate = "2000-01-01";

    Migrate plugin = new Migrate();

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
    plugin.setMigrateFromDate(migrateFromDate);
    plugin.setMigrateScript(migrateScript);

    try {
      plugin.execute();
      Assert.fail("should fail since both migrateFromDate and migrateScript are specified");
    } catch (MojoExecutionException e) {
      
    }

  }
}
