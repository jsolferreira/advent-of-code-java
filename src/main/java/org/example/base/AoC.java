package org.example.base;

import org.example.aoc2016.AoC2016Runner;
import org.example.aoc2020.AoC2020Runner;
import org.example.cli.Cli;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class AoC {

    private static final Map<String, Class<? extends AoCRunner>> classes = Map.of(
            "2016", AoC2016Runner.class,
            "2020", AoC2020Runner.class
    );

    public static void start() {

        Cli.getYear()
                .ifPresentOrElse(AoC::runYear, AoC::runAllYears);
    }

    private static void runYear(String year) {

        final Class<? extends AoCRunner> c = classes.get(year);

        runYear(c);
    }

    private static void runAllYears() {

        classes.values().forEach(AoC::runYear);
    }

    private static void runYear(Class<? extends AoCRunner> c) {

        try {

            final AoCRunner base = c.getDeclaredConstructor().newInstance();

            base.run();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
