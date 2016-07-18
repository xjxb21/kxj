package com.bigvideo.kxj.controller;

import com.bigvideo.kxj.service.IFaceSearchTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xiao on 2016/7/12.
 * C++对接相关功能
 */
@RestController
@RequestMapping(value = "/face")
public class InterfaceController {

    @Autowired
    IFaceSearchTaskService IFaceSearchTaskService;

    /**
     * FLEX前端POST现场图片
     *
     * @param request
     * @return
     * @throws IOException
     */
    //ADDTask 改名为 compareTask
    @RequestMapping(value = "/compareTask", method = RequestMethod.POST)
    public Msg addFaceCompareTask(HttpServletRequest request) throws IOException {

        InputStream in = request.getInputStream();
        int formLength = request.getContentLength();

        //执行对比任务,获取SESSIONID
        String sessionid = IFaceSearchTaskService.compareFace(in, formLength);

        //获取对应最像的对比结果


        return new Msg(1, "save success");
    }
}
