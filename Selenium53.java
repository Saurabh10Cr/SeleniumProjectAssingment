package com.thetestingacademy.selenium31032024;

import io.qameta.allure.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Selenium53 {

    private EdgeDriver driver;

    @BeforeTest
    public void openBrowser() {
        EdgeOptions options = new EdgeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.addArguments("--guest");
        driver = new EdgeDriver(options);
    }

    @Test()
    @Description("Test Case: Calculate days between two dates from the web page")
    @Parameters("inputString")
    public void testPositive(String inputString) throws InterruptedException {
        driver.manage().window().maximize();
        driver.get("https://patinformed.wipo.int/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

                WebElement searchBox = driver.findElement(By.xpath("//input[@class='searchField']"));
                searchBox.sendKeys(inputString);

                WebElement clickPopup = driver.findElement(By.xpath("//button[@class='green']"));
                clickPopup.click();
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement noResultsMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[@class='card noResult flex center-v']//span[1]")));

            System.out.println("No search results found for: " + inputString);
            return;
        } catch (Exception e) {
            System.out.println("Search results found, proceeding...");
        }


              WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(5));
              WebElement element = wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//tbody/tr[1]/td[3]/ul[1]/li[1]/div[1]")));
              element.click();

        try {
            WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement filingDateElement = wait2.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("(//td[@class='flex column']/span[@class='nobreak'])[1]")));
            WebElement publicationDateElement = wait1.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("(//td[@class='nobreak'])[1]")));

            String filingDateText = filingDateElement.getText().trim();
            String publicationDateText = publicationDateElement.getText().trim();

            String failingDateOnly = filingDateText.substring(0, filingDateText.indexOf(' '));
            System.out.println("Filing Date: " + failingDateOnly);

            String pubDate = publicationDateText.substring(0, publicationDateText.indexOf(' '));
            System.out.println("Publication Date: " + pubDate);

            LocalDate filingDate = LocalDate.parse(failingDateOnly);
            LocalDate publicationDate = LocalDate.parse(pubDate);

            long daysBetween = ChronoUnit.DAYS.between(filingDate, publicationDate);
            System.out.println("Days between Filing Date and Publication Date: " + daysBetween);
        } catch (Exception e) {
            System.out.println("Error date values: " + e.getMessage());
        }

    }

    @AfterTest
    public void closeBrowser() {

          driver.quit();
           System.out.println("Browser closed successfully.");

    }
}
