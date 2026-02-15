package ru.yandex.practicum.sleeptracker;

import java.util.List;

public interface SleepMetricCalculator<R extends SleepAnalysisResult<?>> {
    R calculate(List<SleepingSession> sessions);
}
