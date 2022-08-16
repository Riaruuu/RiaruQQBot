package org.riaru.boot.riaruqqbot.plugins.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.utils.ExternalResource;
import org.jetbrains.annotations.NotNull;
import org.riaru.boot.riaruqqbot.plugins.BasePlugins;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PixivImpl extends BasePlugins {

    /**
     * 图片数量
     */
    public static int num = 1;

    /**
     * 图片等级 2 4 6
     */
    public static int san = 4;

    /**
     * 图片路径
     */
    public static StringBuffer url = new StringBuffer("https://api.a60.one:8443/");

    /**
     * 关键词
     */
    private static final List<Pattern> PATTERNS = new ArrayList<>() {{
        add(Pattern.compile("^来点[\\s\\S]*色图$"));
    }};

    private String getUrl(String tag) {
        if (StringUtils.isEmpty(tag)) {
           return url.toString();
        }

        return url + "get/tags/" + tag +
                "?num=" + num +
                "&san=" + san;
    }

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
        String content = event.getMessage().contentToString();
        Optional<Matcher> matcher = PATTERNS.stream().filter(ptn -> ptn.matcher(content).matches())
                .map(ptn -> ptn.matcher(content))
                .findFirst();

        if (matcher.isEmpty()) {
            return;
        }

        while (matcher.get().find()) {
            String url = getUrl(matcher.get().group().replaceAll("来点", "").replaceAll("色图", ""));
            JSONObject res = JSONObject.parseObject(HttpUtil.get(url));

            if (!"200".equals(res.getString("code"))) {
                event.getSubject().sendMessage(res.getString("error"));
                return;
            }

            JSONObject img = (JSONObject) res.getJSONObject("data").getJSONArray("imgs").get(0);
            //下载图片 并且上传 获得图片
            File file = HttpUtil.downloadFileFromUrl(img.getString("url"), "pixiv/" + img.getString("pic") + ".jpg");
            Image image = event.getSubject().uploadImage(ExternalResource.create(file));
            event.getSubject().sendMessage(image.plus(new PlainText(img.getString("name"))));
        }
    }
}
