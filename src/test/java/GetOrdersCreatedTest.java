import client.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GetOrdersCreatedTest {
    private OrderClient orderClient;
    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }
    @Test
    @DisplayName("Список заказов возвращается, не пустой")
    public void getCreateOrderList() {
        ValidatableResponse response = orderClient.getOrderList();
        int statusCode = response.extract().statusCode();
        assertEquals( SC_OK, statusCode);
        ArrayList<String> orderBody = response.extract().path("orders");
        boolean isNotEmpty = orderBody!=null && !orderBody.isEmpty();
        assertTrue("Список заказов пуст", isNotEmpty);
    }
}
