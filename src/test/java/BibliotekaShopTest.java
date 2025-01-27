import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
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
}
