package ru.netology.geo;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import java.util.stream.Stream;


@DisplayName("Тестирование: GeoServiceImpl")
class GeoServiceImplTest {

    private GeoServiceImpl geoServiceImpl;

    @BeforeEach
    void setUp() {
        geoServiceImpl = new GeoServiceImpl();
        System.out.println("Вызываюсь до выполнения теста");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Вызываюсь после вызова теста");
    }

    @DisplayName("Проверика возвращаемого текста (класс GeoServiceImpl)")
    @ParameterizedTest
    @MethodSource("getArguments")
    void locale(String ip, Location expectedResult) {
        Assertions.assertEquals(expectedResult.getCountry(), geoServiceImpl.byIp(ip).getCountry());
    }

    @Test
    void byIpOther() {
        Location expectedResult = geoServiceImpl.byIp("");
        Assertions.assertNull(expectedResult);
    }

    private static Stream<Arguments> getArguments() {
        return Stream.of(
                Arguments.of(GeoServiceImpl.MOSCOW_IP, new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of(GeoServiceImpl.NEW_YORK_IP, new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of(GeoServiceImpl.LOCALHOST, new Location(null, null, null, 0)),
                Arguments.of("172.16.2.13", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.45.1.10", new Location("New York", Country.USA, null, 0))
        );
    }
}
