package org.approvaltests.scrubbers;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.velocity.VelocityApprovals;
import org.junit.jupiter.api.Test;

public class DateScrubberTests
{
  @Test
  void testGetDateScrubber()
  {
    String[] formats = getSupportedFormats();
    Approvals.verifyAll("Date scrubbing", formats, this::verifyScrubbing);
  }
  private String[] getSupportedFormats()
  {
    return new String[]{"Tue May 13 16:30:00",
                        "Tue May 13 2014 23:30:00.789",
                        "Tue May 13 16:30:00 -0800 2014",
                        "13 May 2014 23:50:49,999",
                        "May 13, 2014 11:30:00 PM PST",
                        "23:30:00",
                        "2014/05/13 16:30:59.786",
                        "2020-09-10T08:07Z",
                        "2020-9-10T08:07Z",
                        "2020-09-9T08:07Z",
                        "2020-09-10T8:07Z",
                        "2020-09-10T08:07:89Z",
                        "2020-09-10T01:23:45.678Z"};
  }
  private String verifyScrubbing(String formattedExample)
  {
    DateScrubber scrubber = DateScrubber.getScrubberFor(formattedExample);
    String exampleText = String.format("{'date':\"%s\"}", formattedExample);
    return String.format("Scrubbing for %s:\n" + "%s\n" + "Example: %s\n\n", formattedExample, scrubber,
        scrubber.scrub(exampleText));
  }
  @Test
  void exampleForDocumentation()
  {
    // begin-snippet: scrub-date-example
    Approvals.verify("created at 03:14:15", new Options().withScrubber(DateScrubber.getScrubberFor("00:00:00")));
    // end-snippet
  }
  @Test
  void supportedFormats()
  {
    VelocityApprovals.verify(c -> {
      c.put("formats", getSupportedFormats());
      c.put("datescrubber", new DateScrubber(""));
    }, ".md");
  }
}
