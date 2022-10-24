package generator;

import com.github.javafaker.Faker;
import dto.OrderRequest;
import org.apache.commons.lang3.RandomStringUtils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

public class OrderRequestGenerator {
    private static String getStringForDate (Date date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }
    public static OrderRequest getRandomOrderRequest(){
        Random random = new Random();
        Faker faker = new Faker();
        Date date = faker.date().between(new Date(),new Date());
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setFirstName(faker.name().firstName());
        orderRequest.setLastName(faker.name().lastName());
        orderRequest.setAddress(faker.address().fullAddress());
        orderRequest.setMetroStation(Integer.toString((random.nextInt(236))+1));
        orderRequest.setPhone(faker.numerify("8##########"));
        orderRequest.setRentTime(String.valueOf(random.nextInt(6)+1));
        orderRequest.setDeliveryDate(getStringForDate(date));
        orderRequest.setComment((RandomStringUtils.randomAlphanumeric(10)));
        return orderRequest;
    }
}
