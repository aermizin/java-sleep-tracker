package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InsomniaNightsCounter implements Function<List<SleepingSession>, SleepAnalysisResult<?>> {

    public static final LocalTime MORNING_SESSION_START = LocalTime.of(6, 0);

    @Override
    public SleepAnalysisResult<Integer> apply(List<SleepingSession> sleepingSessions) {
        if (sleepingSessions.isEmpty()) {
            return new SleepAnalysisResult<>(0, "Количество бессонных ночей");
        }


        Set<LocalDate> activeNights = sleepingSessions.stream()
                .filter(this::crossesNightInterval)
                .map(SleepingSession::getStartSleep)
                .map(LocalDateTime::toLocalDate)
                .collect(Collectors.toSet());

        LocalDate firstSessionDate = sleepingSessions.getFirst().getStartSleep().toLocalDate();
        LocalDate lastSessionDate = sleepingSessions.getLast().getEndSleep().toLocalDate();

        Period period = Period.between(firstSessionDate, lastSessionDate);
        int totalNights = period.getDays();

        int sum = totalNights - activeNights.size();

        return new SleepAnalysisResult<>(sum, "Количество бессонных ночей");
    }

    private boolean crossesNightInterval(SleepingSession session) {

        LocalDateTime start = session.getStartSleep();
        LocalDateTime end = session.getEndSleep();

        boolean spansMidnightTransition = start.toLocalDate() != end.toLocalDate();

        boolean startsAfterMidnightButBeforeSixAM = start.toLocalTime().isBefore(MORNING_SESSION_START);

        return spansMidnightTransition || startsAfterMidnightButBeforeSixAM;
    }
}
