import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.TypeOptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;


import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static java.nio.file.Files.find;

public class SwitchLanguageTest {
    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1928x1980";
        Configuration.pageLoadStrategy = "eager";
    }
    @Tag("Language test")
    @ParameterizedTest()
    @EnumSource(Language.class)
    void websiteShouldDisplayCorrectAdress(Language language){
        open("https://hotel-spb.ru/");
        $(".img-fluid").click();
        $("[x-placement='bottom-start']").$(byText(language.name())).click();
        $(".row .align-items-center .justify-content-end").shouldHave(text(language.description));
    }
}
