package ua.goit.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SerialExecutor<T> implements Executor<T> {

    private static final Validator<Object> DEFAULT_VALIDATOR = value -> true;

    private List<TaskAndValidator<T>> tasks = new ArrayList<>();
    private List<T> validResults = new ArrayList<>();
    private List<T> invalidResults = new ArrayList<>();

    private boolean executed = false;

    private Map<Task<? extends T>, Validator<? super T>> map = new HashMap<>();

    public void addTask(Task<? extends T> task) {
        checkNotExecuted();
        addTask(task, DEFAULT_VALIDATOR);
    }

    public void addTask(Task<? extends T> task, Validator<? super T> validator) {
        tasks.add(new TaskAndValidator<>(task, validator));

    }

    public void execute() {
        checkNotExecuted();
        for (TaskAndValidator<T> taskAndValidator : tasks) {
            Task<? extends T> task = taskAndValidator.task;
            task.execute();
            if (taskAndValidator.validator.isValid(task.getResult())) {
                validResults.add(task.getResult());
            } else {
                invalidResults.add(task.getResult());
            }
        }
        executed = true;
    }

    public List<T> getValidResults() {
        checkExecuted();
        return validResults;
    }

    public List<T> getInvalidResults() {
        checkExecuted();
        return invalidResults;
    }

    private void checkNotExecuted() {
        if (executed){
            throw new IllegalStateException("Executor already executed");
        }
    }

    private void checkExecuted() {
        if (!executed){
            throw new IllegalStateException("Executor already executed");
        }
    }

    private static class TaskAndValidator<T> {
        private Task<? extends T> task;
        private Validator<? super T> validator;

        public TaskAndValidator(Task<? extends T> task, Validator<? super T> validator) {
            this.task = task;
            this.validator = validator;
        }
    }
}
