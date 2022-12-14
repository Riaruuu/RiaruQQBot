package org.riaru.boot.riaruqqbot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "admin")
public class Admin {

    private long id;

}
