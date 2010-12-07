package com.readytalk.staccato.maven.plugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.testng.annotations.Test;

/**
 * @author jhumphrey
 */
public class MigrationTest {

  @Test
  public void testExecuteWithSchemaUp() throws MojoExecutionException, MojoFailureException {

    String dbName = "staccato";
    String jdbcUrl = "jdbc:postgresql://localhost:5432/";
    String dbUsername = "staccato";
    String dbPassword = "staccato";
    String dbType = "SCHEMA_UP";

    Migration plugin = new Migration();

    plugin.setJdbcUrl(jdbcUrl);
    plugin.setDbName(dbName);
    plugin.setDbUser(dbUsername);
    plugin.setDbPwd(dbPassword);
    plugin.setMigrationType(dbType);

    plugin.execute();
  }

  @Test
  public void testExecuteWithCreate() throws MojoExecutionException, MojoFailureException {

    String dbName = "staccato";
    String jdbcUrl = "jdbc:postgresql://localhost:5432/";
    String dbUsername = "staccato";
    String dbPassword = "staccato";
    String dbType = "CREATE";
    String dbSuperUserPwd = "staccato";

    Migration plugin = new Migration();

    plugin.setJdbcUrl(jdbcUrl);
    plugin.setDbName(dbName);
    plugin.setDbUser(dbUsername);
    plugin.setDbPwd(dbPassword);
    plugin.setMigrationType(dbType);
    plugin.setSuperUserPwd(dbSuperUserPwd);

    plugin.execute();
  }
}
