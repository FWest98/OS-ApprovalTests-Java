package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.FirstWorkingReporter;
import org.approvaltests.reporters.intellij.IntelliJCommunityReporter;
import org.approvaltests.reporters.intellij.IntelliJUltimateReporter;

public class WindowsDiffReporter extends FirstWorkingReporter
{
  public static final WindowsDiffReporter INSTANCE = new WindowsDiffReporter();
  public WindowsDiffReporter()
  {
    super(
        // startcode windows_diff_reporters
        TortoiseDiffReporter.INSTANCE,
        BeyondCompareReporter.INSTANCE,
        WinMergeReporter.INSTANCE,
        AraxisMergeReporter.INSTANCE,
        CodeCompareReporter.INSTANCE,
        KDiff3Reporter.INSTANCE,
        VisualStudioCodeReporter.INSTANCE
    // endcode
    );
  }
}
