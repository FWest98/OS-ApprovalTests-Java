package org.approvaltests.core;

import java.util.Optional;

import org.approvaltests.ReporterFactory;
import org.approvaltests.scrubbers.NoOpScrubber;

public class Options
{
  private Scrubber                          scrubber    = NoOpScrubber.INSTANCE;
  private Optional<ApprovalFailureReporter> reporter    = Optional.empty();
  private FileOptions                       fileOptions = new FileOptions();
  public Options()
  {
  }
  public Options(Scrubber scrubber)
  {
    this.scrubber = scrubber;
  }
  public Options(ApprovalFailureReporter reporter)
  {
    this.reporter = Optional.ofNullable(reporter);
  }
  private Options(Scrubber scrubber, Optional<ApprovalFailureReporter> reporter, FileOptions fileOptions)
  {
    this.scrubber = scrubber;
    this.reporter = reporter;
    this.fileOptions = fileOptions;
  }
  public Options(Options parent, FileOptions fileOptions)
  {
    this(parent.scrubber, parent.reporter, fileOptions);
  }
  public ApprovalFailureReporter getReporter()
  {
    return this.reporter.orElse(ReporterFactory.get());
  }
  public Options withReporter(ApprovalFailureReporter reporter)
  {
    return new Options(this.scrubber, Optional.ofNullable(reporter), fileOptions);
  }
  public Options withScrubber(Scrubber scrubber)
  {
    return new Options(scrubber, this.reporter, fileOptions);
  }
  public String scrub(String input)
  {
    return scrubber.scrub(input);
  }
  public FileOptions forFile()
  {
    fileOptions.setParent(this);
    return fileOptions;
  }
  public static class FileOptions
  {
    private String    fileExtension = ".txt";
    protected Options parent;
    public FileOptions()
    {
    }
    public FileOptions(String fileExtension)
    {
      this.fileExtension = fileExtension;
    }
    public void setParent(Options parent)
    {
      this.parent = parent;
    }
    public Options withExtension(String fileExtensionWithDot)
    {
      if (!fileExtensionWithDot.startsWith("."))
      {
        fileExtensionWithDot = "." + fileExtensionWithDot;
      }
      FileOptions f = new FileOptions(fileExtensionWithDot);
      return new Options(parent, f);
    }

    public String getFileExtension() {
      return fileExtension;
    }
  }
}
