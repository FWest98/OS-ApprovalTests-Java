package org.approvaltests.packagesettings;

import org.approvaltests.reporters.EnvironmentAwareReporter;

public class CountingReporter implements EnvironmentAwareReporter
{
  public int count;
  @Override
  public boolean report(String received, String approved)
  {
    // do Nothing
    return isWorkingInThisEnvironment(received);
  }
  @Override
  public boolean isWorkingInThisEnvironment(String forFile)
  {
    count++;
    return count == 11;
  }
}
