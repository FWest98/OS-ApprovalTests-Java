package org.approvaltests.reporters;

import org.junit.jupiter.api.Test;
import org.lambda.query.Queryable;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class IntelliJResolverTest
{
  @Test
  void testFindItOnMac()
  {
    String userHome = "Users/lars";
    Queryable<String> validPaths = Queryable.as("/Applications/IntelliJ IDEA.app/Contents/MacOS/idea",
        "Users/lars/Applications/IntelliJ IDEA Ultimate.app/Contents/MacOS/idea",
        "Users/lars/Applications/IntelliJ IDEA Community.app/Contents/MacOS/idea",
        "Users/lars/Applications/IntelliJ IDEA Community Edition.app/Contents/MacOS/idea");
    for (String path : validPaths)
    {
      DiffInfo diffInfo = IntelliJToolboxResolver.getDiffInfoMac(userHome, f -> f.equals(path));
      assertNotEquals("", diffInfo.diffProgram, path);
    }
  }
  @Test
  void testFindItOnLinux()
  {
    String userHome = "/home/lars";
    Queryable<String> validPaths = Queryable.as(
        "/home/lars/.local/share/JetBrains/Toolbox/apps/intellij-idea-ultimate/bin/idea.sh",
        "/home/lars/.local/share/JetBrains/Toolbox/apps/intellij-idea-community-edition/bin/idea.sh");
    for (String path : validPaths)
    {
      DiffInfo diffInfo = IntelliJToolboxResolver.getDiffInfoLinux(userHome, f -> f.equals(path));
      assertNotEquals("", diffInfo.diffProgram, path);
    }
  }
}
