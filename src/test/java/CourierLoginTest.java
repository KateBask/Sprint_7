import client.CourierClient;
import dto.CourierRequest;
import dto.LoginRequest;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static generator.CourierRequestGenerator.getRandomCourierRequest;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTest {
    private CourierClient courierClient;
    private Integer id;
    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }
    @Test
    @DisplayName("Курьер с валидными данными должен авторизоваться")
    public void CourierWithValidDataShouldAuthorize() {
        CourierRequest randomCourierRequest = getRandomCourierRequest();
        courierClient.create(randomCourierRequest)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin(randomCourierRequest.getLogin());
        loginRequest.setPassword(randomCourierRequest.getPassword());
       id = courierClient.login(loginRequest)
                .assertThat()
                .statusCode(SC_OK)
                .body("id", notNullValue())
                .extract()
                .path("id");
    }
    //этот тест падает, так как возвращается 504 ошибка, вместо заявленной в документации 400
    @Test
    @DisplayName("Курьер с неверным паролем не должен авторизоваться")
    public void CourierWithWrongPasswordShouldNotAuthorize() {
        CourierRequest randomCourierRequest = getRandomCourierRequest();
        courierClient.create(randomCourierRequest)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin(randomCourierRequest.getLogin());
        loginRequest.setPassword(RandomStringUtils.randomAlphabetic(8));

        courierClient.login(loginRequest)
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }
    @Test
    @DisplayName("Курьер с неверным логином не должен авторизоваться")
    public void CourierWithWrongLoginShouldNotAuthorize() {
        CourierRequest randomCourierRequest = getRandomCourierRequest();
        courierClient.create(randomCourierRequest)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin(RandomStringUtils.randomAlphabetic(8));
        loginRequest.setPassword(randomCourierRequest.getPassword());

        courierClient.login(loginRequest)
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }
    @Test
    @DisplayName("Курьер без логина не должен авторизоваться")
    public void CourierWithoutLoginShouldNotAuthorize() {
        CourierRequest randomCourierRequest = getRandomCourierRequest();
        courierClient.create(randomCourierRequest)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword(randomCourierRequest.getPassword());
        courierClient.login(loginRequest)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
    @Test
    @DisplayName("Курьер без регистрации не должен авторизоваться")
    public void CourierWithoutRegShouldNotBeAuth() {
        CourierRequest randomCourierRequest = getRandomCourierRequest();
        courierClient.create(randomCourierRequest)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin(RandomStringUtils.randomAlphabetic(8));
        loginRequest.setPassword(RandomStringUtils.randomAlphabetic(8));
        courierClient.login(loginRequest)
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }
    @After
    public void tearDown() {
        if (id != null) {
            courierClient.delete(id)
                    .assertThat()
                    .body("ok", equalTo(true));
        }
    }
}
