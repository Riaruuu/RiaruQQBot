package org.riaru.boot.riaruqqbot.plugins;

import net.mamoe.mirai.event.GlobalEventChannel;
import org.springframework.stereotype.Component;

@Component
public class PluginsRegister {

    public void register(BasePlugins basePlugins) {
        GlobalEventChannel.INSTANCE.registerListenerHost(basePlugins);
    }

}
