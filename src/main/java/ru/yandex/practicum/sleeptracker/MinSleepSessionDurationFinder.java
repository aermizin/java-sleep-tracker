package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;

public class MinSleepSessionDurationFinder implements SleepMetricCalculator<SleepAnalysisResult<?>> {

    @Override
    public SleepAnalysisResult<Long> calculate(List<SleepingSession> sleepingSessions) {

        Duration minSleepSession = sleepingSessions.stream()
                .map(session -> Duration.between(session.getStartSleep(), session.getEndSleep()))
                .min(Duration::compareTo)
                .orElse(Duration.ZERO);

        long result = minSleepSession.toMinutes();

        return new SleepAnalysisResult<>(result, "Минимальная продолжительность сессии сна (в минутах)");

    }
}
