package com.bigvideo.kxj.controller;

import com.bigvideo.kxj.dao.IBigPersonDao;
import com.bigvideo.kxj.dao.IBigPersonPhotoDao;
import com.bigvideo.kxj.entity.BigPerson;
import com.bigvideo.kxj.message.OperMessageCompareRet;
import com.bigvideo.kxj.service.IFaceSearchResultSrv;
import com.bigvideo.kxj.service.IFaceSearchTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xiao on 2016/7/12.
 * C++对接相关功能
 */
@RestController
@RequestMapping(value = "/face")
public class InterfaceController {

    @Autowired
    IFaceSearchTaskService faceSearchTaskService;

    @Autowired
    IFaceSearchResultSrv faceSearchResultSrv;

    @Autowired
    IBigPersonPhotoDao bigPersonPhotoDao;

    @Autowired
    IBigPersonDao bigPersonDao;

    /**
     * FLEX前端POST现场图片,返回对应人员信息
     * 【最像科学家只有图片和人员只为 1：1关系】
     *
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/compareTask", method = RequestMethod.POST)
    public OperMessageCompareRet addFaceCompareTask(HttpServletRequest request) throws IOException {

        InputStream in = request.getInputStream();
        int formLength = request.getContentLength();

        //执行对比任务,获取SESSIONID
        int sessionid = faceSearchTaskService.compareFace(in, formLength);

        List<Map> retList;

        //获取对应最像的对比结果\
        if (sessionid != -1) {
            retList = faceSearchResultSrv.getPhotoIdsBySessionId(sessionid);
            //System.out.println("retList:"+retList.toString());
            if (retList.isEmpty()) {
                System.out.println("没有找到 sessionid 为：" + sessionid + "对应的科学家列表");
            }else{
                //获取photoid, 找到对应的科学家信息
                int photoId = (int)retList.get(0).get("photoId");
                int personId = bigPersonPhotoDao.getPersonIdByPhotoId(photoId);
                BigPerson person = bigPersonDao.queryPerson(personId);
                OperMessageCompareRet retMsg = new OperMessageCompareRet("SUCCESS", "INVOKE HTTP INTERFACE SUCCESS.", person);
                retMsg.setMaxScore((Double) retList.get(0).get("score"));
                return retMsg;
            }
        } else {
            return new OperMessageCompareRet("FAILED", "COMPARE SESSIONID is -1.", null);
        }

        return new OperMessageCompareRet("FAILED", "INVOKE HTTP INTERFACE FAILED.", null);
    }

}
