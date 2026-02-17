package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ChronotypeClassifier implements SleepMetricCalculator<SleepAnalysisResult<?>> {

    public static final LocalTime LATE_BEDTIME = LocalTime.of(23, 0);
    public static final LocalTime LATE_WAKEUP = LocalTime.of(9, 0);
    public static final LocalTime EARLY_BEDTIME = LocalTime.of(22, 0);
    public static final LocalTime EARLY_WAKEUP = LocalTime.of(7, 0);
    public static final LocalTime EVENING_SESSION_START = LocalTime.of(18, 0);

    @Override
    public SleepAnalysisResult<String> calculate(List<SleepingSession> sleepingSessions) {

        long owlCount = sleepingSessions.stream()
                .filter(this::isOwl)
                .count();

        long larkCount = sleepingSessions.stream()
                .filter(this::isLark)
                .count();

        Chronotype chronotype = owlCount > larkCount ? Chronotype.OWL : larkCount > owlCount ? Chronotype.LARK
                : Chronotype.DOVE;

        return new SleepAnalysisResult<>(chronotype.getDescription(), "Ваш хронотип");
    }

    public boolean isOwl(SleepingSession session) {
        LocalDateTime start = session.getStartSleep();
        LocalDateTime end = session.getEndSleep();

        boolean nightOwl = start.toLocalTime().isAfter(LocalTime.MIDNIGHT) && end.toLocalTime().isAfter(LATE_WAKEUP);

        boolean transitionOwl = start.toLocalTime().isAfter(LATE_BEDTIME) && end.toLocalTime().isBefore(LATE_WAKEUP)
                && !start.equals(end);

        return nightOwl || transitionOwl;
    }

    public boolean isLark(SleepingSession session) {
        LocalDateTime start = session.getStartSleep();
        LocalDateTime end = session.getEndSleep();

        boolean nightLark = start.toLocalTime().isBefore(EARLY_BEDTIME) &&
                start.toLocalTime().isAfter(EVENING_SESSION_START) &&
                end.toLocalTime().isBefore(EARLY_WAKEUP);

        return nightLark;
    }
}
