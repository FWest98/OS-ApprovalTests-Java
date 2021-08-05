package com.spun.util.parser;

import com.spun.util.DateUtils;
import java.sql.Timestamp;
import java.util.Arrays;
import org.approvaltests.utils.WithTimeZone;
import org.approvaltests.velocity.VelocityApprovals;
import org.junit.jupiter.api.Test;

//@UseReporter(FileLauncherReporter.class)
public class VelocityUtilsTest
{
  @Test
  public void testArray()
  {
    VelocityApprovals.verify(c -> c.put("array", new String[]{"one", "two", "three", "four", "five"}));
  }
  @Test
  public void testList()
  {
    VelocityApprovals.verify(c -> c.put("array", Arrays.asList("one", "two", "three")), ".html");
  }
  @Test
  public void testDate()
  {
    try (WithTimeZone tz = new WithTimeZone())
    {
      Timestamp date = DateUtils.parse("2001/02/03");
      VelocityApprovals.verify(c -> c.put("date", date), ".md");
    }
  }
}
