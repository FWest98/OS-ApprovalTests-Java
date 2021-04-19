package org.approvaltests.reporters;

import java.io.IOException;

import com.spun.util.StringUtils;
import com.spun.util.io.FileUtils;
import com.spun.util.logger.SimpleLogger;
import org.approvaltests.core.ApprovalFailureReporter;

import com.spun.util.ObjectUtils;

public class FileCaptureReporter implements ApprovalFailureReporter
{
  private String message = "*** adding received file via FileCaptureReporter for further inspection";
  public FileCaptureReporter()
  {
  }
  public FileCaptureReporter(String message)
  {
    this.message = message;
  }
  @Override
  public void report(String received, String approved)
  {
    run("git add --force " + received);
    run(String.format("git commit -m '%s'", message));
    run("git push");
  }
  private void run(String command)
  {
    SimpleLogger.event(command);
    try
    {
      Process process = Runtime.getRuntime().exec(command);
      process.waitFor();
      final int exitValue = process.exitValue();
      if (exitValue != 0)
      {
        final String stdout = FileUtils.readStream(process.getInputStream());
        final String stderr = FileUtils.readStream(process.getErrorStream());
        SimpleLogger.warning(String.format("stdout:\n%s\nstderr:\n%s", stdout, stderr));
      }
    }
    catch (IOException | InterruptedException e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
}
