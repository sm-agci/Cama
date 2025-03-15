package cama.api.scheduler;

import cama.api.generate.dto.EventData;
import cama.api.generate.dto.LocationNotification;
import cama.api.generate.dto.Notification;
import cama.api.generate.dto.Subscription;
import cama.api.notification.CamaApiNotificationService;
import cama.api.notification.CamaNotificationApiController;

import java.time.OffsetDateTime;
import java.util.UUID;

public class NotificationTask implements  Runnable{
    private final Subscription subscription;
    private final CamaApiNotificationService service;
    private final String xCorrelator;

    public NotificationTask(CamaApiNotificationService service, Subscription subscription,  String uuid){
        this.subscription = subscription;
        this.service = service;
        this.xCorrelator = uuid;
    }

    @Override
    public void run() {
        LocationNotification notification = createNotification(subscription);
        service.storeNotification(notification, xCorrelator);
    }

    private LocationNotification createNotification(Subscription subscription) {
        LocationNotification notification = new LocationNotification();
        notification.setId(UUID.randomUUID().toString());
        notification.setTime(OffsetDateTime.now());
        notification.setType(subscription.getTypes().get(0));
        notification.setSource("com.orange.camara.geofencing");
        notification.setDatacontenttype("application/json");
        notification.setSpecversion("1.0");
        EventData data = new EventData();
        data.setDevice(subscription.getConfig().getSubscriptionDetail().getDevice());
        data.setSubscriptionId(subscription.getId());
        data.setArea(subscription.getConfig().getSubscriptionDetail().getArea());
        notification.setData(data);
        return  notification;
    }
}
