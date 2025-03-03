package cama.api.controller;

import cama.api.config.CamaFencingConfig;
import cama.api.config.CamaOtpConfig;
import cama.api.config.CamaOtpServiceConfig;
import cama.api.generate.dto.*;
import cama.api.local.otp.CamaOtpLocalMockService;
import cama.api.webclient.CamaOtpWebClient;
import cama.api.webclient.OplSandboxGeofencingClient;
import cama.api.webclient.OplWebClientProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
class CamaApiFencingService {

    private final CamaFencingConfig config;
    private final OplSandboxGeofencingClient webClient;

    TaskResponse createTask(Task task, String xCorrelator) {
        SubscriptionRequest subscriptionRequest = createSubscriptionRequest(task);
        Subscription response = webClient.post(config.getUrl(),
                subscriptionRequest, xCorrelator, Subscription.class);
        return mapToTaskResponse(response);
    }

    private SubscriptionRequest createSubscriptionRequest(Task task) {
//        "protocol": "HTTP",
//                "sink": "https://notificationSendServer12.supertelco.com",
//                "types": [
//        "org.camaraproject.geofencing-subscriptions.v0.area-entered"
//  ],
//        "config": {
//            "subscriptionDetail": {
//                "device": {
//                    "phoneNumber": "+33699901032"
//                },
//                "area": {
//                    "areaType": "CIRCLE",
//                            "center": {
//                        "latitude": "48.80",
//                                "longitude": "2.259"
//                    },
//                    "radius": 2000
//                }
//            },
//            "initialEvent": true,
//                    "subscriptionMaxEvents": 10,
//                    "subscriptionExpireTime": "2024-03-22T05:40:58.469Z"
//        }
        return null;
    }

    private TaskResponse mapToTaskResponse(Subscription subscription) {
//        {
//            "protocol": "HTTP",
//                "sink": "https://endpoint.example.com/sink",
//                "sinkCredential": {
//            "credentialType": "PLAIN"
//        },
//            "types": [
//            "string"
//  ],
//            "config": {
//            "subscriptionDetail": {
//                "device": {
//                    "phoneNumber": "+33699901032"
//                },
//                "area": {
//                    "areaType": "CIRCLE",
//                            "center": {
//                        "latitude": "48.80",
//                                "longitude": "2.259"
//                    },
//                    "radius": 2000
//                }
//            },
//            "subscriptionExpireTime": "2023-01-17T13:18:23.682Z",
//                    "subscriptionMaxEvents": 5,
//                    "initialEvent": true
//        },
//            "id": "1119920371",
//                "startsAt": "2024-07-11T19:08:47.612Z",
//                "expiresAt": "2024-07-11T19:08:47.612Z",
//                "status": "ACTIVE"
//        }
        return null;
    }

    void deleteTask(String taskId, String xCorrelator) {
    }

    TaskResponse getTask(String id, String xCorrelator) {
        return null;
    }

    List<TaskResponse> getTasks(String xCorrelator) {
        return null;
    }
}
