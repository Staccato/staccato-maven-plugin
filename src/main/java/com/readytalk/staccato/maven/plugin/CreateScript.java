package com.readytalk.staccato.maven.plugin;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.joda.time.DateTime;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.readytalk.staccato.database.migration.guice.MigrationModule;
import com.readytalk.staccato.database.migration.script.ScriptTemplate;
import com.readytalk.staccato.database.migration.script.groovy.GroovyScriptService;

/**
 * Goal that creates a groovy migration script from the template provided by the staccato project
 *
 * @goal create-script
 */
public class CreateScript extends AbstractMojo {

  Injector injector = Guice.createInjector(new MigrationModule());

  /**
   * The migrations directory
   *
   * @parameter expression="${migrationsDir}"
   * @required
   */
  private String migrationsDir;

  /**
   * The migration filename
   *
   * @parameter expression="${filename}"
   */
  public String filename;

  /**
   * The maven project.
   *
   * @parameter expression="${project}"
   * @readonly
   */
  private MavenProject project;

  private File migrationFile;

  public void execute() throws MojoExecutionException {

    injector.injectMembers(this);
    GroovyScriptService scriptService = injector.getInstance(GroovyScriptService.class);

    DateTime date = new DateTime();
    try {
      String user = System.getenv("USER");
      ScriptTemplate template = scriptService.getScriptTemplate(date, user, project.getVersion());

      String filenameToUse = template.getClassname() + "." + scriptService.getScriptFileExtension();
      if (filename != null && !filename.equals("")) {
        filenameToUse = filename + "." + scriptService.getScriptFileExtension();
      }

      migrationFile = new File(migrationsDir, filenameToUse);

      String templateContents = template.getContents();

      if (filename != null && !filename.equals("")) {
        templateContents = templateContents.replace(template.getClassname(), filename);
      }

      FileUtils.writeStringToFile(migrationFile, templateContents);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getMigrationsDir() {
    return migrationsDir;
  }

  public void setMigrationsDir(String migrationsDir) {
    this.migrationsDir = migrationsDir;
  }

  public File getMigrationFile() {
    return migrationFile;
  }

  public MavenProject getProject() {
    return project;
  }

  public void setProject(MavenProject project) {
    this.project = project;
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }
}
