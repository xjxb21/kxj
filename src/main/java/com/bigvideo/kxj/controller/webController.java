package com.bigvideo.kxj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xiao on 2016/7/15.
 */
@Controller
public class webController {

    @RequestMapping(value = "/")
    public String index() {
        System.out.println("哈喽 123");
        return "index.html1231212";
    }
}
