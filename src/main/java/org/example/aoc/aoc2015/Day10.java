package org.example.aoc.aoc2015;

class Day10 extends AoC2015Day<String> {

    @Override
    protected String parseInput(String strInput) {

        return strInput;
    }

    @Override
    protected Integer partOne(String input) {

        return run(input, 40);
    }

    @Override
    protected Integer partTwo(String input) {

        return run(input, 50);
    }

    private int run(String input, int times) {

        String previousValue = input;

        for (int i = 0; i < times; i++) {

            Character previousCharacter = null;
            int nCharacters = 1;
            final StringBuilder sb = new StringBuilder();

            for (char character : previousValue.toCharArray()) {

                if (previousCharacter == null) {

                    previousCharacter = character;
                } else {

                    if (previousCharacter == character) {

                        nCharacters++;
                    } else {

                        sb.append(nCharacters).append(previousCharacter);
                        previousCharacter = character;
                        nCharacters = 1;
                    }
                }
            }

            sb.append(nCharacters).append(previousCharacter);
            previousValue = sb.toString();
        }

        return previousValue.length();
    }

    @Override
    protected String getDay() {

        return "day10";
    }
}
