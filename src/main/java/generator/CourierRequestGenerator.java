package generator;

import dto.CourierRequest;
import org.apache.commons.lang3.RandomStringUtils;

public class CourierRequestGenerator {
    public static CourierRequest getRandomCourierRequest(){
        CourierRequest courierRequest = new CourierRequest();
        courierRequest.setFirstName(RandomStringUtils.randomAlphabetic(8));
        courierRequest.setPassword(RandomStringUtils.randomAlphabetic(8));
        courierRequest.setLogin(RandomStringUtils.randomAlphabetic(8));
        return courierRequest;
    }
    public static CourierRequest getRandomCourierRequestWithoutPassword(){
        CourierRequest courierRequest = new CourierRequest();
        courierRequest.setFirstName(RandomStringUtils.randomAlphabetic(8));
        courierRequest.setLogin(RandomStringUtils.randomAlphabetic(8));
        return courierRequest;
    }
}
