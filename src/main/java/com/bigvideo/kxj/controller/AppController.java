package com.bigvideo.kxj.controller;

import com.bigvideo.kxj.dao.support.PageInfo;
import com.bigvideo.kxj.entity.BigPerson;
import com.bigvideo.kxj.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * Created by xiao on 2016/7/6.
 * WEB应用功能
 */
@RestController
@RequestMapping(value = "app")
public class AppController {

    @Autowired
    IPersonService personService;

    /**
     * 查询所有的科学家信息，如果缺少任何一个参数，则搜索所有
     *
     * @param pageSize 每页显示多少条
     * @param pageNum  第几页
     * @return
     */
    @RequestMapping(value = "getAllPerson", method = RequestMethod.GET)
    public PageInfo queryAllPerson(@RequestParam(name = "pageSize", required = false) Integer pageSize,
                                   @RequestParam(name = "pageNum", required = false) Integer pageNum,
                                   @RequestParam(name = "searchName", required = false) String nameKey) {

        //跨域访问
        //httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        StringBuilder querySql = new StringBuilder("SELECT PERSONID, NAME, HISTORY FROM BIGPERSON");

        if (nameKey != null) {
            querySql.append(" WHERE NAME LIKE '%" + nameKey + "%'");
        }
        System.out.println(querySql.toString());
        return personService.queryPerson(querySql.toString(), pageNum, pageSize);
    }

    /**
     * 查询单个科学家信息, 没有信息或错误，返回NULL
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "getPerson/{id}", method = RequestMethod.GET)
    public BigPerson queryPerson(@PathVariable("id") int id) {
        BigPerson person = personService.queryPerson(id);
        return person;
    }

    /**
     * 获取科学家图片
     *
     * @param photoId       图片photoId
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "getPic/{photoId}", method = RequestMethod.GET)
    public void getPic(@PathVariable("photoId") int photoId, HttpServletResponse response) throws IOException {
        File tarFile = personService.getPersonPicByPhotoId(photoId);

        InputStream in = new FileInputStream(tarFile);
        int i = in.available();
        byte[] content = new byte[i];
        in.read(content);

        response.setContentType("image/jpeg"); // 设置返回内容格式
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(content);
        outputStream.flush();
        in.close();
        outputStream.close();
    }

    /**
     * 根据PERSONID 获取 PHOTOID集合
     * @param personId
     * @return
     */
    @RequestMapping(value = "getPic")
    public List<Integer> getPicListByPersonId(@RequestParam(value = "pid") int personId) {

        List<Integer> list = personService.getPersonPicByPersonId(personId);
        return list;
    }

    /**
     * 处理jqgrid的数据操作
     *
     * @param oper      {add | edit | del}
     * @param bigPerson
     * @return
     */
    @RequestMapping(value = "doPerson")
    public OperMessage addPerson(@RequestParam(name = "oper") String oper,
                                 BigPerson bigPerson) {
        System.out.println("oper is:" + oper);
        System.out.println(bigPerson.toString());

        switch (oper) {
            case "add":
                BigPerson addPerson = new BigPerson(bigPerson.getPersonId(), bigPerson.getName(), bigPerson.getHistory());
                int pid = personService.addPerson(addPerson);
                return new OperMessage("SUCCESS", "SAVE PERSON INFO SUCCESS", pid);
            case "edit":
                if (bigPerson.getPersonId() != null) {
                    //更新单独的科学家信息(不包含图片)
                    personService.updatePerson(bigPerson);
                    return new OperMessage("SUCCESS", "EDIT PERSON INFO SUCCESS", bigPerson.getPersonId());
                } else {
                    return new OperMessage("FAILED", "EDIT PERSON INFO FAILED， PERSON'ID IS NULL", bigPerson.getPersonId());
                }
            case "del":
                personService.delPerson(new BigPerson(bigPerson.getPersonId(), null, null));
                return new OperMessage("SUCCESS", "DELETE PERSON INFO SUCCESS", bigPerson.getPersonId());
        }

        return new OperMessage("FAILED", "NOT SURPORT OPERATION", null);
    }

    /**
     * HTML Post FILE 类型 ，插入图片
     *
     * @param pid  对应人员的的personId
     * @param file 人员照片
     * @return
     */
    @RequestMapping(value = "addPersonPic4File", method = RequestMethod.POST)
    public OperMessage addPersonPic(@RequestParam(name = "pid") int pid,
                                    @RequestParam(value = "imgUpload") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                InputStream inFile = file.getInputStream();
                personService.istPersonPic(new BigPerson(pid, null, null), inFile, new Long(file.getSize()).intValue());
            } catch (IOException e) {
                e.printStackTrace();
                return new OperMessage("FAILED", "SAVE PERSON'S IMG FILE FAILED", pid);
            }
        }
        return new OperMessage("SUCCESS", "SAVE PERSON'S IMG FILE SUCCESS", pid);
    }

    /**
     * 前端 POST 上传图片二进制
     *
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/addPersonPic4Binary", method = RequestMethod.POST)
    public OperMessage addFaceCompareTask(HttpServletRequest request, BigPerson bigPerson) {
        try {
            InputStream in;
            in = request.getInputStream();
            int formLength = request.getContentLength();

            int retId = personService.istPersonPic(bigPerson, in, formLength);
            return new OperMessage("SUCCESS", "INSERT PERSON'S IMG FILE SUCCESS", retId);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new OperMessage("FAILED", "INSERT PERSON'S IMG FILE FAILED:", null);
    }

}
