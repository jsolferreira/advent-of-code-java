package org.example.base;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public abstract class AbstractAoCDay<T> implements Runnable {

    private static final String BASE = "input";

    private final String YEAR = getYear();

    private final String DAY = getDay();

    private final String INPUT_PATH = BASE + "/" + YEAR + "/" + DAY + ".txt";

    protected long PART_ONE_RESULT;

    @Override
    public void run() throws IOException {

        final String strInput = readFileInput();

        final T input = parseInput(strInput);

        PART_ONE_RESULT = partOne(input);

        System.out.println("Part One: " + PART_ONE_RESULT);
        System.out.println("Part Two: " + partTwo(input));
    }

    private String readFileInput() throws IOException {

        final InputStream is = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);

        if (is == null) {

            throw new IOException();
        }

        return new String(is.readAllBytes(), StandardCharsets.UTF_8).trim();
    }

    protected abstract T parseInput(String strInput);

    protected abstract long partOne(T input);

    protected abstract long partTwo(T input);

    protected abstract String getYear();

    protected abstract String getDay();
}
