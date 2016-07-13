package com.bigvideo.kxj.controller;

import com.bigvideo.kxj.service.MaintainPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xiao on 2016/7/13.
 * 系统维护功能
 */
@RestController
@RequestMapping(value = "sys")
public class SysController {

    @Autowired
    MaintainPerson maintain;

    /**
     * 更新EXCEL的信息和图片
     *
     * @return
     */
    @RequestMapping(value = "updateAll")
    public Msg updateAll() {
        try {
            maintain.updateAllPerson();
        } catch (Exception e) {
            e.printStackTrace();
            return new Msg(0, "Update all excel info fail!");
        }
        return new Msg(1, "Update all excel info Success!");
    }
}
