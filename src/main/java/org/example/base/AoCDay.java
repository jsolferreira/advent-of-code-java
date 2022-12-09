package org.example.base;

import org.example.base.result.DayResult;
import org.example.base.result.PartResult;
import org.example.cli.Cli;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;

public abstract class AoCDay<T> implements Runnable<DayResult> {

    protected Object partOneResult;

    @Override
    public DayResult run() throws IOException {

        final String strInput = readFileInput();

        final T input = parseInput(strInput);

        final PartResult part1Result = executePart(input, this::executePartOne);
        final PartResult part2Result = executePart(input, this::executePartTwo);

        return new DayResult(getDay(), part1Result.result(), part1Result.duration(), part2Result.result(), part2Result.duration());
    }

    private String readFileInput() throws IOException {

        final String base = "input";
        final String year = getYear();
        final String day = getDay();

        final String inputPath = base + File.separator + year + File.separator + day + ".txt";

        final InputStream is = getClass().getClassLoader().getResourceAsStream(inputPath);

        if (is == null) {

            throw new IOException();
        }

        return new String(is.readAllBytes(), StandardCharsets.UTF_8).stripTrailing();
    }

    private PartResult executePart(T input, Function<T, Object> partConsumer) {

        if (Cli.measureTime()) {

            return executeAndMeasure(input, partConsumer);
        } else {

            return new PartResult(partConsumer.apply(input), null);
        }
    }

    private Object executePartOne(T input) {

        partOneResult = partOne(input);

        return partOneResult;
    }

    private Object executePartTwo(T input) {

        return partTwo(input);
    }

    private PartResult executeAndMeasure(T input, Function<T, Object> function) {

        final Instant start = Instant.now();

        final Object result = function.apply(input);

        final Instant end = Instant.now();

        final long l = Duration.between(start, end).toMillis();

        return new PartResult(result, l);
    }

    protected abstract T parseInput(String strInput);

    protected abstract Object partOne(T input);

    protected abstract Object partTwo(T input);

    protected abstract String getYear();

    protected abstract String getDay();
}
