package org.example.base;

import java.io.IOException;

public interface Runnable<T> {

    T run() throws IOException;
}
