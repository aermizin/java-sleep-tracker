package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class MaxSleepSessionDurationFinder implements Function<List<SleepingSession>, SleepAnalysisResult<?>> {

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sleepingSessions) {

        Duration maxSleepSession = sleepingSessions.stream()
                .map(session -> Duration.between(session.getStartSleep(), session.getEndSleep()))
                .max(Duration::compareTo)
                .orElse(Duration.ZERO);

        long result = maxSleepSession.toMinutes();

        return new SleepAnalysisResult<>(result, "Максимальная продолжительность сессии сна (в минутах)");
    }
}
