package cama.api.controller;

import cama.api.generate.dto.Task;
import cama.api.generate.dto.TaskResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
class TaskStorage {
    Map<String, List<TaskResponse>> tasks = new HashMap<>();

    void delete(String id) {
        tasks.values().forEach(taskList -> taskList.removeIf(task -> task.getId().equals(id)));
    }

    void save(String phoneNumber, TaskResponse task) {
        if (!tasks.containsKey(phoneNumber)) {
            tasks.put(phoneNumber, new ArrayList<>());
        }
        tasks.get(phoneNumber).add(task);
    }

    TaskResponse get(String id) {
        return tasks.values().stream().flatMap(Collection::stream).filter(taskList -> taskList.getId().equals(id))
                .findFirst().orElse(null);
    }

    List<TaskResponse> getAll(String phoneNumber) {
        if (tasks.containsKey(phoneNumber)) {
            return tasks.get(phoneNumber);
        }
        return new ArrayList<>();
    }
}
