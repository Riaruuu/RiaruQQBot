package org.riaru.boot.riaruqqbot.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "login-info")
@Component
@Data
public class LoginInfo {

    private long qqCode;

    private String password;

}
