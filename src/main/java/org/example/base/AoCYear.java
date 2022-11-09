package org.example.base;

import org.example.cli.Cli;
import org.example.exceptions.DayNotFoundException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class AoCYear implements Runnable {

    private final List<Class<? extends Runnable>> dayClasses = getDays();

    @Override
    public void run() {

        Cli.getDay()
                .map(Integer::parseInt)
                .ifPresentOrElse(this::runDay, this::runAllDays);
    }

    private void runDay(int day) {

        if (day > dayClasses.size()) {

            throw new DayNotFoundException();
        }

        final Class<? extends Runnable> c = dayClasses.get(day - 1);

        if (c == null) {

            throw new DayNotFoundException();
        }

        runDay(c);
    }

    private void runAllDays() {

        dayClasses.forEach(this::runDay);
    }

    private void runDay(Class<? extends Runnable> c) {

        try {
            final Runnable runnable = newDayInstance(c);

            runnable.run();
        } catch (NoSuchMethodException |
                 InvocationTargetException |
                 InstantiationException |
                 IllegalAccessException |
                 IOException e) {

            throw new RuntimeException(e);
        }
    }

    protected abstract List<Class<? extends Runnable>> getDays();

    protected abstract Runnable newDayInstance(Class<? extends Runnable> c) throws NoSuchMethodException,
                                                                                   InvocationTargetException,
                                                                                   InstantiationException,
                                                                                   IllegalAccessException;
}
