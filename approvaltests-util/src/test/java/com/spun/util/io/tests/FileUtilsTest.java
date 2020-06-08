package com.spun.util.io.tests;

import java.io.BufferedWriter;
import java.io.File;

import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import org.approvaltests.Approvals;
import org.approvaltests.namer.ApprovalNamer;
import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.Test;

import com.spun.util.io.FileUtils;

import junit.framework.TestCase;

/**
 * A static class of convenience functions for Files
 **/
public class FileUtilsTest extends TestCase
{
  public void testDirectoryName() throws Exception
  {
    String name = "Set Enterprises Inc.";
    assertEquals("Set Enterprises Inc", FileUtils.getDirectoryFriendlyName(name));
    name = "Lucon Kids Inc.";
    assertEquals("Lucon Kids Inc", FileUtils.getDirectoryFriendlyName(name));
  }
  public void testExtensionWithDot() throws Exception
  {
    assertEquals(".txt", FileUtils.getExtensionWithDot("c:\\some.thing\\there\\a.txt"));
  }
  public void testExtensionWithoutDot() throws Exception
  {
    assertEquals("txt", FileUtils.getExtensionWithoutDot("c:\\some.thing\\there\\a.txt"));
  }
  @Test
  public void testIsNonEmptyFile() throws Exception
  {
    assertTrue(FileUtils.isNonEmptyFile(adjacentFile("FileTestUtils.java")));
  }
  @Test
  public void testIsDefaultText() throws Exception
  {
    String text = FileUtils.readFile(new File(adjacentFile("missing_file.txt")), "default text");
    assertEquals("default text", text);
  }
  private String adjacentFile(String name)
  {
    ApprovalNamer namer = Approvals.createApprovalNamer();
    String file = namer.getSourceFilePath() + name;
    return file;
  }
  
  public void testCopyFile() throws Exception
  {
    File first = File.createTempFile("unitTest", ".txt");
    File second = File.createTempFile("unitTestCopy", ".txt");
    first.deleteOnExit();
    second.deleteOnExit();
    try (BufferedWriter writer = Files.newBufferedWriter(first.toPath(), StandardCharsets.UTF_8)) {
      writer.write("Mary had a little lamb");
    }
    FileUtils.copyFile(first, second);
    assertEquals("File sizes ", first.length(), second.length());
  }
  /************************************************************************/
  /************************************************************************/

  @UseReporter(ClipboardReporter.class)
  @Test
  public void testSaveToFile() {
    Reader input = new StringReader("hello Approvals");
    File file = FileUtils.saveToFile("pre_", input);
    Approvals.verify(file);
  }
}