package org.example.aoc.aoc2015;

import java.util.Arrays;
import java.util.List;

class Day02 extends AoC2015Day<List<Day02.Present>> {

    protected record Present(int h, int l, int w) {
    }

    @Override
    protected List<Present> parseInput(String strInput) {

        return strInput.lines()
                .map(line -> Arrays.stream(line.split("x")).map(Integer::parseInt).toArray(Integer[]::new))
                .map(split -> new Present(split[0], split[1], split[2]))
                .toList();
    }

    @Override
    protected Integer partOne(List<Present> input) {

        return input.stream()
                .map(present -> {
                    int surface = 2 * present.l * present.w +
                            2 * present.w * present.h +
                            2 * present.h * present.l;

                    int area;

                    if (present.l > present.w) {
                        if (present.l > present.h) {
                            area = present.h * present.w;
                        } else {
                            area = present.l * present.w;
                        }
                    } else {
                        if (present.w > present.h) {
                            area = present.l * present.h;
                        } else {
                            area = present.w * present.l;
                        }
                    }
                    return surface + area;
                })
                .reduce(Integer::sum)
                .orElse(0);
    }

    @Override
    protected Integer partTwo(List<Present> input) {

        return input.stream()
                .map(present -> {
                    int volume = present.l * present.h * present.w;

                    int smallestPerimeter;

                    if (present.l > present.w) {
                        if (present.l > present.h) {
                            smallestPerimeter = 2 * present.h + 2 * present.w;
                        } else {
                            smallestPerimeter = 2 * present.l + 2 * present.w;
                        }
                    } else {
                        if (present.w > present.h) {
                            smallestPerimeter = 2 * present.l + 2 * present.h;
                        } else {
                            smallestPerimeter = 2 * present.w + 2 * present.l;
                        }
                    }
                    return volume + smallestPerimeter;
                })
                .reduce(Integer::sum)
                .orElse(0);
    }

    @Override
    protected String getDay() {

        return "day02";
    }
}
