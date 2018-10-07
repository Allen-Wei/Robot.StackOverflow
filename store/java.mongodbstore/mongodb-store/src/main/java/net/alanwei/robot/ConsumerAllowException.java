package net.alanwei.robot;

@FunctionalInterface
public interface ConsumerAllowException<T> {
    void accept(T data) throws Throwable;
}
