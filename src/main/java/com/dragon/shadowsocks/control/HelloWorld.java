package com.dragon.shadowsocks.control;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cjw on 2017/6/11.
 */
@RestController
public class HelloWorld {

    @GetMapping("/say")
    public String say(){
        return "hello world";
    }

}
