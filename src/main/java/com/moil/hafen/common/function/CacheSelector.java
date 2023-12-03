package com.moil.hafen.common.function;

@FunctionalInterface
public interface CacheSelector<T> {
    T select() throws Exception;
}
