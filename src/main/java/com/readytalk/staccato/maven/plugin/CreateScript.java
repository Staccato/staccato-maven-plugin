package com.readytalk.staccato.maven.plugin;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.joda.time.DateTime;

import com.mysql.jdbc.StringUtils;
import com.readytalk.staccato.database.migration.script.template.GroovyScriptTemplateService;
import com.readytalk.staccato.database.migration.script.template.ScriptTemplate;

/**
 * Goal that creates a groovy migration script from the template provided by the staccato project
 *
 * @goal create-script
 */
public class CreateScript extends AbstractMojo {

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

    GroovyScriptTemplateService templateService = new GroovyScriptTemplateService();

    DateTime date = new DateTime();
    try {
      String user = System.getenv("USER");
      ScriptTemplate template = templateService.loadTemplate(date, user, project.getVersion());

      String filenameToUse = template.getFilename();
      if (!StringUtils.isNullOrEmpty(filename)) {
        filenameToUse = filename + ".groovy";
      }

      migrationFile = new File(migrationsDir, filenameToUse);

      String templateContents = template.getTemplateContents();

      if (!StringUtils.isNullOrEmpty(filename)) {
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
