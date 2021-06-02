package at.technikumwien.personwebapp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

// E2E (end-2-end) test

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Tag("extended")
public class IndexTest {
    @LocalServerPort // injects the random server port
    private long port;

    private WebDriver driver;
    private Wait<WebDriver> wait;

    @BeforeAll
    public static void setUpBeforeClass() {
        // manage drivers -> no manual binary download of driver
        WebDriverManager.chromedriver().setup();
        System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
    }

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver(
                new ChromeOptions().setHeadless(true) // no chrome GUI
        );
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 3);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testIndex() {
        driver.get(getUrl(""));
        var button = driver.findElement(By.tagName("button"));
        assertEquals("show all persons", button.getText());

        var lis = driver.findElements(By.tagName("li"));
        assertEquals(1, lis.size());

        button.submit();
        wait.until(ExpectedConditions.urlToBe(getUrl("?all=true")));
    }

    @Test
    public void testIndexWithAll() {
        driver.get(getUrl("?all=true"));
        var button = driver.findElement(By.tagName("button"));
        assertEquals("show only active persons", button.getText());

        var lis = driver.findElements(By.tagName("li"));
        assertEquals(2, lis.size());

        button.submit();
        wait.until(ExpectedConditions.urlToBe(getUrl("?")));
    }

    private String getUrl(String path) {
        return "http://localhost:" + port + "/" + path;
    }
}
