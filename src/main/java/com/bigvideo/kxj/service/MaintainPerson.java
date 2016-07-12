package com.bigvideo.kxj.service;

import com.bigvideo.kxj.entity.BigPerson;
import com.bigvideo.kxj.utils.ExcelReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by xiao on 2016/7/10.
 * 维护、初始化使用
 */
@Service(value = "maintain")
public class MaintainPerson {

    @Value("${kxj.excelFilePath}")
    String excelFilePath;

    @Value("${kxj.picDirectory}")
    String picDirectory;

    @Autowired
    PersonService personService;

    /**
     * 根据EXCEL表中的科学家信息，更新所有的科学家信息,初始化整体维护使用
     */
    @Transactional
    public void updateAllPerson() throws Exception {
        File excelFile = new File(excelFilePath);
        File picFile;
        if (excelFile.exists()) {
            // 读取excel文件内容到Map集合中
            ExcelReader excelReader = new ExcelReader();
            InputStream is2 = new FileInputStream(excelFilePath);
            Map<Integer, String> PersonInfoMap = excelReader.readExcelContent(is2);

            for (int i = 1; i <= PersonInfoMap.size(); i++) {
                System.out.println(PersonInfoMap.get(i));
                String[] personInfoArr = PersonInfoMap.get(i).split("###");

                BigPerson person = new BigPerson();
                //person.setPersonId(Integer.parseInt(personInfoArr[0]));   //set id
                person.setPersonId(Float.valueOf(personInfoArr[0]).intValue());
                person.setName(personInfoArr[1]);
                person.setHistory(personInfoArr[2]);

                //获取对应的图片
                //picFile = new File(picDirectory + "\\" + person.getPersonId()+".jpg");
                picFile = new File(picDirectory + "\\" + personInfoArr[3]);
                System.out.println("picFile:" + picFile.length());
                personService.addPerson(person);
                InputStream pin = new FileInputStream(picFile);
                personService.istPersonPic(person, pin, (int) picFile.length());
                pin.close();
            }
        } else {
            System.out.println("File not found....");
        }
    }

    /**
     * 暂不实现，供测试使用
     *
     * @param personIds
     */
    public void dellAllPerson(int[] personIds) {
        System.out.println("del all person");
    }
}
