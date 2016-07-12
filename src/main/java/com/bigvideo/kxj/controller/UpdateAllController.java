package com.bigvideo.kxj.controller;

import com.bigvideo.kxj.entity.BigPerson;
import com.bigvideo.kxj.service.MaintainPerson;
import com.bigvideo.kxj.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * Created by xiao on 2016/7/6.
 */

@RestController
public class UpdateAllController {

    @Autowired
    PersonService personService;

    @Autowired
    MaintainPerson maintain;

    /**
     * 更新EXCEL的信息和图片
     *
     * @return
     */
    @RequestMapping(value = "/updateAll")
    public Msg updateAll() {
        try {
            maintain.updateAllPerson();
        } catch (Exception e) {
            e.printStackTrace();
            return new Msg(0, "Update all excel info fail!");
        }
        return new Msg(1, "Update all excel info Success!");
    }

    /**
     * 查询所有的科学家信息
     * @return
     */
    @RequestMapping(value = "/getAllPerson", method = RequestMethod.GET)
    public List<BigPerson> queryAllPerson(){
        List list = personService.queryPerson();
        return list;
    }

    /**
     * 更新单独的科学家信息(不包含图片?)
     *
     * @return
     */
    @RequestMapping(value = "/updatePerson")
    public Msg updateSingle() {
        //personService.updatePerson();
        return null;
    }

    /**
     * 获取科学家图片流
     */
    @RequestMapping(value = "/getPic", method = RequestMethod.GET)
    public void getPic(HttpServletResponse response) throws IOException {

        File tarFile = personService.getPersonPic(1);

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

    /**
     * FLEX前端POST现场图片（待修改为facesearchTask表）
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/demoFileUpload", method = RequestMethod.POST)
    public Msg DemoUpdate(HttpServletRequest request) throws IOException {

        InputStream in = request.getInputStream();
        int formLength = request.getContentLength();

        BigPerson testPerson = new BigPerson();
        testPerson.setPersonId(10);
        testPerson.setName("test");
        testPerson.setHistory("test");
        personService.istPersonPic(testPerson, in, formLength);

        return new Msg(1, "the file formLength："+ formLength);
    }
}
