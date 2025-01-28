import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class BibliotekaShopTest {

    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1928x1980";
        Configuration.pageLoadStrategy = "eager";
    }
    @BeforeEach
    void setUp() {
        open("https://biblioteka.shop/");
    }
    @Tag("Search")
    @ValueSource(strings = {"Свечи", "Диффузеры", "Духи"})
    @ParameterizedTest (name = "При поиске товара {0} выпадает не пустой список карточек")
    void searchItemsTest(String value){
        $("[name='search[name]']").setValue(value).pressEnter();
        $$(".b-product__img")
                .shouldBe(sizeGreaterThan(0));
    }
    @Tag("Search")
    @CsvSource(value = {
            "Свечи, Трава",
            "Диффузеры , Чистота",
               "Духи, Брауни"})
    @ParameterizedTest(name = "Для поискового запроса {0} на странице должен быть товар {1}")
    void searchSpecificItemsTest(String value, String item){
        $("[name='search[name]']").setValue(value).pressEnter();
        $(".b-catalog__in").shouldHave(text(item));

    }
    @Tag("Search")
    @CsvFileSource(resources = "/test_data/TestDataForBibliotekaShop.csv")
    @ParameterizedTest(name = "Для поискового запроса {0} на странице должен быть товар {1}(то же самое, что в предыдущем тесте, но с данными из файла")
    void searchSpecificItemsWithFileTest(String value, String item){
        $("[name='search[name]']").setValue(value).pressEnter();
        $(".b-catalog__in").shouldHave(text(item));

    }
    @Tag("CheckButtons")
    @ParameterizedTest(name = "Для категорий товаров должны быть обязательные подкатегории этих товаров")
    @MethodSource
    void checkThePresenceOfTheNecessaryButtonsTest (String value, List<String> items ) {
        $(".lheader__bottom").$(byText(value)).hover();
        $$(".lheader__nav .lheader__nav-col-item-title").shouldHave(texts(items));
    }

    static Stream<Arguments> checkThePresenceOfTheNecessaryButtonsTest () {
        return Stream.of(
                Arguments.of(
                        "Духи",  List.of("Все духи", "Парфюм", "Масляные духи", "Одеколоны")),
                Arguments.of(
                        "Для дома", List.of("Все ароматы для дома", "Ароматические диффузоры"))

                );

    }


}
