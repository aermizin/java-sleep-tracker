package ru.yandex.practicum.sleeptracker;

import java.util.List;

    public class BadQualitySleepSessionCounter implements SleepMetricCalculator<SleepAnalysisResult<?>> {

    @Override
    public SleepAnalysisResult<Long> calculate(List<SleepingSession> sleepingSessions) {

        long totalBadQuality = sleepingSessions.stream()
                .filter(session -> session.getQualitySleep().equals("BAD"))
                .count();

        return new SleepAnalysisResult<>(totalBadQuality, "Количество сессий с плохим качеством сна");
    }
}
