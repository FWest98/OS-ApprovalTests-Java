package com.spun.util.date.test;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.approvaltests.Approvals;
import org.lambda.query.Query;

import com.spun.util.DateUtils;
import com.spun.util.date.DateRange;

import junit.framework.TestCase;

public class DateRangeTest extends TestCase
{

  public void testIsIn() throws Exception
  {
    DateRange range = new DateRange(date(40), date(20));
    assertTrue(range.contains(date(40)));
    assertTrue(range.contains(date(30)));
    assertFalse(range.contains(date(20)));
    assertFalse(range.contains(date(10)));
  }

  public void testFilter() throws Exception
  {
    DateRange range = new DateRange(quickDate(20), quickDate(40));
    Timestamp dates[] = {quickDate(50), quickDate(40), quickDate(30), quickDate(20), quickDate(10)};
    Approvals.verifyAll("Dates", Query.where(dates, d -> range.contains(d)));
  }

  public static Timestamp quickDate(int daysPastNewYears)
  {
    Timestamp start = DateUtils.parse("2001/01/01");
    Calendar calendar = DateUtils.asCalendar(start);
    calendar.add(GregorianCalendar.DAY_OF_YEAR, daysPastNewYears);
    return DateUtils.asTimestamp(calendar.getTime());
  }

  public static Timestamp date(int daysAgo)
  {
    return DateUtils.getStartOfXDaysAgo(daysAgo);
  }

  public void testGetWeeks() throws Exception
  {
    DateRange d = new DateRange(DateUtils.parse("2008/10/01"), DateUtils.parse("2008/11/01"));
    Approvals.verifyAll("week", d.getWeeks());
  }

  public void testContainsDayOfWeek() throws Exception
  {
    DateRange d = new DateRange(DateUtils.parse("2008/11/11"), DateUtils.parse("2008/11/15"));
    assertTrue(d.containsDayOfWeek(Calendar.THURSDAY));
    assertFalse(d.containsDayOfWeek(Calendar.MONDAY));
  }

  public void testGetFirstDayOfWeek() throws Exception
  {
    DateRange d = new DateRange(DateUtils.parse("2008/11/11"), DateUtils.parse("2008/11/15"));
    Date expected = new Date(DateUtils.parse("2008/11/13").getTime());
    assertEquals(expected, d.getFirst(Calendar.THURSDAY));
  }

  public void testGetMonths() throws Exception
  {
    DateRange d = new DateRange(DateUtils.parse("2008/01/01"), DateUtils.parse("2009/01/01"));
    Approvals.verifyAll("months", d.getMonths());
  }

  public void testGetQuarters() throws Exception
  {
    DateRange d = new DateRange(DateUtils.parse("2008/01/01"), DateUtils.parse("2009/01/01"));
    Approvals.verifyAll("months", d.getQuarters());
  }

  public void testGetRangeContaining() throws Exception
  {
    DateRange d = new DateRange(DateUtils.parse("2008/01/01"), DateUtils.parse("2009/01/01"));
    DateRange containing = DateRange.getRangeContaining(d.getQuarters(), d.getMonths()[0]);
    Approvals.verify(containing.toString());
  }


}
