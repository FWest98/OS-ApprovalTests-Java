package org.approvaltests.scrubbers;

import com.spun.util.FormattedException;
import org.lambda.functions.Function1;

import java.util.Arrays;
import java.util.regex.Pattern;

public class DateScrubber extends RegExScrubber {

    public DateScrubber(String pattern, Function1<Integer, String> replacement) {
        super(pattern, replacement);
    }

    public static DateScrubber getScrubberFor(String formattedExample) {
      String possiblePatterns[] = {"[a-zA-Z]{3} [a-zA-Z]{3} \\d{2} \\d{2}:\\d{2}:\\d{2}",
                "[a-zA-Z]{3} [a-zA-Z]{3} \\d{2} \\d{4} \\d{2}:\\d{2}:\\d{2}.\\d{2}\\d",
                "[a-zA-Z]{3} [a-zA-Z]{3} \\d{2} \\d{2}:\\d{2}:\\d{2} -\\d{4} \\d{4}",
                "\\d{2} [a-zA-Z]{3} \\d{4} \\d{2}:\\d{2}:\\d{2},\\d{2}\\d",
                "[a-zA-Z]{3} \\d{2}, \\d{4} \\d{2}:\\d{2}:\\d{2} [a-zA-Z][a-zA-Z] [a-zA-Z]{3}",
                "\\d{2}:\\d{2}:\\d{2}",
                "\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{2}\\d",
                "\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{2}Z",
                "\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{2}Z",
                "\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{2}:\\d{2}Z",
                "\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{2}\\:\\d{2}\\.\\d{3}Z",
        };
        for (String pattern : possiblePatterns) {
            DateScrubber scrubber = new DateScrubber(pattern, n -> "[Date" + n + "]");
            if ("[Date1]".equals(scrubber.scrub(formattedExample))) {
                return scrubber;
            }
        }
        throw new FormattedException("No match found for %s.\n Current supported formats are: %s", formattedExample, Arrays.asList(possiblePatterns));
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
