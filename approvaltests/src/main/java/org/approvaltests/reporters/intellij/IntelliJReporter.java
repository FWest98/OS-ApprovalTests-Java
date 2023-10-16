package org.approvaltests.reporters.intellij;

import org.approvaltests.reporters.DiffPrograms;
import org.approvaltests.reporters.FirstWorkingReporter;
import org.approvaltests.reporters.GenericDiffReporter;

public class IntelliJReporter extends FirstWorkingReporter
{
  public static final IntelliJReporter INSTANCE = new IntelliJReporter();
  public IntelliJReporter()
  {
    super(IntelliJToolboxReporter.INSTANCE, IntelliJMacSiliconReporter.INSTANCE, IntelliJUltimateReporter.INSTANCE,
        IntelliJCommunityReporter.INSTANCE);
  }
  private static class IntelliJToolboxReporter extends GenericDiffReporter
  {
    public static final IntelliJToolboxReporter INSTANCE = new IntelliJToolboxReporter();
    public IntelliJToolboxReporter()
    {
      super(DiffPrograms.All.INTELLIJ);
    }
  }
}
