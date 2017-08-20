package ua.goit.java;

public interface Validator<T> {

    boolean isValid(T result);
}
