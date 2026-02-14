package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class TotalSleepSessionsCounter implements Function<List<SleepingSession>, SleepAnalysisResult<?>> {

    @Override
    public SleepAnalysisResult<Integer> apply(List<SleepingSession> sleepingSessions) {

        int result = sleepingSessions.size();
        return new SleepAnalysisResult<>(result, "Количество сессий сна");

    }
}
