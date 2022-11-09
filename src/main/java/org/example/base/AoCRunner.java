package org.example.base;

import org.example.aoc.aoc2016.AoC2016;
import org.example.aoc.aoc2019.AoC2019;
import org.example.aoc.aoc2020.AoC2020;
import org.example.cli.Cli;
import org.example.exceptions.YearNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class AoCRunner {

    private static final Map<String, Class<? extends AoCYear>> classes = Map.of(
            "2016", AoC2016.class,
            "2019", AoC2019.class,
            "2020", AoC2020.class
    );

    private AoCRunner() {
    }

    public static void start() {

        Cli.getYear()
                .ifPresentOrElse(AoCRunner::runYear, AoCRunner::runAllYears);
    }

    private static void runYear(String year) {

        final Class<? extends AoCYear> c = classes.get(year);

        if (c == null) {

            throw new YearNotFoundException();
        }

        runYear(c);
    }

    private static void runAllYears() {

        classes.values().forEach(AoCRunner::runYear);
    }

    private static void runYear(Class<? extends AoCYear> c) {

        try {

            final AoCYear base = c.getDeclaredConstructor().newInstance();

            base.run();
        } catch (InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
