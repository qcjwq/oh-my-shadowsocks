package com.dragon.shadowsocks.control;

import com.dragon.shadowsocks.repository.ShadowSocksRepository;
import com.dragon.shadowsocks.service.ScheduledExecutor;
import com.dragon.shadowsocks.service.ScheduledFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cjw on 2017/6/11.
 */
@RestController
public class HelloWorld {

    @Autowired
    private ScheduledFactory scheduledFactory;

    @Autowired
    private ShadowSocksRepository shadowSocksRepository;

    @GetMapping("/say")
    public String say() {
        return "hello world";
    }

    @RequestMapping("/restart")
    public String restart() {
        ScheduledExecutor scheduledExecutor = scheduledFactory.createScheduledExecutor();
        scheduledExecutor.run();
        return "success";
    }

    @RequestMapping("/index/{index}")
    public int setIndex(@PathVariable("index") int index) {
        shadowSocksRepository.setIndex(index);

        return shadowSocksRepository.getIndex();
    }
}
