package org.riaru.boot.riaruqqbot.controller;

import org.riaru.boot.riaruqqbot.main.RiaruBot;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@CrossOrigin
public class OuterController {

    @Resource
    private RiaruBot riaruBot;

    @RequestMapping("/login")
    public String test() {
        riaruBot.login();
        return "登陆中";
    }


}
