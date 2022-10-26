import client.OrderClient;
import dto.OrderRequest;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.List;

import static generator.OrderRequestGenerator.getRandomOrderRequest;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class OrderParameterizedCreatedTest {
    private OrderClient orderClient;
    Integer track;
    private final List<String> color;

    public OrderParameterizedCreatedTest (List<String> color) {
        this.color = color;
    }
    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }
    @Parameterized.Parameters
    public static List<List<String>> color() {
        return List.of (
                List.of("BLACK"),
                List.of("GREY"),
                List.of("GREY","BLACK"),
                List.of("","")
        );
    }
    @Test
    @DisplayName("Создание заказа с разными цветами и без цвета")
    public void orderShouldBeCreated() {
        OrderRequest randomOrder = getRandomOrderRequest();
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setColor(color);
        orderClient.createOrder(randomOrder)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("track", Matchers.notNullValue())
                .extract()
                .path("track");
    }
    @After
    public void tearDown() {
        if (track != null)
            orderClient.cancelOrder(track)
                    .assertThat()
                    .statusCode(SC_CREATED)
                    .body("ok", equalTo(true));
    }
}
