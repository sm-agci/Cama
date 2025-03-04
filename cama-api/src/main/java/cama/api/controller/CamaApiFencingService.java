package cama.api.controller;

import cama.api.config.CamaFencingConfig;
import cama.api.generate.dto.*;
import cama.api.webclient.OplSandboxGeofencingClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
class CamaApiFencingService {

    private final TaskStorage taskStorage;

    TaskResponse createTask(Task task, String xCorrelator) {
        SubscriptionRequest subscriptionRequest = createSubscriptionRequest(task);
        log.info("Creating subscription: {}, task: {}", task, subscriptionRequest);
//        Subscription response = webClient.post(config.getUrl(),
//                subscriptionRequest, xCorrelator, Subscription.class);
        Subscription response = null;
        log.info("Subscription response: {}, task: {}", response, task);
        TaskResponse taskResponse= mapToTaskResponse(task, response);
        taskStorage.save(task.getPhoneNumber(), taskResponse);
        return taskResponse;
    }

    private SubscriptionRequest createSubscriptionRequest(Task task) {
        SubscriptionDetail subscriptionDetail = new SubscriptionDetail();
        Device device = new Device();
        device.setPhoneNumber("+33699901033"); // todo set
        subscriptionDetail.setDevice(device);
        Circle area = new Circle();
        area.setRadius(2000);//todo set
        area.setCenter(new Point(48.86074,2.29485)); //todo set
        area.setAreaType(AreaType.CIRCLE);
        subscriptionDetail.setArea(area);
        Config config = new Config();
        config.setInitialEvent(true);
        config.setSubscriptionDetail(subscriptionDetail);
//        config.setSubscriptionMaxEvents(20); //todo set?
//        config.setSubscriptionExpireTime(); //todo set?
        SubscriptionRequest request =new SubscriptionRequest();
        request.setProtocol(Protocol.HTTP);
       // request.setSink(URI.create("https://cama-api.onrender.com/api/v1/cama/notifications/retrieve")); //todo set
        request.setSink(URI.create("http://localhost:8080/api/v1/cama/notifications/retrieve")); //todo set
        request.setTypes(List.of(SubscriptionEventType.ENTERED, SubscriptionEventType.LEFT));  //todo set
        request.setTypes(List.of(SubscriptionEventType.ENTERED, SubscriptionEventType.LEFT));  //todo set
        request.setConfig(config);
        return request;
    }

    private TaskResponse mapToTaskResponse(Task task, Subscription subscription) {
        TaskResponse response = new TaskResponse();
        response.setId(subscription.getId());
        response.setPhoneNumber(subscription.getConfig().getSubscriptionDetail().getDevice().getPhoneNumber());
        response.setArea(subscription.getConfig().getSubscriptionDetail().getArea());
        response.setAddress("Paris Eiffel");//todo set
        response.setStartTime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        response.setEndTime(null); //todo set
        response.setType(subscription.getTypes());
        response.setStatus(subscription.getStatus().getValue());

        return response;
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
    }

    void deleteTask(String taskId, String xCorrelator) {
        taskStorage.delete(taskId);
    }

    TaskResponse getTask(String id, String xCorrelator) {
       return taskStorage.get(id);
    }

    List<TaskResponse> getTasks(String phoneNumber) {
        return taskStorage.getAll(phoneNumber);
    }
}
