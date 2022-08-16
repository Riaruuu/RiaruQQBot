package org.riaru.boot.riaruqqbot.plugins.impl;

import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import net.mamoe.mirai.message.data.PlainText;
import org.jetbrains.annotations.NotNull;
import org.riaru.boot.riaruqqbot.plugins.BasePlugins;

import java.util.List;

public class ReplyImpl  extends BasePlugins {

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
        String message = event.getMessage().contentToString().toUpperCase();
        if (!CustomizeReplyImpl.MAP.containsKey(message)) {
            return;
        }
        MessageChain chain = MessageUtils.newChain(new PlainText(CustomizeReplyImpl.MAP.get(message)));
        if (CustomizeReplyImpl.QQ_CODE_MAP.containsKey(message)) {
            List<Long> qqCodes = CustomizeReplyImpl.QQ_CODE_MAP.get(message);
            for (Long qqCode : qqCodes) {
                chain = chain.plus(new At(qqCode));
            }
        }
        event.getSubject().sendMessage(chain);
    }
}
