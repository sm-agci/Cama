package cama.api.controller;

import cama.api.ai.AiCommandParser;
import cama.api.ai.Command;
import cama.api.config.CamaFencingConfig;
import cama.api.generate.dto.Subscription;
import cama.api.generate.dto.SubscriptionRequest;
import cama.api.generate.dto.Task;
import cama.api.generate.dto.TaskResponse;
import cama.api.mappers.SubscriptionRequestMapper;
import cama.api.mappers.TaskResponseMapper;
import cama.api.webclient.OplSandboxGeofencingClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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

    TaskResponse createTask(Task task, String xCorrelator) {
        Command command = commandParser.parse(task);
        SubscriptionRequest subscriptionRequest = subscriptionRequestMapper
                .createSubscriptionRequest(command, task.getPhoneNumber());
        log.info("Creating subscription: {}, task: {}", task, subscriptionRequest);
        Subscription response = webClient.post(config.getSimulatorUrl(),
                subscriptionRequest, xCorrelator, Subscription.class);
//        Subscription response= null;
        log.info("Subscription response: {}, task: {}", response, task);
        TaskResponse taskResponse= taskResponseMapper.mapToTaskResponse(command, response);
        taskStorage.save(task.getPhoneNumber(), taskResponse);
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
