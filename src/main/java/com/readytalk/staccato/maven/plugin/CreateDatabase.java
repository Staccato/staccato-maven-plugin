package com.readytalk.staccato.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mysql.jdbc.StringUtils;
import com.readytalk.staccato.Main;
import com.readytalk.staccato.Staccato;
import com.readytalk.staccato.StaccatoOptions;
import com.readytalk.staccato.database.migration.MigrationException;
import com.readytalk.staccato.database.migration.MigrationType;
import com.readytalk.staccato.database.migration.guice.MigrationModule;

/**
 * Runs a migration
 *
 * @goal create-database
 * @requiresDependencyResolution compile
 */
public class CreateDatabase extends AbstractMojo {

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
   * The root db name
   *
   * @parameter expression="${rootDb}"
   */
  private String rootDb;

  /**
   * The root database username
   *
   * @parameter expression="${superUser}"
   */
  private String superUser;

  /**
   * The root database password
   *
   * @parameter expression="${superUserPwd}"
   * @required
   */
  private String superUserPwd;

  /**
   * true if logging enabled, false otherwise
   *
   * @parameter expression="${loggingEnabled}"
   */
  private Boolean loggingEnabled = true;

  /**
   * The maven project.
   *
   * @parameter expression="${project}"
   * @readonly
   */
  private MavenProject project;

  public void execute() throws MojoExecutionException {

    Injector injector = Guice.createInjector(new MigrationModule());

    StaccatoOptions options = new StaccatoOptions();
    options.jdbcUrl = jdbcUrl;
    options.dbName = dbName;
    options.dbUser = dbUser;
    options.dbPwd = dbPwd;
    options.migrationType = MigrationType.CREATE.name();
    options.dbSuperUserPwd = superUserPwd;
    options.enableLogging = loggingEnabled;

    if (!StringUtils.isNullOrEmpty(superUser)) {
      options.dbSuperUser = superUser;
    }

    if (!StringUtils.isNullOrEmpty(rootDb)) {
      options.rootDb = rootDb;
    }

    try {
      Staccato staccato = injector.getInstance(Staccato.class);
      staccato.execute(options);
    } catch (MigrationException e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }

    options.migrationType = MigrationType.UP.name();

    try {
      Staccato staccato = injector.getInstance(Staccato.class);
      staccato.execute(options);
    } catch (MigrationException e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }
  }

  public MavenProject getProject() {
    return project;
  }

  public void setProject(MavenProject project) {
    this.project = project;
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

  public String getDbName() {
    return dbName;
  }

  public void setDbName(String dbName) {
    this.dbName = dbName;
  }

  public void setJdbcUrl(String jdbcUrl) {
    this.jdbcUrl = jdbcUrl;
  }

  public String getRootDb() {
    return rootDb;
  }

  public void setRootDb(String rootDb) {
    this.rootDb = rootDb;
  }

  public String getSuperUserPwd() {
    return superUserPwd;
  }

  public void setSuperUserPwd(String superUserPwd) {
    this.superUserPwd = superUserPwd;
  }

  public String getSuperUser() {
    return superUser;
  }

  public void setSuperUser(String superUser) {
    this.superUser = superUser;
  }

  public Boolean getLoggingEnabled() {
    return loggingEnabled;
  }

  public void setLoggingEnabled(Boolean loggingEnabled) {
    this.loggingEnabled = loggingEnabled;
  }
}
