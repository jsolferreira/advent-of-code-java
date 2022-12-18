package org.example.aoc.aoc2022;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

class Day18 extends AoC2022Day<List<Day18.Position>> {

    protected record Position(int x, int y, int z) {
    }

    private record Cube(Set<Position> vertices) {
    }

    private record ReduceResult(int faces, List<Cube> cubes) {
    }

    @Override
    protected List<Position> parseInput(String strInput) {

        return strInput.lines()
                .map(line -> line.split(","))
                .map(split -> new Position(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])))
                .toList();
    }

    @Override
    protected Integer partOne(List<Position> input) {

        final List<Cube> cubes = input.stream()
                .map(cube -> new Cube(Set.of(
                        new Position(cube.x, cube.y, cube.z),
                        new Position(cube.x + 1, cube.y, cube.z),
                        new Position(cube.x, cube.y + 1, cube.z),
                        new Position(cube.x, cube.y, cube.z + 1),
                        new Position(cube.x + 1, cube.y + 1, cube.z),
                        new Position(cube.x + 1, cube.y, cube.z + 1),
                        new Position(cube.x, cube.y + 1, cube.z + 1),
                        new Position(cube.x + 1, cube.y + 1, cube.z + 1))))
                .toList();

        final ReduceResult reduceResult = cubes.stream()
                .reduce(null,
                        (aux, val) -> {
                            if (aux == null) {

                                return new ReduceResult(6, List.of(val));
                            }

                            final long numberOfFacesConnected = aux.cubes.stream()
                                    .filter(cube -> cube.vertices.stream()
                                            .filter(val.vertices::contains)
                                            .count() == 4)
                                    .count();

                            return new ReduceResult((int) ((aux.faces - numberOfFacesConnected) + (6 - numberOfFacesConnected)),
                                                    Stream.concat(
                                                                    aux.cubes.stream(),
                                                                    Stream.of(val))
                                                            .toList());
                        },
                        (a, b) -> a);

        return reduceResult.faces;
    }

    @Override
    protected Integer partTwo(List<Position> input) {

        return null;
    }

    @Override
    protected String getDay() {

        return "day18";
    }
}
