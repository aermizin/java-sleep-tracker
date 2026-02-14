package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;

public class SleepingSession {
    private LocalDateTime startSleep;
    private LocalDateTime endSleep;
    private String qualitySleep;

    public SleepingSession (LocalDateTime startSleep, LocalDateTime endSleep, String qualitySleep) {
        this.startSleep = startSleep;
        this.endSleep = endSleep;
        this.qualitySleep = qualitySleep;
    }

    public LocalDateTime getStartSleep() {
        return startSleep;
    }

    public LocalDateTime getEndSleep() {
        return endSleep;
    }

    public String getQualitySleep() {
        return qualitySleep;
    }

    @Override
    public String toString() {
        return "SleepingSession { " +
                "startSleep = " + startSleep +
                ", endSleep = " + endSleep +
                ", qualitySleep = " + qualitySleep +
                '}';
    }

}
