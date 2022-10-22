package org.example.base;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class AbstractYearRunner implements Runnable {

    private final List<Class<? extends Runnable>> dayClasses = getDays();

    @Override
    public void run() {

        try {
            for (Class<? extends Runnable> dayClass : dayClasses) {
                final Runnable runnable = newInstance(dayClass);

                runnable.run();
            }
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract List<Class<? extends Runnable>> getDays();

    protected abstract Runnable newInstance(Class<? extends Runnable> c) throws NoSuchMethodException,
                                                                                InvocationTargetException,
                                                                                InstantiationException,
                                                                                IllegalAccessException;
}
