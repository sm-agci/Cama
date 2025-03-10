package cama.api.ai;

import cama.api.exceptions.PromptException;
import cama.api.generate.dto.Task;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper;
    private final String promptStr = "You are a geolocation expert, based on the task text: %s" +
            " specify the approximate location of this place by providing its address, coordinates and the time when the task should be performed." +
            " Present the data in the form of json, which will contain the fields: name, address, latitude, longitude, time and result. " +
            " Name field should represent a short, recognizable name of the place.  If the time is undefined use null. " +
            " Result filed accepts two values: OK - coordinates are present, FAILED - unable to get coordinates. " +
            " Keep responses short, concise, and easy to understand.";
    public Command parse(Task task) {
        String taskCommand = String.format(promptStr, "When i will come near Eiffel Tower remind me about buying gift for kids friend birthday party.");
        Prompt prompt = new Prompt(taskCommand);
//        String content = getChatGptContent(prompt, taskCommand);
//        Command command;
//        try {
//            command = objectMapper.readValue(content, Command.class);
//            command.setTaskCommand(task.getCommand());
//        } catch (JsonProcessingException e) {
//            throw new PromptException(e);
//        }
        Command command = callChatGpt(prompt, taskCommand);
        command.setTaskCommand(task.getCommand());
        return command;
    }

    private String getChatGptContent(Prompt prompt, String taskCommand) {
 //       return "{\"name\": \"Eiffel Tower\",\"address\": \"Champ de Mars, 5 Avenue Anatole France, 75007 Paris, France\",\"latitude\": 48.8584,\"longitude\": 2.2945,\"time\": null}";
        return chatClient
//                .prompt(prompt)
//                .user(taskCommand)
                .prompt()
                .user(taskCommand)
                .call()
                .content();
    }
    private Command callChatGpt(Prompt prompt, String taskCommand) {
 //       return "{\"name\": \"Eiffel Tower\",\"address\": \"Champ de Mars, 5 Avenue Anatole France, 75007 Paris, France\",\"latitude\": 48.8584,\"longitude\": 2.2945,\"time\": null}";
        return chatClient
//                .prompt(prompt)
//                .user(taskCommand)
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
        command.setAddress("Paris Eiffel");
        return command;
    }


  //  Pewnie nawet wystarczyłby 1 prompt, od tego bym zaczął. Udostępnij LLMowi funkcję
    //  do wyszukiwania koordynatów (bodajże drugie nagranie ze Spring AI dotyczyło konkretnie
    //  function callingu), w prompcie zacznij od raczej niskiej temperatury (powiedzmy 0,5
    //  albo nawet niżej), opisz mu proces (1. wyodrębnij takie i takie dane z tekstu, 2.
    //  użyj tych danych do wywołania podanej funkcji, 3. odebrane koordynaty zapakuj do
    //  takiego formatu). U nas w projekcie zauważyliśmy, że bardzo duży wpływ ma podanie
    //  LLMowi przykładu, więc też polecam zawrzeć w prompcie min. 1 przykład inputu/outputu.
}
