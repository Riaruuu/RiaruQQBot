package org.riaru.boot.riaruqqbot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "help")
@Component
public class Help {

    private List<String> command;

}
