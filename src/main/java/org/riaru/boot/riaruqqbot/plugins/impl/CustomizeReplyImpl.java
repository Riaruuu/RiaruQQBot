package org.riaru.boot.riaruqqbot.plugins.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.*;
import org.jetbrains.annotations.NotNull;
import org.riaru.boot.riaruqqbot.plugins.BasePlugins;
import org.thymeleaf.util.StringUtils;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CustomizeReplyImpl extends BasePlugins {

    private final static Pattern PATTERN = Pattern.compile("^[\\s\\S]*有人发[\\s\\S]*你回[\\s\\S]*$");

    public final static Map<String, String> MAP = new HashMap<>();

    public final static Map<String, List<Long>> QQ_CODE_MAP = new HashMap<>();

    @Override
    protected boolean baseLicense() {
        return true;
    }

    @Override
    @EventHandler
    public void onMessage(@NotNull MessageEvent event) throws Exception {
        super.onMessage(event);
    }

    @Override
    protected boolean passLicense(MessageEvent event) {
        return true;
    }

    @Override
    protected void active(MessageEvent event) {
        MessageChain chain = event.getMessage();
        if (!PATTERN.matcher(chain.contentToString()).matches()) {
            return;
        }
        ReplyReturn replyReturn = getAt(event.getMessage());
        String message = replyReturn.getChain().contentToString();

        String receiveMessage = message.substring(message.indexOf("有人发") + 3, message.indexOf("你回")).toUpperCase();
        String returnMessage = message.substring(message.indexOf("你回") + 2);
        if (StringUtils.isEmpty(receiveMessage) || StringUtils.isEmpty(returnMessage)) {
            event.getSubject().sendMessage(new At(event.getSender().getId()).plus(new PlainText("指令要发全捏！")));
            return;
        }
        event.getSubject().sendMessage(new At(event.getSender().getId()).plus(new PlainText("收到了喵！")));
        MAP.put(receiveMessage, returnMessage);
        if (replyReturn.getQqCodes().size() > 0) QQ_CODE_MAP.put(receiveMessage, replyReturn.getQqCodes());
    }

    private ReplyReturn getAt(MessageChain chain) {
        // 获取被at的QQ号
        List<Long> qqCodes = chain.stream().filter(msg -> msg instanceof At)
                .map(msg -> ((At) msg).getTarget()).collect(Collectors.toList());

        // 将at的内容去除
        MessageChain rChain = MessageUtils.newChain();
        for (SingleMessage singleMessage : chain) {
            if (singleMessage instanceof At) {
                continue;
            }
            rChain = rChain.plus(singleMessage);
        }
        return new ReplyReturn(qqCodes, rChain);
    }

}

@Data
@NoArgsConstructor
@AllArgsConstructor
class ReplyReturn {

    private List<Long> qqCodes = new ArrayList<>();

    private MessageChain chain = MessageUtils.newChain();
}

