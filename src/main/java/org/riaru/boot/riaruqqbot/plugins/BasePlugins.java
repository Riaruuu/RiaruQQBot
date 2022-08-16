package org.riaru.boot.riaruqqbot.plugins;

import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.MessageEvent;
import org.jetbrains.annotations.NotNull;
import org.riaru.boot.riaruqqbot.common.LicenseUtils;

/**
 * 插件接口
 */
public abstract class BasePlugins extends SimpleListenerHost {

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        super.handleException(context, exception);
    }

    @EventHandler
    public void onMessage(@NotNull MessageEvent event) throws Exception {
        //如果走基础校验并且不通过
        if (this.baseLicense() && !LicenseUtils.passLicense()) {
            return;
        }

        if (!this.passLicense(event)) {
            return;
        }

        this.active(event);
    }

    protected abstract boolean baseLicense();

    protected abstract boolean passLicense(MessageEvent event);

    protected abstract void active(MessageEvent event);

}
