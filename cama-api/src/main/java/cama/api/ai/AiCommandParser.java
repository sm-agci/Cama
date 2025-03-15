package cama.api.ai;

import cama.api.generate.dto.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AiCommandParser {

    private final ChatClient chatClient;
    private final AiConfig aiConfig;
    private final String promptStr = "You are a geolocation expert, based on the task text: %s" +
            " specify the approximate location of this place by providing its address, coordinates and the time when the task should be performed." +
            " Present the data in the form of json, which will contain the fields: name, address, latitude, longitude, time and result. " +
            " Name field should represent a short, recognizable name of the place.  If the time is undefined use null. " +
            " Result filed accepts two values: OK - coordinates are present, FAILED - unable to get coordinates. " +
            " Keep responses short, concise, and easy to understand.";
    public Command parse(Task task) {
        String taskCommand = String.format(promptStr, task.getCommand());
        Prompt prompt = new Prompt(taskCommand);
        Command command = aiConfig.isEnabled() ? callChatGpt(prompt, taskCommand) : getCommand();
        command.setTaskCommand(task.getCommand());
        return command;
    }

    private Command callChatGpt(Prompt prompt, String taskCommand) {
 //       return "{\"name\": \"Eiffel Tower\",\"address\": \"Champ de Mars, 5 Avenue Anatole France, 75007 Paris, France\",\"latitude\": 48.8584,\"longitude\": 2.2945,\"time\": null}";
        return chatClient
                .prompt()
                .user(taskCommand)
                .call()
                .entity(Command.class);
    }

    private Command getCommand() {
        Command command = new Command();
        command.setLongitude(2.29485);
        command.setLatitude(48.86074);
        command.setTime(null);
        command.setAddress("Champ de Mars, 5 Avenue Anatole France, 75007 Paris, France");
        command.setName("Paris Eiffel");
        return command;
    }
}
