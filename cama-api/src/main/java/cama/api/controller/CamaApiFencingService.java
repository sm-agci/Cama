package cama.api.controller;

import cama.api.generate.dto.Task;
import cama.api.generate.dto.TaskResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
class CamaApiFencingService {


    TaskResponse createTask(Task task, String xCorrelator) {
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
