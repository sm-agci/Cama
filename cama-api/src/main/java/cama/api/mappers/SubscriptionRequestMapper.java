package cama.api.mappers;

import cama.api.ai.Command;
import cama.api.config.CamaFencingConfig;
import cama.api.generate.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SubscriptionRequestMapper {
    private final CamaFencingConfig fencingConfig;
//    public SubscriptionRequest createSubscriptionRequest(Command command, Task task) {
//        SubscriptionDetail subscriptionDetail = new SubscriptionDetail();
//        Device device = new Device();
//        device.setPhoneNumber("+33699901033"); // todo set
//        subscriptionDetail.setDevice(device);
//        Circle area = new Circle();
//        area.setRadius(2000);//todo set
//        area.setCenter(new Point(48.86074, 2.29485)); //todo set
//        area.setAreaType(AreaType.CIRCLE);
//        subscriptionDetail.setArea(area);
//        Config config = new Config();
//        config.setInitialEvent(true);
//        config.setSubscriptionDetail(subscriptionDetail);
//        config.setSubscriptionMaxEvents(5); //todo set?
//        config.setSubscriptionExpireTime(OffsetDateTime.now().plusMinutes(60)); //todo set?
//        SubscriptionRequest request = new HTTPSubscriptionRequest();
//        request.setSink(URI.create("https://cama-api.onrender.com/api/v1/cama/notifications/retrieve"));
//        request.setProtocol(Protocol.HTTP);
//        //  request.setSink(URI.create("http://localhost:8080/api/v1/cama/notifications/retrieve")); //todo set
//        request.setTypes(List.of(SubscriptionEventType.ENTERED));  //todo set
////        request.setTypes(List.of(SubscriptionEventType.ENTERED, SubscriptionEventType.LEFT));  //todo set
//        request.setConfig(config);
//        return request;
//    }

    public SubscriptionRequest createSubscriptionRequest(Command command, String phoneNumber) {
        SubscriptionDetail subscriptionDetail = new SubscriptionDetail();
        Device device = new Device();
        device.setPhoneNumber(getPhoneNumberWithPlus(phoneNumber));
        subscriptionDetail.setDevice(device);
        Circle area = new Circle();
        area.setRadius(fencingConfig.getRadius());
        area.setCenter(new Point(command.getLatitude(), command.getLongitude()));
        area.setAreaType(AreaType.CIRCLE);
        subscriptionDetail.setArea(area);
        Config config = new Config();
        config.setInitialEvent(true);
        config.setSubscriptionDetail(subscriptionDetail);
        config.setSubscriptionMaxEvents(fencingConfig.getMaxEvents());
        OffsetDateTime endTime = OffsetDateTime.now().plusMinutes(fencingConfig.getMaxWindowInMin());
        if(command.getTime() != null) {
            ZoneId zoneId = ZoneId.systemDefault();
            endTime = command.getTime().atZone(zoneId).toOffsetDateTime().plusMinutes(fencingConfig.getMaxWindowInMin());
        }
        config.setSubscriptionExpireTime(endTime);
        SubscriptionRequest request = new HTTPSubscriptionRequest();
        request.setSink(URI.create(fencingConfig.getNotificationUrl()));
        request.setProtocol(Protocol.HTTP);
        request.setTypes(List.of(SubscriptionEventType.ENTERED));
//        request.setTypes(List.of(SubscriptionEventType.ENTERED, SubscriptionEventType.LEFT));
        request.setConfig(config);
        return request;
    }

    private static String getPhoneNumberWithPlus(String phoneNumber) {
        return phoneNumber.startsWith("+") ? phoneNumber : "+" + phoneNumber;
    }
}
