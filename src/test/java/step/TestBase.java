package step;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.cucumber.java.*;
import org.openqa.selenium.Dimension;

public class TestBase {

    @Before
    public void setUpDriverBeforeScenario(){
        Configuration.timeout = 160000;
    }

/*    @Before
    public void setUp(){
        Configuration.browser = "chrome";
        Dimension size = new Dimension(1920, 1080);
        Configuration.browserSize = size.getWidth() + "x" + size.getHeight();
        //Configuration.headless = true;
        //Configuration.baseUrl = "https://klavogonki.ru/go?type=normal";
        Configuration.timeout = 60000;
    }*/

    @AfterStep
    public void tearDown(Scenario scenario){
        // Завершение сценария
        if (scenario.isFailed()) {
            // Если сценарий не прошел успешно, то можно сделать скриншот
            Selenide.screenshot(scenario.getName());
        }
        // Закрытие браузера
        //Selenide.closeWebDriver();
    }
}