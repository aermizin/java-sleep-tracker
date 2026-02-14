package ru.yandex.practicum.sleeptracker;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SleepTrackerApp {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public static final List<Function<List<SleepingSession>, SleepAnalysisResult<?>>> functions = new ArrayList<>();

    private static LogManager infoLogManager;

    public static void main(String[] args) {

        SleepTrackerApp sleepTrackerApp = new SleepTrackerApp();

        String inputFileName = "C:\\Users\\Artem\\Desktop\\Projects\\beak\\Sprint 8\\src\\main\\resources\\sleep_log.txt";
        String outputLogFileName = "log.txt";

        try (FileWriter infoFileWriter = new FileWriter(outputLogFileName, true)) {

            infoLogManager = new LogManager(new PrintWriter(infoFileWriter));

            List<SleepingSession> sleepingSessions = Files.lines(Paths.get(inputFileName))
                .map(line -> {
                    try {
                        String[] parts = line.split(";");
                        LocalDateTime startSleep = LocalDateTime.parse(parts[0].trim(), DATE_TIME_FORMATTER);
                        LocalDateTime endSleep = LocalDateTime.parse(parts[1].trim(), DATE_TIME_FORMATTER);
                        String qualitySleep = parts[2].trim();
                        return new SleepingSession(startSleep, endSleep, qualitySleep);
                    } catch (DateTimeParseException e) {
                        infoLogManager.logError("Ошибка разбора строки: " + line + " - " + e.getMessage());
                        return null;
                    }
                })
                .filter(session -> session != null)
                .collect(Collectors.toList());

            sleepTrackerApp.setupAnalysisFunctions();

            functions.stream()
                    .map(function -> function.apply(sleepingSessions))
                    .forEach(result -> System.out.println(result.getDescription() +
                            ": " + result.getResult()));

        } catch (IOException ex) {
            try (FileWriter infoFileWriter = new FileWriter("log.txt", true)) {
                infoLogManager = new LogManager(new PrintWriter(infoFileWriter));
                infoLogManager.logError("Ошибка обработки файла: " + ex.getMessage());
            } catch (IOException ignored) {
                // Последнюю ошибку игнорируем, так как ничего другого сделать нельзя.
            }
        }
    }

    public void setupAnalysisFunctions() {
        functions.add(new TotalSleepSessionsCounter());
        functions.add(new MinSleepSessionDurationFinder());
        functions.add(new MaxSleepSessionDurationFinder());
        functions.add(new AverageSleepSessionDurationCalculator());
        functions.add(new BadQualitySleepSessionCounter());
        functions.add(new InsomniaNightsCounter());
        functions.add(new ChronotypeClassifier());
    }
}