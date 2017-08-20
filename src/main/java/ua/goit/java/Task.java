package ua.goit.java;

public interface Task<T> {

    void execute();

    T getResult();
}
