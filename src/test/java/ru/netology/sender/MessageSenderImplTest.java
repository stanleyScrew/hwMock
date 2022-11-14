package ru.netology.sender;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.Map;

import static ru.netology.sender.MessageSenderImpl.IP_ADDRESS_HEADER;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование: MessageSenderImpl")
class MessageSenderImplTest {

    public static final String MOSCOW_IP = "172.0.32.11";
    public static final String NEW_YORK_IP = "96.44.183.149";
    @Mock
    private GeoService geoService;
    @Mock
    private LocalizationService localizationService;

    private MessageSenderImpl messageSenderImpl;

    @BeforeEach
    void setUp() {
        geoService = Mockito.mock(GeoService.class);
        localizationService = Mockito.mock(LocalizationService.class);
        messageSenderImpl = new MessageSenderImpl(geoService, localizationService);
        System.out.println("Вызываюсь до выполнения теста");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Вызываюсь после вызова теста");
    }

    @Test
    @DisplayName("Тестирование: Проверить, что MessageSenderImpl всегда отправляет" +
            " только русский текст, если ip относится к российскому сегменту адресов.")
    void send_RUSSIA() {
        Mockito.when(geoService.byIp(Mockito.eq(MOSCOW_IP))).thenReturn(new Location("Moscow", Country.RUSSIA, "lenina", 25));
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        String actualResult = "Добро пожаловать";
        String expectedResult = messageSenderImpl.send(Map.of(IP_ADDRESS_HEADER, MOSCOW_IP));
        Assertions.assertEquals(expectedResult, actualResult);
        System.out.println("Тест выполнен");
    }

    @Test
    @DisplayName("Тестирование: Проверить, что MessageSenderImpl всегда отправляет " +
            "только английский текст, если ip относится к американскому сегменту адресов.")
    void send_USA() {
        Mockito.when(geoService.byIp(Mockito.eq(NEW_YORK_IP))).thenReturn(new Location("New York", Country.USA, " 10th Avenue", 32));
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");
        String actualResult = "Welcome";
        String expectedResult = messageSenderImpl.send(Map.of(IP_ADDRESS_HEADER, NEW_YORK_IP));
        Assertions.assertEquals(expectedResult, actualResult);
        System.out.println("Тест выполнен");
    }
}

