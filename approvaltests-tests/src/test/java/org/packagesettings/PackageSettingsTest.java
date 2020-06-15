package org.packagesettings;

import java.util.Map;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

public class PackageSettingsTest
{
  @Test
  public void testRetriveValue()
  {
    // begin-snippet: package_level_settings_get
    Map<String, Settings> settings = PackageLevelSettings.get();
    // end-snippet
    Approvals.verify(settings);
  }
}
