package step;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import dev.failsafe.internal.util.Assert;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.openqa.selenium.Keys;
import static com.codeborne.selenide.Selenide.$x;

public class KlavagonkiSteps{
    private final SelenideElement closeButton = $x("//input[@value='Закрыть'][1]");
    private final SelenideElement startButton = $x("//*[@id='host_start']");
    private final SelenideElement highlightWord = $x("//*[@id='typefocus']");
    private final SelenideElement inputField = $x("//input[@type='text'][@tabindex='1']");
    private final SelenideElement afterFocusWord = $x("//span[@id='afterfocus']");
    private final SelenideElement resultText = $x("//td[text()='Это вы']//ancestor-or-self::div//div[@class='stats']//div[2]/span/span");
    private String getCurrentWord(){
        return highlightWord.getText().replaceAll("c", "с").replaceAll("o", "о");
    }
    @Given("Открыта стартовая страница браузера {string}")
    public void openHomePage(String url) {
        Selenide.open(url);
    }

    @When("Начинаем игру")
    public void startGame() {
        closeButton.click();
        if(startButton.isDisplayed()){
            startButton.click();
        }
    }

    @And("Ждем начала игры")
    public void waitForStartGame() {
        highlightWord.click();
    }

    @And("Вводим подсвеченное слово в цикле")
    public void playGame() {
        while (true){
            String currentWord = getCurrentWord();
            String afterFocusSymbol = afterFocusWord.getText();
            inputField.sendKeys(currentWord);
            if(afterFocusSymbol.equals(".")){
                inputField.sendKeys(".");
                break;
            }
            inputField.sendKeys(Keys.SPACE);
        }
    }

    @Then("Фиксируем, что игра завершена и символов в митуту больше чем {int}")
    public void endGame(int minValue) {
        String result = resultText.getText();
        int resultNumber = Integer.parseInt(result);
        System.out.println("Количество знаков в минуту: " + resultNumber);
        Assert.isTrue(resultNumber > minValue, "Актуальный результат был: " + resultNumber);
    }
}
