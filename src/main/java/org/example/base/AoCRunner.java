package org.example.base;

import org.example.aoc.aoc2015.AoC2015;
import org.example.aoc.aoc2016.AoC2016;
import org.example.aoc.aoc2019.AoC2019;
import org.example.aoc.aoc2020.AoC2020;
import org.example.aoc.aoc2021.AoC2021;
import org.example.aoc.aoc2022.AoC2022;
import org.example.base.result.ResultsPrinter;
import org.example.base.result.YearResult;
import org.example.cli.Cli;
import org.example.exceptions.YearNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class AoCRunner {

    private static final Map<String, Class<? extends AoCYear>> classes = Map.of(
            "2015", AoC2015.class,
            "2016", AoC2016.class,
            "2019", AoC2019.class,
            "2020", AoC2020.class,
            "2021", AoC2021.class,
            "2022", AoC2022.class
    );

    private AoCRunner() {

    }

    public static void start() {

        final List<YearResult> dayResults = Cli.getYear()
                .map(year -> List.of(runYear(year)))
                .orElseGet(AoCRunner::runAllYears);

        ResultsPrinter.x(dayResults);
    }

    private static List<YearResult> runAllYears() {

        return classes.values().stream().map(AoCRunner::runYear).toList();
    }

    private static YearResult runYear(String year) {

        final Class<? extends AoCYear> c = classes.get(year);

        if (c == null) {

            throw new YearNotFoundException();
        }

        return runYear(c);
    }

    private static YearResult runYear(Class<? extends AoCYear> c) {

        try {

            final AoCYear base = c.getDeclaredConstructor().newInstance();

            return base.run();
        } catch (InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
