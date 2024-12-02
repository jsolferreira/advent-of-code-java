package org.example.aoc.aoc2015;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.IntStream;

class Day04 extends AoC2015Day<String> {

    @Override
    protected String parseInput(String strInput) {

        return strInput;
    }

    @Override
    protected Integer partOne(String input) {

        return run(input, 5);
    }

    @Override
    protected Integer partTwo(String input) {

        return run(input, 6);
    }

    private Integer run(String input, int nZeroes) {

        return IntStream.iterate(0, i -> i + 1)
                .filter(i -> this.startsWithNZeroes(input + i, nZeroes))
                .findFirst()
                .orElseThrow();
    }

    private boolean startsWithNZeroes(String input, int nZeroes) {

        final byte[] bytes = md5(input);

        int foundZeroes = 0;

        for (byte aByte : bytes) {

            final String hexadecimal = String.format("%02X", aByte);

            if (hexadecimal.equals("00")) {
                foundZeroes += 2;
            } else if (hexadecimal.startsWith("0")) {
                foundZeroes += 1;
                break;
            } else {
                break;
            }
        }

        return foundZeroes == nZeroes;
    }

    private byte[] md5(String input) {

        try {
            final MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(input.getBytes(StandardCharsets.UTF_8));
            return m.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected String getDay() {

        return "day04";
    }
}
