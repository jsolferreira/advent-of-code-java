package org.example.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.Optional;
import java.util.stream.Stream;

public class Cli {

    private static CommandLine cmd;

    private Cli() {

    }

    public static void start(String[] args) {

        final Options options = buildOptions();
        buildCommandLine(options, args);
    }

    public static Optional<String> getYear() {

        return getOption(CommandLineOption.YEAR);
    }

    public static Optional<String> getDay() {

        return getOption(CommandLineOption.DAY);
    }

    public static boolean measureTime() {

        return cmd.hasOption(CommandLineOption.TIME.getOption());
    }

    private static Optional<String> getOption(CommandLineOption commandLineOption) {

        return Optional.ofNullable(cmd.getOptionValue(commandLineOption.getOption()));
    }

    private static Options buildOptions() {

        final Options options = new Options();

        Stream.of(CommandLineOption.values())
                .map(Cli::newOption)
                .forEach(options::addOption);

        return options;
    }

    private static Option newOption(CommandLineOption commandLineOption) {

        return new Option(commandLineOption.getOption(),
                          commandLineOption.getLongOption(),
                          commandLineOption.isHasArg(),
                          commandLineOption.getDescription());
    }

    private static void buildCommandLine(Options options, String[] args) {

        final CommandLineParser parser = new DefaultParser();

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());

            System.exit(1);
        }
    }
}
