package com.bigvideo.kxj.controller;

import com.bigvideo.kxj.entity.BigPerson;
import com.bigvideo.kxj.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * Created by xiao on 2016/7/6.
 * 应用功能
 */
@RestController
@RequestMapping(value = "app")
public class AppController {

    @Autowired
    PersonService personService;

    /**
     * 查询所有的科学家信息
     * @param start
     * @param end
     * @return
     */
    @RequestMapping(value = "getAllPerson", method = RequestMethod.GET)
    public List<BigPerson> queryAllPerson(@RequestParam(name = "start", required = false) Integer start,
                                          @RequestParam(name = "end", required = false) Integer end) {

        List list = personService.queryPerson();
        return list;
    }

    /**
     * 查询单个科学家信息, 没有信息或错误，返回NULL
     * @return
     */
    @RequestMapping(value = "getPerson/{id}", method = RequestMethod.GET)
    public BigPerson queryPerson(@PathVariable("id") int id){
        BigPerson person =  personService.queryPerson(id);
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

    /**************DEMO****************/
    /*
    @RequestMapping(value = "/demoFileUpload3", method = RequestMethod.POST)
    public Msg DemoUpdate(@RequestParam("name") String name, @RequestParam(value = "file", required = false) MultipartFile file) {


        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(name + "-uploaded")));
                stream.write(bytes);
                stream.close();
                return new Msg(1, "Upload Success! name is:" + name + ", file size:"+file.getSize());
            } catch (Exception e) {
                return new Msg(0, "Upload fail!" + e.getMessage());
            }
        } else {
            return new Msg(0, "Upload File is empty! name is:" + name);
        }
    }

    @RequestMapping(value = "/demoFileUpload2", method = RequestMethod.POST)
    public Msg DemoUpdate(@RequestParam("name") String name) {

        System.out.println("Upload File is empty! name is:" + name);
        return new Msg(1, "Upload Success! name is:" + name );
    }
*/


}
