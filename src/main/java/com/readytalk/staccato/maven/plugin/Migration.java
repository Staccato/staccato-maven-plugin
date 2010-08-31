package com.readytalk.staccato.maven.plugin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

import com.readytalk.staccato.Main;
import com.readytalk.staccato.database.migration.MigrationException;

/**
 * @author jhumphrey
 * @goal migration
 */
public class Migration extends AbstractMojo {

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
   * The database password
   *
   * @parameter expression="${migrateScript}"
   */
  private String migrateScript;

  /**
   * The database password
   *
   * @parameter expression="${migrateFromDate}"
   */
  private String migrateFromDate;

  /**
   * The database password
   *
   * @parameter expression="${migrateToDate}"
   */
  private String migrateToDate;

  /**
   * The maven project.
   *
   * @parameter expression="${project}"
   * @readonly
   */
  private MavenProject project;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {

    List<String> argList = new ArrayList<String>();
    argList.add("-jdbc");
    argList.add(jdbcUrl);
    argList.add("-dbn");
    argList.add(dbName);
    argList.add("-dbu");
    argList.add(dbUsername);
    argList.add("-dbp");
    argList.add(dbPassword);
    argList.add("-pn");
    argList.add(project.getName());
    argList.add("-pv");
    argList.add(project.getVersion());
    argList.add("-m");
    argList.add(migrationType);

    if (!StringUtils.isEmpty(migrateScript) && !StringUtils.isEmpty(migrateFromDate)) {
      throw new MojoExecutionException("Either migrateScript or migrateFromDate should be specified, not both");
    } else if (!StringUtils.isEmpty(migrateScript)) {
        argList.add("-ms");
        argList.add(migrateScript);
    } else {
      if (!StringUtils.isEmpty(migrateFromDate)) {
        argList.add("-mfd");
        argList.add(migrateFromDate);
      }

      if (!StringUtils.isEmpty(migrateToDate)) {
        argList.add("-mtd");
        argList.add(migrateToDate);
      }
    }

    String[] args = new String[argList.size()];
    for (int i = 0; i < args.length; i++) {
      args[i] = argList.get(i);

    }

    try {
      Main.main(args);
    } catch (MigrationException e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }
  }

  public String getDbName() {
    return dbName;
  }

  public void setDbName(String dbName) {
    this.dbName = dbName;
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

  public void setJdbcUrl(String jdbcUrl) {
    this.jdbcUrl = jdbcUrl;
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

  public String getMigrateFromDate() {
    return migrateFromDate;
  }

  public void setMigrateFromDate(String migrateFromDate) {
    this.migrateFromDate = migrateFromDate;
  }

  public String getMigrateScript() {
    return migrateScript;
  }

  public void setMigrateScript(String migrateScript) {
    this.migrateScript = migrateScript;
  }

  public String getMigrateToDate() {
    return migrateToDate;
  }

  public void setMigrateToDate(String migrateToDate) {
    this.migrateToDate = migrateToDate;
  }
}
