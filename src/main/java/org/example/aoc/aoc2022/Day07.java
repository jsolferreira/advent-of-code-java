package org.example.aoc.aoc2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.function.Predicate.not;

class Day07 extends AoC2022Day<List<Record>> {

    private record File(String name, int size) {
    }

    private record Directory2(String name) {
    }

    private class Directory {

        String name;
        Directory previous = null;
        List<Directory> directories = new ArrayList<>();
        List<File> files = new ArrayList<>();

        public Directory(String name) {

            this.name = name;
        }
    }

    private record ListDirectory(List<Record> list) {
    }

    private record ChangeDirectory(String name) {
    }

    @Override
    protected List<Record> parseInput(String strInput) {

        final Pattern cdPattern = Pattern.compile(".*cd (.*)");
        final Pattern directoryPattern = Pattern.compile("dir (.*)");
        final Pattern filePattern = Pattern.compile("(\\d+) (.*)");

        return Arrays.stream(strInput.split("\\$ "))
                .filter(not(String::isBlank))
                .map(line -> {

                    final Matcher cdMatcher = cdPattern.matcher(line);

                    if (cdMatcher.find()) {

                        return new ChangeDirectory(cdMatcher.group(1));
                    }

                    final List<Record> records = line.lines()
                            .skip(1)
                            .map(x -> {

                                final Matcher directoryMatcher = directoryPattern.matcher(x);

                                if (directoryMatcher.find()) {

                                    return new Directory2(directoryMatcher.group(1));
                                }

                                final Matcher fileMatcher = filePattern.matcher(x);

                                if (fileMatcher.find()) {

                                    return new File(fileMatcher.group(2), Integer.parseInt(fileMatcher.group(1)));
                                }

                                throw new RuntimeException();
                            })
                            .toList();

                    return new ListDirectory(records);
                })
                .toList();
    }

    @Override
    protected Long partOne(List<Record> input) {

        final Directory root = new Directory("/");

        auxRec(root, input, 1);

        List<Directory> flat = flat(root);

        return flat.stream()
                .mapToLong(this::size)
                .filter(size -> size < 100000)
                .sum();
    }

    private void auxRec(Directory currentDirectory, List<Record> instructions, int index) {

        if (index >= instructions.size()) {

            return;
        }

        final Record record = instructions.get(index);

        if (record instanceof ChangeDirectory changeDirectory) {

            if (changeDirectory.name.equals("..")) {

                auxRec(currentDirectory.previous, instructions, index + 1);
            } else {

                Directory directory1 = currentDirectory.directories.stream()
                        .filter(directory -> directory.name.equals(changeDirectory.name))
                        .findFirst()
                        .orElseThrow();

                directory1.previous = currentDirectory;

                auxRec(directory1, instructions, index + 1);
            }
        } else if (record instanceof ListDirectory listDirectory) {

            for (Record record1 : listDirectory.list) {

                if (record1 instanceof Directory2 directory2) {

                    Directory directory = new Directory(directory2.name);
                    currentDirectory.directories.add(directory);
                } else if (record1 instanceof File file) {

                    currentDirectory.files.add(file);
                }
            }

            auxRec(currentDirectory, instructions, index + 1);
        }
    }

    private List<Directory> flat(Directory directory) {

        if (directory.directories.isEmpty()) {

            return List.of(directory);
        }

        ArrayList<Directory> directories = new ArrayList<>();

        directories.add(directory);
        for (Directory directory1 : directory.directories) {

            directories.addAll(flat(directory1));
        }

        return directories;
    }

    private int size(Directory directory) {

        int sum = 0;

        for (File file : directory.files) {
            sum += file.size;
        }

        for (Directory directory1 : directory.directories) {

            sum += size(directory1);
        }

        return sum;
    }

    @Override
    protected Long partTwo(List<Record> input) {

        final Directory root = new Directory("/");

        auxRec(root, input, 1);

        int unusedSpace = size(root);
        int toDelete = 30000000 - (70000000 - unusedSpace);

        List<Directory> flat = flat(root);

        return flat.stream()
                .mapToLong(this::size)
                .filter(size -> size >= toDelete)
                .sorted()
                .findFirst()
                .orElseThrow();
    }

    @Override
    protected String getDay() {

        return "day07";
    }
}
