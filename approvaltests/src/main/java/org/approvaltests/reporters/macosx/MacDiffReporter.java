package org.approvaltests.reporters.macosx;

import org.approvaltests.reporters.FirstWorkingReporter;

public class MacDiffReporter extends FirstWorkingReporter
{
  public static final MacDiffReporter INSTANCE = new MacDiffReporter();
  public MacDiffReporter()
  {
    super(BeyondCompareMacReporter.INSTANCE, DiffMergeReporter.INSTANCE,
        KaleidoscopeDiffReporter.INSTANCE, P4MergeReporter.INSTANCE, KDiff3Reporter.INSTANCE,
        TkDiffReporter.INSTANCE, VisualStudioCodeReporter.INSTANCE);
  }
}
