package cama.api.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
public class OplAccessToken {
    @Setter
    @Getter
    @JsonProperty("token_type")
    private String tokenType;
    @Getter
    @Setter
    @JsonProperty("access_token")
    private String accessToken;
    @Getter
    @JsonProperty("expires_in")
    private String expireIn;
    @Getter
    @JsonIgnore
    private LocalDateTime expireAt;


    public OplAccessToken(String tokenType, String accessToken, String expireIn) {
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.expireIn = expireIn;
        setExprieAt();
    }

    public void setExprieAt() {
        this.expireAt = LocalDateTime.now().plusSeconds(Integer.parseInt(expireIn));
    }
}
