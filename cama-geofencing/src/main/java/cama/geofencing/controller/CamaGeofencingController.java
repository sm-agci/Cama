package cama.geofencing.controller;

import cama.geofencing.generate.dto.Subscription;
import cama.geofencing.generate.dto.SubscriptionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
public class CamaGeofencingController {//} implements cama.geofencing.generate.controller.GeofencingSubscriptionsApi {

    private final CamaGeofencingService service;
//    @Override
//    public ResponseEntity<Subscription> createSubscription(SubscriptionRequest request, String xCorrelator) {
//        log.debug("[createSubscription] body = {}, x-correlator: {}", request, xCorrelator);
//        Subscription subscription= service.createSubscription(request, xCorrelator);
//        return ResponseEntity.ok()
//                .body(subscription);
//    }
}
