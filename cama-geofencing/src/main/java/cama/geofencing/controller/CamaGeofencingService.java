package cama.geofencing.controller;

import cama.geofencing.generate.dto.Subscription;
import cama.geofencing.generate.dto.SubscriptionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class CamaGeofencingService {

    Subscription createSubscription(SubscriptionRequest request, String xCorrelator) {
        return null;
    }
}
