package com.spun.util.persistence;

import com.spun.util.DateUtils;
import com.spun.util.persistence.test.MockLoader;
import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

class LoaderTest {
    @Test
    void testWithMockedData() {
        Calendar day = DateUtils.asCalendar(DateUtils.parse("2020/01/02"));
        MockLoader<List<Shift>> shifts = new MockLoader<>(Arrays.asList(new Shift("Scott", "Chef", DateUtils.setTime(day, 8, 0))));
        Approvals.verify(KitchenScheduler.print(shifts, day), new Options().forFile().withExtension(".md"));
    }

    @Test
    void testWithDatabaseAccess() {
        Calendar day = DateUtils.asCalendar(DateUtils.parse("2020/01/02"));
//        Approvals.verify(new SqlLoader.ExecutableWrapper(new LoadShiftsFromDatabase(day)));
        Approvals.verify(new LoadShiftsFromDatabase(day));
    }
}