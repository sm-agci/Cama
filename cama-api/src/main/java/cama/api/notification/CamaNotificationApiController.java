package cama.api.notification;

import cama.api.generate.controller.LocationNotificationApi;
import cama.api.generate.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CamaNotificationApiController implements LocationNotificationApi {

    private final CamaApiNotificationService service;

    @Override
    public ResponseEntity<Void> retrieveNotifications(LocationNotification notification) {
        String xCorrelator = UUID.randomUUID().toString();
        MDC.put("uniqueId", xCorrelator);
        log.debug("[retrieveNotifications] x-correlator: {}, notification: {}", xCorrelator, notification);
        if (!notification.getType().contains("subscription-ends")) {
            service.storeNotification(notification, xCorrelator);
        } else {
            //todo subscriptionEnd - when comes remove task
        }
        MDC.clear();
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<NotificationResponse>> getNotifications(String phoneNumber, Integer age) {
        String xCorrelator = UUID.randomUUID().toString();
        MDC.put("uniqueId", xCorrelator);
        log.debug("[getNotifications]  phoneNumber: {}, age:{}", phoneNumber, age);
        List<NotificationResponse> tasks = service.getNotifications(phoneNumber, age, xCorrelator);
        MDC.clear();
        return ResponseEntity.ok()
                .body(tasks);
    }
}
