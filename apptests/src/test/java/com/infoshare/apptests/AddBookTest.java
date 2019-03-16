package com.infoshare.apptests;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AddBookTest {

  private String random;
  private WebDriver driver;

  @BeforeEach
  public void setUp() throws MalformedURLException {
    WebDriverManager.chromedriver().setup();
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setBrowserName("chrome");
    driver = new RemoteWebDriver(new URL("http://chrome:4444/wd/hub/"), capabilities);
    WebDriverRunner.setWebDriver(driver);

    random = UUID.randomUUID().toString();
  }

  @Test
  public void addBook() {

    open("http://jenkins:8585");
    $(By.xpath("//a[@href='/books/all']")).click();
    $(By.xpath("//a[@href='/books/create']")).click();
    $(By.id("books0.title")).sendKeys("title" + random);
    $(By.id("books0.author")).sendKeys("author" + random);
    $(By.id("submitButton")).click();
    assertThat($$(By.xpath("//tbody/tr/td[1]")).stream().map(SelenideElement::getText).collect(Collectors.toList()))
            .contains("title" + random);
    assertThat($$(By.xpath("//tbody/tr/td[2]")).stream().map(SelenideElement::getText).collect(Collectors.toList()))
            .contains("author" + random);

  }

  @AfterEach
  public void tearDown() {
    driver.close();
  }

}


