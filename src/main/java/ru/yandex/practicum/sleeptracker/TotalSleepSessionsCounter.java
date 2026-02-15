package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class TotalSleepSessionsCounter implements SleepMetricCalculator<SleepAnalysisResult<?>> {

    @Override
    public SleepAnalysisResult<Integer> calculate(List<SleepingSession> sleepingSessions) {

        int result = sleepingSessions.size();
        return new SleepAnalysisResult<>(result, "Количество сессий сна");

    }
}
