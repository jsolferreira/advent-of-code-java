package org.example.base;

import org.example.cli.Cli;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Consumer;

public abstract class AoCDay<T> implements Runnable {

    protected long partOneResult;

    @Override
    public void run() throws IOException {

        final String strInput = readFileInput();

        final T input = parseInput(strInput);

        executePart(input, this::executePartOne);
        executePart(input, this::executePartTwo);
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

        partOneResult = partOne(input);
        System.out.println("Part One: " + partOneResult);
    }

    private void executePartTwo(T input) {

        final long partTwoResult = partTwo(input);

        System.out.println("Part Two: " + partTwoResult);
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
