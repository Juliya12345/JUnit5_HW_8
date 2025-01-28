
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;


public class SwitchLanguageTest {
    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1928x1980";
        Configuration.pageLoadStrategy = "eager";
    }

    @BeforeEach
    void setUp() {
        open("https://hotel-spb.ru/");
    }

    @Tag("Language test")
    @ParameterizedTest(name = "При переключении языков меняется адрес")
    @EnumSource(Language.class)
    void websiteShouldDisplayCorrectAdress(Language language) {
        $(".img-fluid").click();
        $("[x-placement='bottom-start']").$(byText(language.name())).click();
        $(".row .align-items-center .justify-content-end").shouldHave(text(language.description));
    }
}





