package cama.api.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient.Builder chatClientBuilder(OpenAiChatModel chatModel) {
        return ChatClient.builder(chatModel);
    }

    //    @Bean
//    public ChatClient.Builder chatClientBuilder(ChatModel model) {
//        return ChatClient.builder(model);
//    }
   @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
        var defaultConfig = ChatOptions.builder()
                .temperature(0.3)
                .build();
        return builder
                .defaultSystem("You are and geo location expert")
                .defaultOptions(defaultConfig)
                .build();
    }
}
