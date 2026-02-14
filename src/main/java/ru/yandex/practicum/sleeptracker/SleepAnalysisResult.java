package ru.yandex.practicum.sleeptracker;

public class SleepAnalysisResult <T> {

    private final T result;
    private final String description;

    public SleepAnalysisResult (T result, String description) {
        this.result = result;
        this.description = description;
    }

    public T getResult() {
        return result;
    }

    public String getDescription() {
        return description;
    }
}
