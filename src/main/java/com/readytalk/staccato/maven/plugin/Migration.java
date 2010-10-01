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
   * The super user pwd.
   *
   * @parameter expression="${superUserPwd}"
   */
  private String superUserPwd;

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
  private String script;

  /**
   * The database password
   *
   * @parameter expression="${fromDate}"
   */
  private String fromDate;

  /**
   * The database password
   *
   * @parameter expression="${toDate}"
   */
  private String toDate;
  /**
   * The database password
   *
   * @parameter expression="${fromVersion}"
   */
  private String fromVersion;

  /**
   * The database password
   *
   * @parameter expression="${toVersion}"
   */
  private String toVersion;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {

    Injector injector = Guice.createInjector(new MigrationModule());

    StaccatoOptions options = new StaccatoOptions();
    options.jdbcUrl = jdbcUrl;
    options.dbName = dbName;
    options.dbUser = dbUser;
    options.dbPwd = dbPwd;
    options.migrationType = migrationType;
    options.migrateScript = script;
    options.migrateFromDate = fromDate;
    options.migrateToDate = toDate;
    options.migrateFromVer = fromVersion;
    options.migrateToVer = toVersion;
    options.dbSuperUserPwd = superUserPwd;

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

  public String getFromDate() {
    return fromDate;
  }

  public void setFromDate(String fromDate) {
    this.fromDate = fromDate;
  }

  public String getScript() {
    return script;
  }

  public void setScript(String script) {
    this.script = script;
  }

  public String getToDate() {
    return toDate;
  }

  public void setToDate(String toDate) {
    this.toDate = toDate;
  }

  public String getFromVersion() {
    return fromVersion;
  }

  public void setFromVersion(String fromVersion) {
    this.fromVersion = fromVersion;
  }

  public String getToVersion() {
    return toVersion;
  }

  public void setToVersion(String toVersion) {
    this.toVersion = toVersion;
  }
}
