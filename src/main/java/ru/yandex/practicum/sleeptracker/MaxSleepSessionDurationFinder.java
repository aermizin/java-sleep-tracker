package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;

public class MaxSleepSessionDurationFinder implements SleepMetricCalculator<SleepAnalysisResult<?>> {

    @Override
    public SleepAnalysisResult<Long> calculate(List<SleepingSession> sleepingSessions) {

        Duration maxSleepSession = sleepingSessions.stream()
                .map(session -> Duration.between(session.getStartSleep(), session.getEndSleep()))
                .max(Duration::compareTo)
                .orElse(Duration.ZERO);

        long result = maxSleepSession.toMinutes();

        return new SleepAnalysisResult<>(result, "Максимальная продолжительность сессии сна (в минутах)");
    }
}
