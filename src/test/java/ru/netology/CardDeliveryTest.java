package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class CardDeliveryTest {


    public static String dateDelivery(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");
    }


    @Test
    public void shouldOrderCardDelivery() {
        String date = CardDeliveryTest.dateDelivery(3);
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(date);
        $("[data-test-id='name'] .input__control").setValue("Быкова Юлия");
        $("[data-test-id='phone'] .input__control").setValue("+79123456789");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldHave(text("Успешно!"),
                Duration.ofSeconds(15)).shouldBe(visible);
        $("[data-test-id='notification'] .notification__content").shouldHave(text("Встреча успешно забронирована на " + date),
                Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    public void shouldCityWithDash() {
        String date = dateDelivery(3);
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(date);
        $("[data-test-id='name'] .input__control").setValue("Быкова Юлия");
        $("[data-test-id='phone'] .input__control").setValue("+79123456789");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldHave(text("Успешно!"),
                Duration.ofSeconds(15)).shouldBe(visible);
        $("[data-test-id='notification'] .notification__content").shouldHave(text("Встреча успешно забронирована на " + date),
                Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    public void shouldEnglishCity() {
        String date = dateDelivery(3);
        $("[data-test-id='city'] .input__control").setValue("Moscow");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(date);
        $("[data-test-id='name'] .input__control").setValue("Быкова Юлия");
        $("[data-test-id='phone'] .input__control").setValue("+79123456789");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='city'] .input__sub")
                .shouldHave(text("Доставка в выбранный город недоступна"),
                        Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    public void shouldEmptyCity() {
        String date = dateDelivery(3);
        $("[data-test-id='city'] .input__control").setValue("");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(date);
        $("[data-test-id='name'] .input__control").setValue("Быкова Юлия");
        $("[data-test-id='phone'] .input__control").setValue("+79123456789");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='city'] .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"),
                        Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    public void shouldNameWithDash() {
        String date = dateDelivery(3);
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(date);
        $("[data-test-id='name'] .input__control").setValue("Быкова Юлия-Анна");
        $("[data-test-id='phone'] .input__control").setValue("+79123456789");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldHave(text("Успешно!"),
                Duration.ofSeconds(15)).shouldBe(visible);
        $("[data-test-id='notification'] .notification__content").shouldHave(text("Встреча успешно забронирована на " + date),
                Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    public void shouldEnglishName() {
        String date = dateDelivery(3);
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(date);
        $("[data-test-id='name'] .input__control").setValue("Bykova Iuliya");
        $("[data-test-id='phone'] .input__control").setValue("+79123456789");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='name'] .input__sub")
                .shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."),
                        Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    public void shouldEmptyName() {
        String date = dateDelivery(3);
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(date);
        $("[data-test-id='name'] .input__control").setValue("");
        $("[data-test-id='phone'] .input__control").setValue("+79123456789");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='name'] .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"),
                        Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    public void shouldPhoneWithoutPlus() {
        String date = dateDelivery(3);
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(date);
        $("[data-test-id='name'] .input__control").setValue("Быкова Юлия");
        $("[data-test-id='phone'] .input__control").setValue("89123456789");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='phone'] .input__sub")
                .shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."),
                        Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    public void shouldPhoneWithoutOneNumber() {
        String date = dateDelivery(3);
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(date);
        $("[data-test-id='name'] .input__control").setValue("Быкова Юлия");
        $("[data-test-id='phone'] .input__control").setValue("+7912345678");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='phone'] .input__sub")
                .shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."),
                        Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    public void shouldPhonePlusNotInFirstPlace() {
        String date = dateDelivery(3);
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(date);
        $("[data-test-id='name'] .input__control").setValue("Быкова Юлия");
        $("[data-test-id='phone'] .input__control").setValue("7+9123456789");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='phone'] .input__sub")
                .shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."),
                        Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    public void shouldEmptyPhone() {
        String date = dateDelivery(3);
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(date);
        $("[data-test-id='name'] .input__control").setValue("Быкова Юлия");
        $("[data-test-id='phone'] .input__control").setValue("");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='phone'] .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"),
                        Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    public void shouldEmptyCheckbox() {
        String date = dateDelivery(3);
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(date);
        $("[data-test-id='name'] .input__control").setValue("Быкова Юлия");
        $("[data-test-id='phone'] .input__control").setValue("+79123456789");
        $(".button").click();
        $("[data-test-id='agreement']").isSelected();
    }
}