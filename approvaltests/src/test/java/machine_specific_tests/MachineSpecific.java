package machine_specific_tests;

import java.util.Arrays;
import java.util.List;

import com.spun.util.SystemUtils;

public class MachineSpecific
{
  public static boolean       FORCE_RUN = false;
  private static List<String> MACHINES  = Arrays.asList("LLEWELLYN-FALCOs-MacBook-Pro-2.local");
  public static boolean isMachineConfiguredForTesting()
  {
    return FORCE_RUN || MACHINES.contains(SystemUtils.getComputerName());
  }
}
