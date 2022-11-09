package org.example;

import org.example.base.AoCRunner;
import org.example.cli.Cli;

public class Main {

    public static void main(String[] args) {

        Cli.start(args);

        AoCRunner.start();
    }
}
