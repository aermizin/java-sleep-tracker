package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

    public class BadQualitySleepSessionCounter implements Function<List<SleepingSession>, SleepAnalysisResult<?>> {

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sleepingSessions) {

        long totalBadQuality = sleepingSessions.stream()
                .filter(session -> session.getQualitySleep().equals("BAD"))
                .count();


        return new SleepAnalysisResult<>(totalBadQuality, "Количество сессий с плохим качеством сна");
    }
}
