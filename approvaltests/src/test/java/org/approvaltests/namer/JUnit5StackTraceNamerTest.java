package org.approvaltests.namer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class JUnit5StackTraceNamerTest
{
  @Test
  public void testGetApprovalName()
  {
    StackTraceNamerUtils.assertNamerForFramework(getClass().getSimpleName(), "testGetApprovalName");
  }
  @ParameterizedTest
  @ValueSource(strings = {"A", "B"})
  public void parameterizedTest(String input)
  {
    StackTraceNamerUtils.assertParameterizedTest(getClass().getSimpleName(), "parameterizedTest", input);
  }
  @Nested
  class NestedTests
  {
    @Test
    void nestedTest()
    {
      String className = JUnit5StackTraceNamerTest.class.getSimpleName() + "." + getClass().getSimpleName();
      StackTraceNamerUtils.assertApprovalName(className, "nestedTest");
    }
  }
  @Test
  @DisplayName("hello")
  public void testWithDisplayName()
  {
    StackTraceNamerUtils.assertNamerForFramework(getClass().getSimpleName(), "testWithDisplayName");
  }
  @RepeatedTest(2)
  public void repeatedTest(RepetitionInfo repetitionInfo)
  {
    StackTraceNamerUtils.assertNamerForFramework(getClass().getSimpleName(), "repeatedTest");
  }
}
