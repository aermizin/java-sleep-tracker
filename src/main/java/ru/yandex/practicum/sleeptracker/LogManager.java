package ru.yandex.practicum.sleeptracker;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class LogManager implements AutoCloseable {

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

    @Override
    public void close() throws IOException {
        if (writer instanceof Closeable && !writer.checkError()) {
            ((Closeable) writer).close();
        }
    }
}