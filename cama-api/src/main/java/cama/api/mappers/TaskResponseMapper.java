package cama.api.mappers;

import cama.api.ai.Command;
import cama.api.generate.dto.Subscription;
import cama.api.generate.dto.Task;
import cama.api.generate.dto.TaskResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TaskResponseMapper {

    public TaskResponse mapToTaskResponse(Command command, Task task, Subscription subscription) {
        TaskResponse response = new TaskResponse();
        response.setId(subscription.getId());
        response.setPhoneNumber(subscription.getConfig().getSubscriptionDetail().getDevice().getPhoneNumber());
        response.setArea(subscription.getConfig().getSubscriptionDetail().getArea());
        response.setAddress(command.getAddress());
        response.setCommand(task.getCommand());
        response.setProcessedCommand(command.getAiResponse());
        response.setStartTime(subscription.getStartsAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        response.setEndTime(subscription.getExpiresAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        response.setType(subscription.getTypes());
        response.setStatus(subscription.getStatus() != null ? subscription.getStatus().getValue() : null);

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
}
