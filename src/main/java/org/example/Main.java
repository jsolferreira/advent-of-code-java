package org.example;

import org.example.aoc2016.AoC2016;
import org.example.aoc2020.AoC2020Runner;
import org.example.base.AbstractYearRunner;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Main {

    private static final List<Class<? extends AbstractYearRunner>> classes = List.of(
            AoC2016.class,
            AoC2020Runner.class
    );

    public static void main(String[] args) {

        classes.forEach(c -> {
            try {

                AbstractYearRunner base = c.getDeclaredConstructor().newInstance();

                base.run();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
    }
}