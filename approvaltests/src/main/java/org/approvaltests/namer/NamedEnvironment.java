package org.approvaltests.namer;

import java.util.Arrays;

import com.spun.util.logger.SimpleLogger;

public class NamedEnvironment implements AutoCloseable
{
  public NamedEnvironment(String info)
  {
    NamerFactory.additionalInformation = info;
  }
  @Override
  public void close()
  {
    NamerFactory.getAndClearAdditionalInformation();
  }
  public boolean isCurrentEnvironmentValidFor(String... environment)
  {
    if (Arrays.asList(environment).contains(NamerFactory.additionalInformation))
    {
      return true;
    }
    else
    {
      SimpleLogger
          .message(String.format("Not valid for current environment: \"%s\"", NamerFactory.additionalInformation));
      return false;
    }
  }
}
