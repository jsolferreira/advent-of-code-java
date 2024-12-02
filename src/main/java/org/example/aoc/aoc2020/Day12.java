package org.example.aoc.aoc2020;

import java.util.List;

class Day12 extends AoC2020Day<List<Day12.Instruction>> {

    private interface Action {
    }

    private enum TurnAction implements Action {
        L,
        R
    }

    private enum MoveAction implements Action {
        N,
        S,
        E,
        W,
        F
    }

    private enum Direction {
        N,
        S,
        E,
        W
    }

    protected record Instruction(Action action, int value) {
    }

    private static class Ship {

        private int x = 0;
        private int y = 0;

        private Direction direction = Direction.E;

        Ship() {
        }

        public void runInstruction(Instruction instruction) {

            final Action action = instruction.action;

            if (action instanceof TurnAction turnAction) {

                turn(turnAction, instruction.value);
            } else if (action instanceof MoveAction moveAction) {

                move(moveAction, instruction.value);
            }
        }

        private void move(MoveAction moveAction, int value) {

            if (moveAction == MoveAction.N) {

                moveNorth(value);
            } else if (moveAction == MoveAction.E) {

                moveEast(value);
            } else if (moveAction == MoveAction.S) {

                moveSouth(value);
            } else if (moveAction == MoveAction.W) {

                moveWest(value);
            } else if (moveAction == MoveAction.F) {

                moveForward(value);
            }
        }

        private void moveNorth(int value) {

            y += value;
        }

        private void moveSouth(int value) {

            y -= value;
        }

        private void moveEast(int value) {

            x += value;
        }

        private void moveWest(int value) {

            x -= value;
        }

        private void moveForward(int value) {

            if (direction == Direction.N) {

                moveNorth(value);
            } else if (direction == Direction.E) {

                moveEast(value);
            } else if (direction == Direction.S) {

                moveSouth(value);
            } else if (direction == Direction.W) {

                moveWest(value);
            }
        }

        private void turn(TurnAction turnAction, int value) {

            if (turnAction == TurnAction.R) {

                turnRight(value);
            } else if (turnAction == TurnAction.L) {

                turnLeft(value);
            }
        }

        public void turnRight(int value) {

            while (value > 0) {

                if (direction == Direction.N) {

                    direction = Direction.E;
                } else if (direction == Direction.E) {

                    direction = Direction.S;
                } else if (direction == Direction.S) {

                    direction = Direction.W;
                } else {

                    direction = Direction.N;
                }

                value -= 90;
            }
        }

        public void turnLeft(int value) {

            while (value > 0) {

                if (direction == Direction.N) {

                    direction = Direction.W;
                } else if (direction == Direction.E) {

                    direction = Direction.N;
                } else if (direction == Direction.S) {

                    direction = Direction.E;
                } else {

                    direction = Direction.S;
                }

                value -= 90;
            }
        }

        public long getManhattanDistance() {

            return Math.abs(x) + Math.abs(y);
        }
    }

    private static class ShipAndWaypoint {

        private int shipX = 0;
        private int shipY = 0;

        private int waypointX = 10;
        private int waypointY = 1;

        ShipAndWaypoint() {
        }

        public void runInstruction(Instruction instruction) {

            final Action action = instruction.action;

            if (action instanceof TurnAction turnAction) {

                turn(turnAction, instruction.value);
            } else if (action instanceof MoveAction moveAction) {

                move(moveAction, instruction.value);
            }
        }

        private void move(MoveAction moveAction, int value) {

            if (moveAction == MoveAction.N) {

                moveNorth(value);
            } else if (moveAction == MoveAction.E) {

                moveEast(value);
            } else if (moveAction == MoveAction.S) {

                moveSouth(value);
            } else if (moveAction == MoveAction.W) {

                moveWest(value);
            } else if (moveAction == MoveAction.F) {

                moveForward(value);
            }
        }

        private void moveNorth(int value) {

            waypointY += value;
        }

        private void moveSouth(int value) {

            waypointY -= value;
        }

        private void moveEast(int value) {

            waypointX += value;
        }

        private void moveWest(int value) {

            waypointX -= value;
        }

        private void moveForward(int value) {

            shipX += waypointX * value;
            shipY += waypointY * value;
        }

        private void turn(TurnAction turnAction, int value) {

            if (turnAction == TurnAction.R) {

                turnRight(value);
            } else if (turnAction == TurnAction.L) {

                turnLeft(value);
            }
        }

        public void turnRight(int value) {

            while (value > 0) {

                final int aux = waypointY;
                waypointY = waypointX * -1;
                waypointX = aux;

                value -= 90;
            }
        }

        public void turnLeft(int value) {

            while (value > 0) {

                final int aux = waypointX;
                waypointX = waypointY * -1;
                waypointY = aux;

                value -= 90;
            }
        }

        public long getManhattanDistance() {

            return Math.abs(shipX) + Math.abs(shipY);
        }
    }

    @Override
    protected List<Instruction> parseInput(String strInput) {

        return strInput.lines()
                .map(line -> {

                    final Action action = parseAction(line.substring(0, 1));
                    final int value = Integer.parseInt(line.substring(1));

                    return new Instruction(action, value);
                })
                .toList();
    }

    private Action parseAction(String action) {

        try {

            return TurnAction.valueOf(action);
        } catch (IllegalArgumentException ignore) {

            return MoveAction.valueOf(action);
        }
    }

    @Override
    protected Long partOne(List<Instruction> input) {

        final Ship ship = new Ship();

        input.forEach(ship::runInstruction);

        return ship.getManhattanDistance();
    }

    @Override
    protected Long partTwo(List<Instruction> input) {

        final ShipAndWaypoint ship = new ShipAndWaypoint();

        input.forEach(ship::runInstruction);

        return ship.getManhattanDistance();
    }

    @Override
    protected String getDay() {

        return "day12";
    }
}
