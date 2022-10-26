import client.CourierClient;
import dto.CourierRequest;
import dto.LoginRequest;
import generator.LoginRequestGenerator;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static generator.CourierRequestGenerator.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierCreatedTest {
    private CourierClient courierClient;
    private Integer id;
    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }
    @Test
    @DisplayName("Создание курьера")
    public void courierShouldBeCreated() {
        CourierRequest randomCourierRequest = getRandomCourierRequest();
        courierClient.create(randomCourierRequest)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));
        LoginRequest loginRequest = LoginRequestGenerator.from(randomCourierRequest);
         id = courierClient.login(loginRequest)
                .assertThat()
                .statusCode(SC_OK)
                .body("id", notNullValue())
                .extract()
                .path("id");
    }
    @Test
    @DisplayName("Нельзя создать двух равных курьеров")
    public void couriersEqualShouldNotBeCreated() {
        CourierRequest randomCourierRequest = getRandomCourierRequest();
        courierClient.create(randomCourierRequest)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));
        courierClient.create(randomCourierRequest)
                .assertThat()
                .statusCode(SC_CONFLICT)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }
    @Test
    @DisplayName("Курьер без пароля не должен создаваться")
    public void courierWithoutPasswordShouldNotBeCreated() {
        CourierRequest randomCourierRequestWithoutPassword = getRandomCourierRequestWithoutPassword();
        courierClient.create(randomCourierRequestWithoutPassword)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
    @After
    public void tearDown() {
        if (id != null) {
            courierClient.delete(id);
        }
    }
}
