package cama.api.ai;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Command {
    private String aiResponse;
    private String address;
    private Double longitude;
    private Double latitude;
    private LocalDateTime time;
}
