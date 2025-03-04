package cama.api.notification;

import cama.api.generate.dto.LocationNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
class NotificationStorage {
    Map<String, List<LocationNotification>> tasks = new HashMap<>();
    List<LocationNotification> get(String phoneNumber, Integer age) {
        //todo filter by age
        if (tasks.containsKey(phoneNumber)) {
            return tasks.get(phoneNumber).stream().sorted(Comparator.comparing(LocationNotification::getTime).reversed()).toList();
        }
        return new ArrayList<>();
    }

    void save(LocationNotification notification) {
        String phoneNumber = notification.getData().getDevice().getPhoneNumber();
        String number = phoneNumber.startsWith("+") ? phoneNumber.substring(1): phoneNumber;
        if (!tasks.containsKey(number)) {
            tasks.put(number, new ArrayList<>());
        }
        tasks.get(number).add(notification);
    }
}
