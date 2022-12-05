package org.example.aoc.aoc2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Day14 extends AoC2020Day<List<? extends Record>> {

    private record Mask(Map<Integer, Character> value, List<Integer> floatingPositions) {
    }

    private record Write(long address, long value) {
    }

    @Override
    protected List<? extends Record> parseInput(String strInput) {

        final Pattern maskPattern = Pattern.compile("mask = (.*)");
        final Pattern memPattern = Pattern.compile("mem\\[(\\d+)] = (\\d+)");

        return strInput.lines()
                .map(line -> {

                    final Matcher maskMatcher = maskPattern.matcher(line);

                    if (maskMatcher.matches()) {

                        final String mask = maskMatcher.group(1);

                        final Map<Integer, Character> collect = IntStream.range(0, mask.length())
                                .filter(i -> mask.charAt(mask.length() - i - 1) != 'X')
                                .boxed()
                                .collect(Collectors.toMap(Function.identity(), i -> mask.charAt(mask.length() - i - 1)));

                        final List<Integer> xPositions = IntStream.range(0, mask.length())
                                .filter(i -> mask.charAt(mask.length() - i - 1) == 'X')
                                .boxed()
                                .toList();

                        return new Mask(collect, xPositions);
                    } else {

                        final Matcher memMatcher = memPattern.matcher(line);

                        if (memMatcher.matches()) {

                            return new Write(Long.parseLong(memMatcher.group(1)), Long.parseLong(memMatcher.group(2)));
                        }
                    }

                    throw new RuntimeException();
                })
                .toList();
    }

    @Override
    protected Long partOne(List<? extends Record> input) {

        Mask currentMask = null;
        final Map<Long, Long> memory = new HashMap<>();

        for (Record instruction : input) {

            if (instruction instanceof Mask mask) {

                currentMask = mask;
            } else if (instruction instanceof Write write) {

                final long value = currentMask == null ? write.value : applyMask(currentMask, write.value);

                memory.put(write.address, value);
            }
        }

        return memory.values().stream()
                .reduce(0L, Long::sum);
    }

    private long applyMask(Mask mask, long value) {

        final String binary = Long.toBinaryString(value);

        final long baseResult = IntStream.range(0, binary.length())
                .filter(i -> mask.value.getOrDefault(i, binary.charAt(binary.length() - i - 1)) == '1')
                .mapToLong(i -> (long) Math.pow(2, i))
                .sum();

        return applyRemainingMask(mask, binary, baseResult);
    }

    private long applyRemainingMask(Mask mask, String binary, long baseResult) {

        return mask.value.entrySet().stream()
                .filter(entry -> entry.getValue() == '1' && entry.getKey() >= binary.length())
                .reduce(baseResult,
                        (acc, val) -> acc + (long) Math.pow(2, val.getKey()),
                        Long::sum);
    }

    @Override
    protected Long partTwo(List<? extends Record> input) {

        Mask currentMask = null;
        final Map<Long, Long> memory = new HashMap<>();

        for (Record instruction : input) {

            if (instruction instanceof Mask mask) {

                currentMask = mask;
            } else if (instruction instanceof Write write) {

                if (currentMask == null) {

                    memory.put(write.address, write.value);
                } else {

                    getMemoryAddresses(currentMask, write.address)
                            .forEach(address -> memory.put(address, write.value));
                }
            }
        }

        return memory.values()
                .stream()
                .reduce(0L, Long::sum);
    }

    private List<Long> getMemoryAddresses(Mask mask, long address) {

        final String binary = Long.toBinaryString(address);

        final long baseResult = IntStream.range(0, binary.length())
                .filter(i -> {
                    if (mask.value.containsKey(i)) {

                        return mask.value.get(i) == '1' || binary.charAt(binary.length() - i - 1) == '1';
                    } else {

                        return false;
                    }
                })
                .mapToLong(i -> (long) Math.pow(2, i))
                .sum();

        final Long result = applyRemainingMask(mask, binary, baseResult);

        return getMemoryOffsetCombinations(mask.floatingPositions).stream()
                .map(floatingMemoryAddress -> floatingMemoryAddress + result)
                .toList();
    }

    private List<Long> getMemoryOffsetCombinations(List<Integer> floatingPositions) {

        List<Long> memoryOffsets = new ArrayList<>();
        memoryOffsets.add(0L);

        for (int floatingPosition : floatingPositions) {

            final List<Long> offsets = memoryOffsets.stream()
                    .map(i -> i + (long) Math.pow(2, floatingPosition))
                    .toList();

            memoryOffsets.addAll(offsets);
        }

        return memoryOffsets;
    }

    @Override
    protected String getDay() {

        return "day14";
    }
}
