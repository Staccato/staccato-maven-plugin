package com.readytalk.staccato.maven.plugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.testng.annotations.Test;

/**
 * @author jhumphrey
 */
public class CreateDatabaseTest {

  @Test
  public void testExecute() throws MojoExecutionException {

    String dbName = "staccato";
    String jdbcUrl = "jdbc:postgresql://localhost:5432/";
    String dbUsername = "staccato";
    String dbPassword = "staccato";
    String rootDbName = "staccato_root";

    CreateDatabase plugin = new CreateDatabase();

    plugin.setJdbcUrl(jdbcUrl);
    plugin.setDbName(dbName);
    plugin.setDbUser(dbUsername);
    plugin.setDbPwd(dbPassword);
    plugin.setRootDb(rootDbName);
    plugin.setSuperUser(dbUsername);
    plugin.setSuperUserPwd(dbPassword);

    plugin.execute();

  }
}
