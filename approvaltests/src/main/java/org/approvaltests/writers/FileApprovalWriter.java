package org.approvaltests.writers;

import java.io.File;

import org.approvaltests.core.ApprovalWriter;

import com.spun.util.io.FileUtils;

public class FileApprovalWriter implements ApprovalWriter
{
  private final File newFile;
  private String     extensionWithDot;
  public FileApprovalWriter(File newFile)
  {
    this.newFile = newFile;
    extensionWithDot = FileUtils.getExtensionWithDot(newFile.getName());
  }
  @Override
  public File writeReceivedFile(File received)
  {
    return newFile;
  }
  @Override
  public String getApprovalFilename(String base)
  {
    return base + Writer.approved + extensionWithDot;
  }
  @Override
  public String getReceivedFilename(String base)
  {
    return base + Writer.received + extensionWithDot;
  }
  @Override
  public String getFileExtensionWithDot()
  {
    return extensionWithDot;
  }
}
