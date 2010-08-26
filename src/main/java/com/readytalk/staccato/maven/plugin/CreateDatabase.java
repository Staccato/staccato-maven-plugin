package com.readytalk.staccato.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import com.ecovate.database.migration.Main;
import com.ecovate.database.migration.MigrationException;

/**
 * Runs a migration
 *
 * @goal create-database
 * @requiresDependencyResolution compile
 */
public class CreateDatabase extends AbstractMojo {

  /**
   * The Migrations Type.
   *
   * @parameter expression="${migrationType}"
   * @required
   */
  private String migrationType;

  /**
   * The jdbc url
   *
   * @parameter expression="${jdbcUrl}"
   * @required
   */
  private String jdbcUrl;

  /**
   * The db name
   *
   * @parameter expression="${dbName}"
   * @required
   */
  private String dbName;

  /**
   * The database username
   *
   * @parameter expression="${dbUsername}"
   * @required
   */
  private String dbUsername;

  /**
   * The database password
   *
   * @parameter expression="${dbPassword}"
   * @required
   */
  private String dbPassword;

  /**
   * The maven project.
   *
   * @parameter expression="${project}"
   * @readonly
   */
  private MavenProject project;

  public void execute() throws MojoExecutionException {

    try {
      Main.main("-jdbc", jdbcUrl, "-dbn", dbName, "-dbu", dbUsername, "-dbp", dbPassword, "-pn", project.getName(), "-pv", project.getVersion(), "-m", migrationType);
    } catch (MigrationException e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }
  }

  public String getMigrationType() {
    return migrationType;
  }

  public void setMigrationType(String migrationType) {
    this.migrationType = migrationType;
  }

  public MavenProject getProject() {
    return project;
  }

  public void setProject(MavenProject project) {
    this.project = project;
  }

  public String getDbPassword() {
    return dbPassword;
  }

  public void setDbPassword(String dbPassword) {
    this.dbPassword = dbPassword;
  }

  public String getDbUsername() {
    return dbUsername;
  }

  public void setDbUsername(String dbUsername) {
    this.dbUsername = dbUsername;
  }

  public String getJdbcUrl() {
    return jdbcUrl;
  }

  public String getDbName() {
    return dbName;
  }

  public void setDbName(String dbName) {
    this.dbName = dbName;
  }

  public void setJdbcUrl(String jdbcUrl) {
    this.jdbcUrl = jdbcUrl;
  }
}
