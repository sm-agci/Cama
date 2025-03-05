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
        String number = addPlusIfMissing(phoneNumber);
        if (!tasks.containsKey(number)) {
            tasks.put(number, new ArrayList<>());
        }
        tasks.get(number).add(task);
    }

    private static String addPlusIfMissing(String phoneNumber) {
        return phoneNumber.startsWith("+") ? phoneNumber : "+" + phoneNumber;
    }

    TaskResponse get(String id) {
        return tasks.values().stream().flatMap(Collection::stream).filter(taskList -> taskList.getId().equals(id))
                .findFirst().orElse(null);
    }

    List<TaskResponse> getAll(String phoneNumber) {
        String number= addPlusIfMissing(phoneNumber);
        if (tasks.containsKey(number)) {
            return tasks.get(number);
        }
        return new ArrayList<>();
    }
}
