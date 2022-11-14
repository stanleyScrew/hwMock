package ru.netology.i18n;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;

@DisplayName("Тестирование: LocalizationServiceImpl")
class LocalizationServiceImplTest {

    private LocalizationServiceImpl localizationService;


    @BeforeEach
    void setUp() {
        localizationService = new LocalizationServiceImpl();
        System.out.println("Вызываюсь до выполнения теста");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Вызываюсь после вызова теста");
    }

    // TODO: 24.10.2022 Вариант 1
    @Test
    void locale_RUSSIA() {
        String actualResult = "Добро пожаловать";
        String expectedResult = localizationService.locale(Country.RUSSIA);
        Assertions.assertEquals(expectedResult, actualResult);
        System.out.println("Тест выполнен");
    }

    @Test
    void locale_USA() {
        String actualResult = "Welcome";
        String expectedResult = localizationService.locale(Country.USA);
        Assertions.assertEquals(expectedResult, actualResult);
        System.out.println("Тест выполнен");
    }

    // TODO: 24.10.2022 Вариант 2
    @DisplayName("Проверика возвращаемого текста (класс LocalizationServiceImpl)")
    @ParameterizedTest
    @MethodSource("getArguments")
    void locale(Country country, String expectedResult) {
        String actualResult = localizationService.locale(country);
        Assertions.assertEquals(expectedResult, actualResult);
    }

    private static Stream<Arguments> getArguments() {
        return Stream.of(
                Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.USA, "Welcome"),
                Arguments.of(Country.BRAZIL, "Welcome"),
                Arguments.of(Country.GERMANY, "Welcome")
        );
    }
}
