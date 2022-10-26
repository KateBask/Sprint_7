package client;

import dto.OrderRequest;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
public class OrderClient extends RestClient {
    public static final String ORDER = "/api/v1/orders";
    public static final String CANCEL = "/api/v1/orders/cancel";
    public ValidatableResponse createOrder (OrderRequest orderRequest) {
        return given()
                .spec(getDefaultRequestSpec())
                .body(orderRequest)
                .post(ORDER)
                .then();
    }
    public ValidatableResponse cancelOrder(Integer track) {
        return given()
                .spec(getDefaultRequestSpec())
                .body(track)
                .put(CANCEL)
                .then();
    }
    public ValidatableResponse getOrderList() {
        return given()
                .spec(getDefaultRequestSpec())
                .get(ORDER)
                .then();
    }
}


