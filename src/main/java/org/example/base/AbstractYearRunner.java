package org.example.base;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class AbstractYear implements Base {

    private final List<Class<? extends Base>> dayClasses = getDays();

    @Override
    public void run() {

        try {
            for (Class<? extends Base> dayClass : dayClasses) {
                final Base base = newInstance(dayClass);

                base.run();
            }
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract List<Class<? extends Base>> getDays();

    protected abstract Base newInstance(Class<? extends Base> c) throws NoSuchMethodException,
                                                                        InvocationTargetException,
                                                                        InstantiationException,
                                                                        IllegalAccessException;
}
