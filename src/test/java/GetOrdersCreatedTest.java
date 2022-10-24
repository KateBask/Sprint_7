import client.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;
public class GetOrdersCreatedTest {
    private OrderClient orderClient;
    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }
    @Test
    @DisplayName("Возвращается список заказов")
    public void getCreateOrderList() {
        orderClient.getOrderList()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("orders", notNullValue());
    }
}
