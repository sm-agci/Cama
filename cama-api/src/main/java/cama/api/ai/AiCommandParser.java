package cama.api.ai;

import cama.api.generate.dto.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AiCommandParser {


    public Command parse(Task task) {
        return getCommand();
    }

    private Command getCommand() {
        Command command = new Command();
        command.setLongitude(2.29485);
        command.setLatitude(48.86074);
        command.setTime(null);
        command.setAddress("Paris Eiffel");
        return command;
    }
}
