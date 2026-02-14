package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class SleepTrackerAppTest {

    private TotalSleepSessionsCounter totalSleepSessionsCounter;
    private MinSleepSessionDurationFinder minSleepSessionDurationFinder;
    private MaxSleepSessionDurationFinder maxSleepSessionDurationFinder;
    private AverageSleepSessionDurationCalculator averageSleepSessionDurationCalculator;
    private BadQualitySleepSessionCounter badQualitySleepSessionCounter;
    private InsomniaNightsCounter insomniaNightsCounter;
    private ChronotypeClassifier chronotypeClassifier;

    @BeforeEach
    void initCounters() {
        totalSleepSessionsCounter = new TotalSleepSessionsCounter();
        minSleepSessionDurationFinder = new MinSleepSessionDurationFinder();
        maxSleepSessionDurationFinder = new MaxSleepSessionDurationFinder();
        averageSleepSessionDurationCalculator = new AverageSleepSessionDurationCalculator();
        badQualitySleepSessionCounter = new BadQualitySleepSessionCounter();
        insomniaNightsCounter = new InsomniaNightsCounter();
        chronotypeClassifier = new ChronotypeClassifier();
    }

    @Test
    void testTotalSleepSessionsCounterWithEmptyList() {
        List<SleepingSession> emptyList = List.of();

        SleepAnalysisResult<Integer> result = totalSleepSessionsCounter.apply(emptyList);

        assertEquals(0, result.getResult(), "Кол-во сессий сна должно быть равно нулю для пустого списка.");
    }

    @Test
    void testCountSleepSession() {
        List<SleepingSession> list = (List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 23, 15),
                    LocalDateTime.of(2025, 10, 2, 7, 30), "GOOD")));

        SleepAnalysisResult<Integer> result = totalSleepSessionsCounter.apply(list);

        assertEquals(1, result.getResult(), "Кол-во сессий сна должно быть равно одному.");
    }

    @Test
    void testMinSleepSessionCounterWithEmptyList() {
        List<SleepingSession> emptyList = List.of();

        SleepAnalysisResult<Long> result = minSleepSessionDurationFinder.apply(emptyList);

        assertEquals(0, result.getResult(), "Минимальная продолжительность сессии сна должна быть равна " +
                "нулю для пустого списка.");
    }

    @Test
    void testFindMinSleepDurationForSessions() {
        List<SleepingSession> list = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 23, 15),
                    LocalDateTime.of(2025, 10, 2, 7, 30), "GOOD"),
                new SleepingSession(LocalDateTime.of(2025, 10, 3, 0, 30),
                    LocalDateTime.of(2025, 10, 3, 7, 30), "NORMAL"));

        SleepAnalysisResult<Long> result = minSleepSessionDurationFinder.apply(list);

        assertEquals(420, result.getResult(), "Минимальная продолжительность сессии сна среди заданных " +
                "сессий должна быть равна наименьшей из них, то есть 420 минут.");
    }

   @Test
    void testMinDurationWithDuplicates() {
        List<SleepingSession> list = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 23, 30),
                    LocalDateTime.of(2025, 10, 2, 6, 30), "GOOD"),
                new SleepingSession(LocalDateTime.of(2025, 10, 3, 0, 30),
                    LocalDateTime.of(2025, 10, 3, 7, 30), "NORMAL"),
                new SleepingSession(LocalDateTime.of(2025, 10, 3, 20, 30),
                    LocalDateTime.of(2025, 10, 4, 6, 0), "BAD"));

        SleepAnalysisResult<Long> result = minSleepSessionDurationFinder.apply(list);

        assertEquals(420, result.getResult(), "Минимальная продолжительность сна должна быть равна " +
                " 420 минут,так как две сессии сна имеют одинаковую минимальную продолжительность.");
    }

    @Test
    void testMaxSleepSessionCounterWithEmptyList() {
        List<SleepingSession> emptyList = List.of();

        SleepAnalysisResult<Long> result = maxSleepSessionDurationFinder.apply(emptyList);

        assertEquals(0, result.getResult(), "Максимальная продолжительность сессии сна должна быть равна " +
                "нулю для пустого списка.");
    }

    @Test
    void testFindMaxSleepDurationForSessions() {
        List<SleepingSession> list = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 22, 0),
                    LocalDateTime.of(2025, 10, 2, 7, 30), "GOOD"),
                new SleepingSession(LocalDateTime.of(2025, 10, 3, 2, 30),
                    LocalDateTime.of(2025, 10, 3, 7, 30), "NORMAL"));

        SleepAnalysisResult<Long> result = maxSleepSessionDurationFinder.apply(list);

        assertEquals(570, result.getResult(), "Максимальная продолжительность сессии сна среди заданных " +
                "сессий должна быть равна наибольшей из них , то есть 570 минут.");
    }

    @Test
    void testMaxDurationWithDuplicates() {
        List<SleepingSession> list = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 23, 30),
                    LocalDateTime.of(2025, 10, 2, 6, 30), "GOOD"),
                new SleepingSession(LocalDateTime.of(2025, 10, 3, 0, 30),
                    LocalDateTime.of(2025, 10, 3, 7, 30), "NORMAL"),
                new SleepingSession(LocalDateTime.of(2025, 10, 3, 20, 30),
                    LocalDateTime.of(2025, 10, 4, 2, 0), "BAD"));

        SleepAnalysisResult<Long> result = maxSleepSessionDurationFinder.apply(list);

        assertEquals(420, result.getResult(), "Максимальная продолжительность сна должна быть равна " +
                " 420 минут,так как две сессии сна имеют одинаковую максимальную продолжительность.");
    }

    @Test
    void testAverageSleepSessionDurationCounterWithEmptyList() {
        List<SleepingSession> emptyList = List.of();

        SleepAnalysisResult<Long> result = averageSleepSessionDurationCalculator.apply(emptyList);

        assertEquals(0, result.getResult(), "Средняя продолжительность сессий сна должна быть равна " +
                "нулю для пустого списка.");
    }

    @Test
    void testFindAverageSleepSessionDurationForSessions() {
        List<SleepingSession> list = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 22, 0),
                    LocalDateTime.of(2025, 10, 2, 6, 0), "GOOD"),
                new SleepingSession(LocalDateTime.of(2025, 10, 3, 0, 0),
                    LocalDateTime.of(2025, 10, 4, 0, 0), "NORMAL"));

        SleepAnalysisResult<Long> result = averageSleepSessionDurationCalculator.apply(list);

        assertEquals(960, result.getResult(), "Средняя продолжительность сессий сна должна быть равна " +
                "среднему арифметическому значению их длительности.");
    }

    @Test
    void testBadQualitySleepSessionCounterWithEmptyList() {
        List<SleepingSession> emptyList = List.of();

        SleepAnalysisResult<Long> result = badQualitySleepSessionCounter.apply(emptyList);

        assertEquals(0, result.getResult(), "Количество сессий с плохим качеством сна " +
                "должно быть равно нулю для пустого списка.");
    }

    @Test
    void testCountBadSessionsWhenAbsent() {
        List<SleepingSession> list = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 22, 0),
                    LocalDateTime.of(2025, 10, 2, 6, 0),  "GOOD"),
                new SleepingSession(LocalDateTime.of(2025, 10, 3, 0, 0),
                    LocalDateTime.of(2025, 10, 4, 0, 0), "NORMAL"));

        SleepAnalysisResult<Long> result = badQualitySleepSessionCounter.apply(list);

        assertEquals(0, result.getResult(), "Количество сессий с плохим качеством сна " +
                "должно быть равно нулю, так как таких сессий нет в списке.");
    }

    @Test
    void testCountBadQualitySession() {
        List<SleepingSession> list = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 22, 0),
                    LocalDateTime.of(2025, 10, 2, 6, 0), "GOOD"),
                new SleepingSession(LocalDateTime.of(2025, 10, 3, 0, 0),
                    LocalDateTime.of(2025, 10, 4, 0, 0), "NORMAL"),
                new SleepingSession(LocalDateTime.of(2025, 10, 3, 20, 30),
                    LocalDateTime.of(2025, 10, 4, 2, 0), "BAD"));

        SleepAnalysisResult<Long> result = badQualitySleepSessionCounter.apply(list);

        assertEquals(1, result.getResult(), "Количество сессий с плохим качеством сна " +
                "должно быть равно одному.");
    }

    @Test
    void testInsomniaNightsClassificationOnEmptyList() {
        List<SleepingSession> emptyList = List.of();

        SleepAnalysisResult<Integer> result = insomniaNightsCounter.apply(emptyList);

        assertEquals(0, result.getResult(), "Количество бессонных ночей должно быть равно " +
                "нулю для пустого списка.");
    }

    @Test
    void testRegularNightSessions() {
        List<SleepingSession> list = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0), "NORMAL"),
                new SleepingSession(LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 7, 0), "NORMAL"),
                new SleepingSession(LocalDateTime.of(2025, 10, 3, 23, 0),
                        LocalDateTime.of(2025, 10, 4, 7, 0), "NORMAL")
        );
        SleepAnalysisResult<Integer> result = insomniaNightsCounter.apply(list);
        assertEquals(0, result.getResult(), "Количество бессонных ночей должно быть равно нулю," +
                " так как все ночи покрыты сессиями.");
    }

    @Test
    void testOneNightWithoutSleep() {
        List<SleepingSession> list = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0), "NORMAL"),
                new SleepingSession(LocalDateTime.of(2025, 10, 3, 23, 0),
                        LocalDateTime.of(2025, 10, 4, 7, 0), "NORMAL")
        );
        SleepAnalysisResult<Integer> result = insomniaNightsCounter.apply(list);
        assertEquals(1, result.getResult(), "Количество бессонных ночей должно быть равно одному, " +
                "так как пропущена одна ночь.");
    }

    @Test
    void testSeveralNightsWithoutSleep() {
        List<SleepingSession> list = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0), "NORMAL"),
                new SleepingSession(LocalDateTime.of(2025, 10, 5, 23, 0),
                        LocalDateTime.of(2025, 10, 6, 7, 0), "NORMAL")
        );
        SleepAnalysisResult<Integer> result = insomniaNightsCounter.apply(list);
        assertEquals(3, result.getResult(), "Количество бессонных ночей должно быть равно трём, " +
                "так как пропущены три ночи.");
    }

    @Test
    void testChronotypeClassificationOnEmptyList() {
        List<SleepingSession> emptyList = List.of();

        SleepAnalysisResult<String> result = chronotypeClassifier.apply(emptyList);

        assertEquals("Голубь", result.getResult(), "Должен возвращаться хронотип Голубь" +
                " для пустого списка.");
    }

    @Test
    void testDetermineOwlChronotypeInValidCase() {
        List<SleepingSession> list = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 23, 30),
                    LocalDateTime.of(2025, 10, 2, 10, 0), "GOOD"),
                new SleepingSession(LocalDateTime.of(2025, 10, 3, 0, 10),
                    LocalDateTime.of(2025, 10, 3, 11, 0), "NORMAL"),
                new SleepingSession(LocalDateTime.of(2025, 10, 3, 20, 30),
                    LocalDateTime.of(2025, 10, 4, 6, 0), "GOOD"));

        SleepAnalysisResult<String> result = chronotypeClassifier.apply(list);

        assertEquals("Сова", result.getResult(), "Должен возвращаться хронотип Сова, так как наибольшее " +
                " количество сессий сна подходят под его условия.");
    }

    @Test
    void testDetermineLarkChronotypeInValidCase() {
        List<SleepingSession> list = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 21, 0),
                        LocalDateTime.of(2025, 10, 2, 5, 30), "GOOD"),
                new SleepingSession(LocalDateTime.of(2025, 10, 3, 20, 30),
                        LocalDateTime.of(2025, 10, 4, 6, 0), "GOOD"),
                new SleepingSession(LocalDateTime.of(2025, 10, 3, 0, 10),
                        LocalDateTime.of(2025, 10, 3, 11, 0), "NORMAL"));

        SleepAnalysisResult<String> result = chronotypeClassifier.apply(list);

        assertEquals("Жаворонок", result.getResult(), "Должен возвращаться хронотип Жаворонок, так как " +
                "наибольшее количество сессий сна подходят под его условия.");
    }

    @Test
    void testDetermineLarkChronotypeInValidC1ase() {
        List<SleepingSession> list = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 0, 30),
                    LocalDateTime.of(2025, 10, 2, 10, 30), "BAD"),
                new SleepingSession(LocalDateTime.of(2025, 10, 2, 21, 0),
                    LocalDateTime.of(2025, 10, 3, 6, 0), "GOOD"));

        SleepAnalysisResult<String> result = chronotypeClassifier.apply(list);

        assertEquals("Голубь", result.getResult(), "Должен возвращаться хронотип Голубь, при равенстве" +
                " количества сессий сна.");
    }

}