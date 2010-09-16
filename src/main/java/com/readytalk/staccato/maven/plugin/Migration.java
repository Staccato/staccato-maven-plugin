package com.readytalk.staccato.maven.plugin;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.readytalk.staccato.Staccato;
import com.readytalk.staccato.StaccatoOptions;
import com.readytalk.staccato.database.migration.guice.MigrationModule;

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
   * @parameter expression="${dbUser}"
   * @required
   */
  private String dbUser;

  /**
   * The database password
   *
   * @parameter expression="${dbPwd}"
   * @required
   */
  private String dbPwd;

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

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {

    Injector injector = Guice.createInjector(new MigrationModule());

    StaccatoOptions options = new StaccatoOptions();
    options.jdbcUrl = jdbcUrl;
    options.dbName = dbName;
    options.dbUser = dbUser;
    options.dbPwd = dbPwd;
    options.migrationType = migrationType;

    if (!StringUtils.isEmpty(migrateScript) && !StringUtils.isEmpty(migrateFromDate)) {
      throw new MojoExecutionException("Either migrateScript or migrateFromDate should be specified, not both");
    } else if (!StringUtils.isEmpty(migrateScript)) {
      options.migrateScript = migrateScript;
    } else {
      if (!StringUtils.isEmpty(migrateFromDate)) {
        options.migrateFromDate = migrateFromDate;
      }

      if (!StringUtils.isEmpty(migrateToDate)) {
        options.migrateToDate = migrateToDate;
      }
    }

    Staccato staccato = injector.getInstance(Staccato.class);
    staccato.execute(options);
  }

  public String getDbName() {
    return dbName;
  }

  public void setDbName(String dbName) {
    this.dbName = dbName;
  }

  public String getDbPwd() {
    return dbPwd;
  }

  public void setDbPwd(String dbPwd) {
    this.dbPwd = dbPwd;
  }

  public String getDbUser() {
    return dbUser;
  }

  public void setDbUser(String dbUser) {
    this.dbUser = dbUser;
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
