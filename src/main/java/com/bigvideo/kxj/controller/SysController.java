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
    public OperMessage updateAll() {
        try {
            maintain.updateAllPerson();
        } catch (Exception e) {
            e.printStackTrace();
            return new OperMessage("FAILED", "Update all excel info FAILED!", null);
        }
        return new OperMessage("SUCCESS", "Update all excel info SUCCESS!", null);
    }
}
