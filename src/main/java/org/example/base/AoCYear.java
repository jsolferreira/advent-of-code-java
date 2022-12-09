package org.example.base;

import org.example.base.result.DayResult;
import org.example.base.result.YearResult;
import org.example.cli.Cli;
import org.example.exceptions.DayNotFoundException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class AoCYear implements Runnable<YearResult> {

    private final List<Class<? extends Runnable<DayResult>>> dayClasses = getDays();

    @Override
    public YearResult run() {

        final List<DayResult> dayResults = Cli.getDay()
                .map(Integer::parseInt)
                .map(day -> List.of(runDay(day)))
                .orElseGet(this::runAllDays);

        return new YearResult(getYear(), dayResults);
    }

    private List<DayResult> runAllDays() {

        return dayClasses.stream().map(this::runDay).toList();
    }

    private DayResult runDay(int day) {

        if (day > dayClasses.size()) {

            throw new DayNotFoundException();
        }

        final Class<? extends Runnable<DayResult>> c = dayClasses.get(day - 1);

        if (c == null) {

            throw new DayNotFoundException();
        }

        return runDay(c);
    }

    private DayResult runDay(Class<? extends Runnable<DayResult>> c) {

        try {
            final Runnable<DayResult> runnable = newDayInstance(c);

            return runnable.run();
        } catch (NoSuchMethodException |
                 InvocationTargetException |
                 InstantiationException |
                 IllegalAccessException |
                 IOException e) {

            throw new RuntimeException(e);
        }
    }

    protected abstract String getYear();

    protected abstract List<Class<? extends Runnable<DayResult>>> getDays();

    protected abstract Runnable<DayResult> newDayInstance(Class<? extends Runnable<DayResult>> c) throws NoSuchMethodException,
                                                                                                         InvocationTargetException,
                                                                                                         InstantiationException,
                                                                                                         IllegalAccessException;
}
