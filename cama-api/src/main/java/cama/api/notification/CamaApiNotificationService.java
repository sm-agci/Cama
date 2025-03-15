package cama.api.notification;

import cama.api.generate.dto.LocationNotification;
import cama.api.generate.dto.NotificationResponse;
import cama.api.generate.dto.SubscriptionEventType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class CamaApiNotificationService {

    private final NotificationStorage storage;

    List<NotificationResponse> getNotifications(String phoneNumber, Integer age, String xCorrelator) {
        log.info("Requesting notifications for phoneNumber: {}, age:{} ", phoneNumber, age);
        String number = phoneNumber.startsWith("+") ? phoneNumber.substring(1): phoneNumber;
        List<LocationNotification> notifications = storage.get(number, age);
        return mapToResponse(notifications);
    }

    private List<NotificationResponse> mapToResponse(List<LocationNotification> notifications) {
        return notifications.stream().map(this::map).toList();
    }

    private NotificationResponse map(LocationNotification notification) {
        NotificationResponse response = new NotificationResponse();
        response.setId(notification.getId());
        response.setType(notification.getType().substring(notification.getType().lastIndexOf(".")+1));
        response.setPhoneNumber(notification.getData().getDevice().getPhoneNumber());
        response.setSubscriptionId(notification.getData().getSubscriptionId());
        response.setTime(notification.getTime());
        response.setArea(notification.getData().getArea());
        return response;
    }

    public void storeNotification(LocationNotification notification, String xCorrelator) {
        log.info("Saving notification:{} ", notification);
        storage.save(notification);
    }
}
