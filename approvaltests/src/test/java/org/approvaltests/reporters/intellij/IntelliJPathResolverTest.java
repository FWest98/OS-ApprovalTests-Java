package org.approvaltests.reporters.intellij;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.core.Scrubber;
import org.approvaltests.namer.NamerFactory;
import org.approvaltests.reporters.GenericDiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.scrubbers.RegExScrubber;
import org.junit.jupiter.api.Test;

class IntelliJPathResolverTest
{
  @UseReporter(IntelliJMacSiliconReporter.class)
  @Test
  void testDetectionOfIntellijOnDevMachines()
  {
    // begin-snippet: runOnlyOnSpecificMachines
    if (!NamerFactory.asMachineNameSpecificTest().isCurrentEnvironmentValidFor("Larss-Air.lan", "macbook13"))
    { return; }
    // the rest of your test...
    // end-snippet

    final GenericDiffReporter environmentAwareReporter = (GenericDiffReporter) new IntelliJReporter()
        .getWorkingReportersForEnviroment().get(0);
    final String[] commandLine = environmentAwareReporter.getCommandLine("r.txt", "a.txt");
    // the moment it breaks and we are annoyed, start scrubbing.
    final Scrubber scrubber = new RegExScrubber("", "<your replacement>");
    Approvals.verify(commandLine[0], new Options(scrubber));
  }
}
