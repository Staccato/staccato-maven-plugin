package com.readytalk.staccato.maven.plugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author jhumphrey
 */
public class MigrationTest {

  @Test
  public void testBasicExecute() throws MojoExecutionException, MojoFailureException {

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
  public void testFailedExecute() throws MojoExecutionException, MojoFailureException {

    String dbName = "staccato";
    String jdbcUrl = "jdbc:postgresql://localhost:5432";
    String dbUsername = "staccato";
    String dbPassword = "staccato";
    String dbType = "SCHEMA_UP";
    String migrateScript = "Foo.groovy";
    String migrateFromDate = "2000-01-01";

    Migration plugin = new Migration();

    plugin.setJdbcUrl(jdbcUrl);
    plugin.setDbName(dbName);
    plugin.setDbUser(dbUsername);
    plugin.setDbPwd(dbPassword);
    plugin.setMigrationType(dbType);
    plugin.setFromDate(migrateFromDate);
    plugin.setScript(migrateScript);

    try {
      plugin.execute();
      Assert.fail("should fail since both migrateFromDate and migrateScript are specified");
    } catch (MojoExecutionException e) {

    }

  }
}
