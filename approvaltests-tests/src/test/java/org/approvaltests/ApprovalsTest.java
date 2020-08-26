package org.approvaltests;

import java.awt.Dimension;
import java.awt.Rectangle;

import org.approvaltests.core.Options;
import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.QuietReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

import javax.swing.JButton;

@UseReporter({DiffReporter.class, ClipboardReporter.class})
public class ApprovalsTest
{
  @Test
  public void testToString() throws Exception
  {
    Approvals.verify(new Rectangle(5, 10, 100, 200));
  }
  @Test
  public void testAsJson() throws Exception
  {
    Approvals.verifyAsJson(new Rectangle(5, 10, 100, 200));
  }
  @Test
  public void testWrongCall()
  {
    Approvals.verifyException(() -> {
      JButton b = new JButton("Approval Tests Rule");
      b.setPreferredSize(new Dimension(150, 20));
      Approvals.verify(b, new Options(new QuietReporter()));
    });
  }
}
