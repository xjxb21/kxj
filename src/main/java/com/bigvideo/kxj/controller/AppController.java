package com.bigvideo.kxj.controller;

import com.bigvideo.kxj.entity.BigPerson;
import com.bigvideo.kxj.service.PersonService;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    PersonService personService;

    /**
     * 查询所有的科学家信息，如果缺少任何一个参数，则搜索所有
     *
     * @param pageSize 每页显示多少条
     * @param pageNum  第几页
     * @return
     */
    @RequestMapping(value = "getAllPerson", method = RequestMethod.GET)
    public List<BigPerson> queryAllPerson(@RequestParam(name = "pageSize", required = false) Integer pageSize,
                                          @RequestParam(name = "pageNum", required = false) Integer pageNum) {
        //跨域访问
        //httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        return personService.queryPerson(pageNum, pageSize);
    }

    /**
     * 查询单个科学家信息, 没有信息或错误，返回NULL
     *
     * @return
     */
    @RequestMapping(value = "getPerson/{id}", method = RequestMethod.GET)
    public BigPerson queryPerson(@PathVariable("id") int id) {
        BigPerson person = personService.queryPerson(id);
        return person;
    }

    /**
     * 更新单独的科学家信息(不包含图片?)
     *
     * @return
     */
    @RequestMapping(value = "updatePerson", method = RequestMethod.POST)
    public Msg updateSingle() {
        //personService.updatePerson();
        return null;
    }

    /**
     * 获取科学家图片
     *
     * @param id       人员ID
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "getPic/{id}", method = RequestMethod.GET)
    public void getPic(@PathVariable("id") int id, HttpServletResponse response) throws IOException {
        System.out.printf(this.toString());
        File tarFile = personService.getPersonPic(id);

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

    /*public BigPerson getBigperson(@ModelAttribute(value = "personId") Integer personId){
        if (personId != null) {
            System.out.println("modeAttribute bigperson..."+personId);
        }
        return new BigPerson(2, "xiao", "xiao 's hisgory!!");
    }*/


    /**
     * 处理jqgrid的数据操作
     * @param oper
     * @param bigPerson
     * @return
     */
    @RequestMapping(value = "doPerson")
    public OperMessage addPerson(@RequestParam(name = "oper") String oper,
                          BigPerson bigPerson, @RequestParam(name = "id", required = false) Integer delId) {
        System.out.println("oper is:" + oper);
        System.out.println(bigPerson.toString());

        switch (oper) {
            case "add":
                BigPerson addPerson = new BigPerson(null, bigPerson.getName(), bigPerson.getHistory());
                int pid = personService.addPerson(addPerson);
                return new OperMessage("SUCCESS", "SAVE PERSON INFO SUCCESS", pid);
            case "edit":
                if (bigPerson.getPersonId() != null) {
                    personService.updatePerson(bigPerson);
                }else{
                   return new OperMessage("FAILED", "EDIT PERSON INFO SUCCESS", bigPerson.getPersonId());
                }
            case "del":
                personService.delPerson(new BigPerson(delId, null, null));
        }

        return null;
    }

    /**
     * Post HTML FILE 类型 ，插入图片
     * @param pid   对应人员的的personId
     * @param file  人员照片
     * @return
     */
    @RequestMapping(value = "addPersonPic", method = RequestMethod.POST)
    public OperMessage addPersonPic(@RequestParam(name = "pid") int pid,
                             @RequestParam(value = "imgUpload") MultipartFile file){
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
}
