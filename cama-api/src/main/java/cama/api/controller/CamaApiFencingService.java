package cama.api.controller;

import cama.api.ai.AiCommandParser;
import cama.api.ai.Command;
import cama.api.config.CamaFencingConfig;
import cama.api.generate.dto.*;
import cama.api.mappers.SubscriptionRequestMapper;
import cama.api.mappers.TaskResponseMapper;
import cama.api.notification.CamaApiNotificationService;
import cama.api.scheduler.NotificationTask;
import cama.api.webclient.OplSandboxGeofencingClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
class CamaApiFencingService {

    private final TaskStorage taskStorage;
    private final OplSandboxGeofencingClient webClient;
    private final CamaFencingConfig config;
    private final AiCommandParser commandParser;
    private final SubscriptionRequestMapper subscriptionRequestMapper;
    private final TaskResponseMapper taskResponseMapper;
    private final CamaApiNotificationService notificationService;
    private final ThreadPoolTaskScheduler taskScheduler;

    TaskResponse createTask(Task task, String xCorrelator) {
        Command command = commandParser.parse(task);
        SubscriptionRequest subscriptionRequest = subscriptionRequestMapper
                .createSubscriptionRequest(command, task.getPhoneNumber());
        log.info("Creating subscription: {}, task: {}", task, subscriptionRequest);
        Subscription response = webClient.post(config.getSimulatorUrl(),
                subscriptionRequest, xCorrelator, Subscription.class);
        response.setProtocol(Protocol.HTTP);
//        Subscription response= null;
        log.info("Subscription response: {}, task: {}", response, task);
        TaskResponse taskResponse= taskResponseMapper.mapToTaskResponse(command, task, response);
        taskStorage.save(task.getPhoneNumber(), taskResponse);

        taskScheduler.scheduleWithFixedDelay(
                new NotificationTask(notificationService, response,xCorrelator),
                Duration.ofSeconds(15)
        );

        return taskResponse;
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
