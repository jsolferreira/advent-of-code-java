package org.example.aoc.aoc2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

class Day17 extends AoC2022Day<List<String>> {

    private record Position(int x, int y) {
    }

    @Override
    protected List<String> parseInput(String strInput) {

        return Arrays.stream(strInput.split("")).toList();
    }

    @Override
    protected Long partOne(List<String> input) {

        return run(input, 2022);
    }

    @Override
    protected Long partTwo(List<String> input) {

        return run(input, 1000000000000L);
    }

    private Long run(List<String> jetPattern, long nRockets) {


        final List<Character[]> chamber = new ArrayList<>();

        long highestRock = -1;

        int jetPatternIndex = 0;
        long currentRockIndex = 0;
        final List<Character[][]> rocks = getRocks();

        while (currentRockIndex < nRockets) {

            // get next rock
            final Character[][] rock = rocks.get((int) currentRockIndex % rocks.size());
            currentRockIndex++;

            // Adjust chamber
            final long nextChamberSize = highestRock + 3 + rock.length + 1;
            adjustChamber(chamber, nextChamberSize);

            Position rockPosition = new Position(2, chamber.size() - 1);

            while (rockPosition != null) {

                rockPosition = pushRock(chamber, jetPattern.get(jetPatternIndex % jetPattern.size()), rock, rockPosition);
                jetPatternIndex++;

                final Position rockPositionAfterFall = fallDown(chamber, rock, rockPosition);

                if (rockPositionAfterFall == null) {
                    for (int y = 0; y < rock.length; y++) {
                        for (int x = 0; x < rock[y].length; x++) {
                            if (rock[y][x] == '#') {
                                chamber.get(rockPosition.y - y)[rockPosition.x + x] = '#';
                            }
                        }
                    }
                }

                rockPosition = rockPositionAfterFall;
            }

            highestRock = IntStream.range(0, chamber.size())
                    .map(y -> chamber.size() - y - 1)
                    .filter(y -> IntStream.range(0, chamber.get(y).length)
                            .anyMatch(x -> chamber.get(y)[x] == '#'))
                    .findFirst()
                    .orElseThrow();

            /*for (int y = chamber.size() - 1; y >= 0; y--) {

                StringBuilder sb = new StringBuilder();

                for (char aChar : chamber.get(y)) {

                    sb.append(aChar);
                }

                System.out.println(sb);
            }*/
        }

        return highestRock + 1;
    }

    private List<Character[][]> getRocks() {


        final Character[][] rock1 = {
                {'#', '#', '#', '#'}
        };

        final Character[][] rock2 = {
                {'.', '#', '.'},
                {'#', '#', '#'},
                {'.', '#', '.'}
        };

        final Character[][] rock3 = {
                {'.', '.', '#'},
                {'.', '.', '#'},
                {'#', '#', '#'}
        };

        final Character[][] rock4 = {
                {'#'},
                {'#'},
                {'#'},
                {'#'}
        };

        final Character[][] rock5 = {
                {'#', '#'},
                {'#', '#'}
        };

        return List.of(rock1, rock2, rock3, rock4, rock5);
    }

    private void adjustChamber(List<Character[]> chamber, long nextChamberSize) {

        while (chamber.size() < nextChamberSize) {
            chamber.add(new Character[]{'.', '.', '.', '.', '.', '.', '.'});
        }
        while (chamber.size() > nextChamberSize) {
            chamber.remove(chamber.size() - 1);
        }
    }

    private Position pushRock(List<Character[]> chamber, String direction, Character[][] rock, Position rockPosition) {

        if (direction.equals(">")) {

            for (int y = 0; y < rock.length; y++) {
                int x;
                for (x = rock[y].length - 1; x >= 0; x--) {
                    if (rock[y][x] == '#') {
                        break;
                    }
                }

                Position position = getPosition(chamber, rockPosition.x + x + 1, rockPosition.y - y);
                if (position == null) {

                    return rockPosition;
                }
            }

            return new Position(rockPosition.x + 1, rockPosition.y);
        } else if (direction.equals("<")) {

            for (int y = 0; y < rock.length; y++) {
                int x;
                for (x = 0; x < rock[y].length; x++) {
                    if (rock[y][x] == '#') {
                        break;
                    }
                }

                Position position = getPosition(chamber, rockPosition.x + x - 1, rockPosition.y - y);
                if (position == null) {

                    return rockPosition;
                }
            }

            return new Position(rockPosition.x - 1, rockPosition.y);
        }

        throw new RuntimeException();
    }

    private Position fallDown(List<Character[]> chamber, Character[][] rock, Position rockPosition) {

        for (int x = 0; x < rock[0].length; x++) {
            int y;
            for (y = rock.length - 1; y >= 0; y--) {
                if (rock[y][x] == '#') {
                    break;
                }
            }

            Position position = getPosition(chamber, rockPosition.x + x, rockPosition.y - y - 1);
            if (position == null) {

                return null;
            }
        }

        return new Position(rockPosition.x, rockPosition.y - 1);
    }

    private Position getPosition(List<Character[]> chamber, int x, int y) {

        if (y < 0 || y >= chamber.size() || x < 0 || x >= chamber.get(y).length || chamber.get(y)[x] == '#') {

            return null;
        }

        return new Position(x, y);
    }

    @Override
    protected String getDay() {

        return "day17";
    }
}
