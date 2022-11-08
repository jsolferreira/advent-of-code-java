package org.example.cli;

enum CommandLineOption {
    YEAR("y", "year", true, "aoc year"),
    DAY("D", "day", true, "aoc day"),
    TIME("t", "time", false, "measure time");

    private final String option;

    private final String longOption;

    private final boolean hasArg;

    private final String description;

    CommandLineOption(String option, String longOption, boolean hasArg, String description) {

        this.option = option;
        this.longOption = longOption;
        this.hasArg = hasArg;
        this.description = description;
    }

    public String getOption() {

        return option;
    }

    public String getLongOption() {

        return longOption;
    }

    public boolean isHasArg() {

        return hasArg;
    }

    public String getDescription() {

        return description;
    }
}
