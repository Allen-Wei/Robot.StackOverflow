package net.alanwei.robots.utils;

@FunctionalInterface
public interface ConsumerAllowException<T> {
    void accept(T data) throws Throwable;
}
