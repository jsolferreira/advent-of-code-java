package org.example.base;

import org.example.cli.Cli;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Consumer;

public abstract class AoCDay<T> implements Runnable {

    private static final String BASE = "input";

    private final String YEAR = getYear();

    private final String DAY = getDay();

    private final String INPUT_PATH = BASE + "/" + YEAR + "/" + DAY + ".txt";

    protected long PART_ONE_RESULT;

    @Override
    public void run() throws IOException {

        final String strInput = readFileInput();

        final T input = parseInput(strInput);

        executePart(input, this::executePartOne);
        executePart(input, this::executePartTwo);
    }

    private String readFileInput() throws IOException {

        final InputStream is = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);

        if (is == null) {

            throw new IOException();
        }

        return new String(is.readAllBytes(), StandardCharsets.UTF_8).trim();
    }

    private void executePart(T input, Consumer<T> partConsumer) {

        if (Cli.measureTime()) {

            executeAndMeasure(input, partConsumer);
        } else {

            partConsumer.accept(input);
        }
    }

    private void executePartOne(T input) {

        PART_ONE_RESULT = partOne(input);
        System.out.println("Part One: " + PART_ONE_RESULT);
    }

    private void executePartTwo(T input) {

        System.out.println("Part Two: " + partTwo(input));
    }

    private void executeAndMeasure(T input, Consumer<T> function) {

        final Instant start = Instant.now();

        function.accept(input);

        final Instant end = Instant.now();

        final long l = Duration.between(start, end).toMillis();

        System.out.println("Duration: " + l + " milliseconds");
    }

    protected abstract T parseInput(String strInput);

    protected abstract long partOne(T input);

    protected abstract long partTwo(T input);

    protected abstract String getYear();

    protected abstract String getDay();
}
