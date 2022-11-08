package org.example;

import org.example.aoc2016.AoC2016Runner;
import org.example.aoc2020.AoC2020Runner;
import org.example.base.AoCRunner;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Main {

    private static final List<Class<? extends AoCRunner>> classes = List.of(
            AoC2016Runner.class,
            AoC2020Runner.class
    );

    public static void main(String[] args) {

        classes.forEach(c -> {
            try {

                AoCRunner base = c.getDeclaredConstructor().newInstance();

                base.run();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
    }
}