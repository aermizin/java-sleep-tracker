package ru.yandex.practicum.sleeptracker;

import java.io.PrintWriter;
import java.time.LocalDateTime;

public class LogManager {

    private final PrintWriter writer;

    public LogManager(PrintWriter printWriter) {
        this.writer = printWriter;
    }

    public void logInfo(String message) {
        writer.println(LocalDateTime.now().format(SleepTrackerApp.DATE_TIME_FORMATTER) + "[INFO] " + message);
    }

    public void logError(String message) {
        writer.println(LocalDateTime.now().format(SleepTrackerApp.DATE_TIME_FORMATTER) + "[ERROR] " + message);
    }
}