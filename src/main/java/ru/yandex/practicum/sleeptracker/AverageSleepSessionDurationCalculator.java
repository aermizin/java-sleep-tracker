package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class AverageSleepSessionDurationCalculator implements Function<List<SleepingSession>, SleepAnalysisResult<?>> {

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sleepingSessions) {
        if (sleepingSessions.isEmpty()) {
            return new SleepAnalysisResult<>(0L, "Средняя продолжительность сессий сна (в минутах)");
        }

        Duration totalDuration = sleepingSessions.stream()
                .map(session -> Duration.between(session.getStartSleep(), session.getEndSleep()))
                .reduce(Duration.ZERO, Duration::plus);

        long result = totalDuration.toMinutes() / sleepingSessions.size();

        return new SleepAnalysisResult<>(result, "Средняя продолжительность сессий сна (в минутах)");
    }
}

