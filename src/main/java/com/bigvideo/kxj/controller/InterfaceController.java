package com.bigvideo.kxj.controller;

import com.bigvideo.kxj.service.FaceSearchTaskService;
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
    FaceSearchTaskService faceSearchTaskService;

    /**
     * FLEX前端POST现场图片
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/addTask", method = RequestMethod.POST)
    public void addFaceCompareTask(HttpServletRequest request) throws IOException {

        InputStream in = request.getInputStream();
        int formLength = request.getContentLength();

        faceSearchTaskService.addTask(in, formLength);

    }
}
