import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class Task02 {
    Playwright playwright;
    Browser browser;
    Page page;

    @BeforeEach
    public void warmUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
    }

    @AfterEach
    public void tearsDown() {
        browser.close();
        playwright.close();
    }

    @Test
    public void practiceFormTest() {
        page.navigate("https://demoqa.com/automation-practice-form");
        page.getByPlaceholder("First Name").fill("Anastasiya");
        page.getByPlaceholder("Last Name").fill("Ivanova");
        page.getByPlaceholder("name@example.com").fill("test@mail.ru");
        page.locator("//label[@for='gender-radio-1']").check();
        page.getByPlaceholder("Mobile Number").fill("4456482256");
        page.locator("//input[@id = 'dateOfBirthInput']").click();
        page.locator("//select[@class = 'react-datepicker__month-select']").selectOption("May");
        page.locator("//select[@class = 'react-datepicker__year-select']").selectOption("1999");
        page.locator("//label[@for='hobbies-checkbox-1']").setChecked(true);
        page.locator("//input[@id = 'uploadPicture']").setInputFiles(Paths.get("src/file.jpg"));
        page.locator("//input[@id='subjectsInput']").press("Enter");


        assertThat(page.locator("#gender-radio-1")).isChecked();
        assertThat(page.getByPlaceholder("First Name")).hasValue("Anastasiya");
        assertThat(page.locator("//input[@id = 'hobbies-checkbox-3']")).isVisible();
        assertThat(page).hasTitle("DEMOQA");
        assertThat(page).hasURL("https://demoqa.com/automation-practice-form");
        assertThat(page.getByPlaceholder("Mobile Number")).hasAttribute("placeholder", "Mobile Number");
        Assertions.assertEquals("Male", page.locator("//input[@name='gender']").nth(0).inputValue());
        Assertions.assertEquals("Mobile Number",page.getByPlaceholder("Mobile Number").getAttribute("placeholder") );
    }
    @Test
    public void menuTest() {
        page.navigate("https://demoqa.com/menu#");
        page.locator("//a[text() = 'Main Item 2']").hover();;
        assertThat(page.locator("//a[text() = 'Sub Item']").nth(1)).hasText("Sub Item");
        assertThat(page.locator("//a[text() = 'Sub Item']").nth(0)).containsText("Sub Item");
    }
    @Test
    public void droppableTest() {
        page.navigate("https://demoqa.com/droppable");
        page.locator("//div[@id = 'draggable' and text()='Drag me']").dragTo(page.locator("//p[text() = 'Drop here']").nth(0));
    }
    @Test
    public void dynamicPropertyTest() throws InterruptedException {
        page.navigate("https://demoqa.com/dynamic-properties");
        assertThat(page.locator("//button[text() ='Will enable 5 seconds']")).isDisabled();
        Thread.sleep(5000);
        assertThat(page.locator("//button[text() ='Will enable 5 seconds']")).isEnabled();

    }
    @Test
    public void sortableTest() {
        page.navigate("https://demoqa.com/sortable");
        System.out.println(page.locator("//div[@class='list-group-item list-group-item-action']").allInnerTexts());
        System.out.println(page.locator("//div[@class='list-group-item list-group-item-action']").nth(0).innerText());
        System.out.println(page.locator("//div[@class='list-group-item list-group-item-action']").count());
        assertThat(page.locator("//div[@class='list-group-item list-group-item-action']")).hasCount(15);

    }
    @Test
    public void focusAndUnfocusTest() {
        page.navigate("https://demoqa.com/text-box");
        Locator btn = page.getByPlaceholder("Full Name");
        btn.focus();
        assertThat(btn).isFocused();
        btn.blur();
        assertThat(btn).not().isFocused();
    }
}
