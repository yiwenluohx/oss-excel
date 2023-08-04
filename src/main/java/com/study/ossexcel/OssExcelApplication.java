package com.study.ossexcel;

import com.alibaba.fastjson.JSON;
import org.apache.commons.compress.utils.Lists;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * oss
 * @author luohx
 */
@SpringBootApplication
public class OssExcelApplication {

    public static void main(String[] args) {

        long l = TimeUnit.DAYS.toMillis(1L);
        System.out.println("一天毫秒数："+ l);

        List<String> testArr = Lists.newArrayList();
        testArr.add("a");
        testArr.add("b");
        testArr.add("c");
        testArr.add("d");
        testArr.add("e");

        testArr.removeIf(k-> k.equals("b"));

        System.out.println(JSON.toJSONString(testArr));



        long timestamp = System.currentTimeMillis();
        String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        char[] chars = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz" +
                "1234567890!@#$%^&*()_+").toCharArray();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 6; i++){
            //Random().nextInt()返回值为[0,n)
            char aChar = chars[new Random().nextInt(chars.length)];
            sb.append(aChar);
        }
        String salt = sb.toString();

        SpringApplication.run(OssExcelApplication.class, args);
    }

}
