package org.approvaltests.legacycode;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;

public class TestCommitRevertTest
{
  public static class MethodRuleForTestCommitRevert extends TestWatchman
  {
    public static int failures = 0;
    public void failed(Throwable e, FrameworkMethod method)
    {
      failures++;
    }
  }
  @Rule
  public MethodRuleForTestCommitRevert watchman = new MethodRuleForTestCommitRevert();
  @AfterClass
  public static void after()
  {
    GitCommitOrRevert.doCommitOrRevert(MethodRuleForTestCommitRevert.failures);
  }
}
