package org.example.aoc.aoc2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

class Day07 extends AoC2022Day<Day07.Directory> {

    private record File(String name, long size) {
    }

    protected static class Directory {

        String name;
        Directory previous = null;
        List<Directory> directories = new ArrayList<>();
        List<File> files = new ArrayList<>();

        public Directory(String name) {

            this.name = name;
        }
    }

    private interface Instruction {
    }

    private record ListDirectory(List<Directory> directories, List<File> files) implements Instruction {
    }

    private record ChangeDirectory(String name) implements Instruction {
    }

    @Override
    protected Directory parseInput(String strInput) {

        final Pattern cdPattern = Pattern.compile(".*cd (.*)");
        final Pattern directoryPattern = Pattern.compile("dir (.*)");
        final Pattern filePattern = Pattern.compile("(\\d+) (.*)");

        final List<? extends Instruction> input = Arrays.stream(strInput.split("\\$ "))
                .filter(not(String::isBlank))
                .map(line -> {

                    final Matcher cdMatcher = cdPattern.matcher(line);

                    if (cdMatcher.find()) {

                        return new ChangeDirectory(cdMatcher.group(1));
                    }

                    final List<Directory> directories = line.lines()
                            .map(directoryPattern::matcher)
                            .filter(Matcher::find)
                            .map(matcher -> new Directory(matcher.group(1)))
                            .toList();

                    final List<File> files = line.lines()
                            .map(filePattern::matcher)
                            .filter(Matcher::find)
                            .map(matcher -> new File(matcher.group(2), Long.parseLong(matcher.group(1))))
                            .toList();

                    return new ListDirectory(directories, files);
                })
                .toList();

        final Directory root = new Directory("/");

        parseDirectories(root, input, 1);

        return root;
    }

    private void parseDirectories(Directory currentDirectory, List<? extends Instruction> instructions, int index) {

        if (index >= instructions.size()) {

            return;
        }

        final Instruction instruction = instructions.get(index);

        if (instruction instanceof ChangeDirectory changeDirectory) {

            if (changeDirectory.name.equals("..")) {

                parseDirectories(currentDirectory.previous, instructions, index + 1);
            } else {

                final Directory directory = currentDirectory.directories.stream()
                        .filter(subDirectory -> subDirectory.name.equals(changeDirectory.name))
                        .findFirst()
                        .orElseThrow();

                directory.previous = currentDirectory;

                parseDirectories(directory, instructions, index + 1);
            }
        } else if (instruction instanceof ListDirectory listDirectory) {

            currentDirectory.directories.addAll(listDirectory.directories);
            currentDirectory.files.addAll(listDirectory.files);

            parseDirectories(currentDirectory, instructions, index + 1);
        }
    }

    @Override
    protected Long partOne(Directory input) {

        return flat(input).stream()
                .mapToLong(this::size)
                .filter(size -> size < 100000)
                .sum();
    }

    @Override
    protected Long partTwo(Directory input) {

        long toDelete = 30000000 - (70000000 - size(input));

        return flat(input).stream()
                .mapToLong(this::size)
                .filter(size -> size >= toDelete)
                .sorted()
                .findFirst()
                .orElseThrow();
    }

    private List<Directory> flat(Directory directory) {

        return Stream.concat(
                        Stream.of(directory),
                        directory.directories.stream()
                                .map(this::flat)
                                .flatMap(Collection::stream))
                .toList();
    }

    private long size(Directory directory) {

        final long filesSize = directory.files.stream()
                .mapToLong(File::size)
                .sum();

        final long directoriesSize = directory.directories.stream()
                .mapToLong(this::size)
                .sum();

        return filesSize + directoriesSize;
    }

    @Override
    protected String getDay() {

        return "day07";
    }
}
