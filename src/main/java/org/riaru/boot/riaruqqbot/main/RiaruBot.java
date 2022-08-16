package org.riaru.boot.riaruqqbot.main;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;
import org.riaru.boot.riaruqqbot.config.LoginInfo;
import org.riaru.boot.riaruqqbot.plugins.PluginsRegister;
import org.riaru.boot.riaruqqbot.plugins.impl.CustomizeReplyImpl;
import org.riaru.boot.riaruqqbot.plugins.impl.HelpImpl;
import org.riaru.boot.riaruqqbot.plugins.impl.PixivImpl;
import org.riaru.boot.riaruqqbot.plugins.impl.ReplyImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class RiaruBot {

    private static LoginInfo loginInfo;

    private static Bot bot;

    @Resource(name = "helpImpl")
    private HelpImpl help;

    @Resource
    private PluginsRegister pluginsRegister;

    @Autowired
    public RiaruBot(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    public void login() {
        if (bot == null) {
            bot = BotFactory.INSTANCE.newBot(loginInfo.getQqCode(), loginInfo.getPassword(), new BotConfiguration() {{
                setProtocol(MiraiProtocol.MACOS);
            }});
        }
        bot.login();
        registerPlugins();
    }

    private void registerPlugins() {
        pluginsRegister.register(new PixivImpl());
        pluginsRegister.register(help);
        pluginsRegister.register(new CustomizeReplyImpl());
        pluginsRegister.register(new ReplyImpl());
    }

}
