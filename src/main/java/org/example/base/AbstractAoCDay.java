package org.example.base;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public abstract class AbstractDay<T, Q> implements Base {

    private final String BASE = "input";

    private final String YEAR = getYear();

    private final String DAY = getDay();

    private final String INPUT_PATH = BASE + "/" + YEAR + "/" + DAY + ".txt";

    protected T PART_ONE_RESULT;

    @Override
    public void run() throws IOException {

        final String strInput = readFileInput();

        final Q input = parseInput(strInput);

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

    protected abstract Q parseInput(String strInput);

    protected abstract T partOne(Q input);

    protected abstract T partTwo(Q input);

    protected abstract String getYear();

    protected abstract String getDay();
}
