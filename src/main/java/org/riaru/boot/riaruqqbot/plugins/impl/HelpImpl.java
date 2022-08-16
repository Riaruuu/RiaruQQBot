package org.riaru.boot.riaruqqbot.plugins.impl;

import net.mamoe.mirai.contact.*;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import org.jetbrains.annotations.NotNull;
import org.riaru.boot.riaruqqbot.config.Help;
import org.riaru.boot.riaruqqbot.plugins.BasePlugins;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class HelpImpl extends BasePlugins {

    @Resource
    private Help help;

    @Override
    @EventHandler
    public void onMessage(@NotNull MessageEvent event) throws Exception {
        super.onMessage(event);
    }

    @Override
    protected boolean baseLicense() {
        return true;
    }

    @Override
    protected boolean passLicense(MessageEvent event) {
        return true;
    }

    @Override
    protected void active(MessageEvent event) {
        if ("#帮助".equals(event.getMessage().contentToString())) {
            MessageChainBuilder chain = new MessageChainBuilder();
            for (String x : help.getCommand()) {
                chain.append(new PlainText(x));
            }
            event.getSubject().sendMessage(chain.build());
        }
    }
}
