package cama.api.ai;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Command {
    private String taskCommand;
    private String name;
    private String address;
    private Double longitude;
    private Double latitude;
    private String result;
    private LocalDateTime time;
}
