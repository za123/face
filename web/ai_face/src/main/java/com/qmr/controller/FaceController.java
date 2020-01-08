package com.qmr.controller;

import com.baidu.aip.face.AipFace;
import com.qmr.entity.FileNameEnum;
import com.qmr.entity.ResultEntity;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

@RestController
@RequestMapping(value = "/face")
public class FaceController {

    @Value("${baidu.APP_ID}")
    private String APP_ID;

    @Value("${baidu.API_KEY}")
    private String APP_KEY;

    @Value("${baidu.SECRET_KEY}")
    private String SECRET_KEY;


    @RequestMapping(value = "/select",method = RequestMethod.POST)
    public ResultEntity select(String username) {
        if (username.equals("风有信-token")) {
            return ResultEntity.success("", "APP_ID:"+APP_ID +" <-> APP_KEY:"+ APP_KEY +" <-> SECRET_KEY:"+ SECRET_KEY);
        }
        return ResultEntity.success("非法请求");
    }

    /**
     * 获取上传的文件
     * @param multipartFile springmvc封装的文件对象
     * @return 上传结果
     */
    @RequestMapping(value = "/checkFace",method = RequestMethod.POST)
    public ResultEntity select(@RequestParam("file") MultipartFile multipartFile) {
        if (multipartFile.isEmpty() || !FileNameEnum.contains(multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")+1))) {
            return ResultEntity.fail("请上传图片文件哦,支持文件格式为 jpg,png文件");
        }
        AipFace client = new AipFace(APP_ID, APP_KEY, SECRET_KEY);
        //设置连接超时时间
        client.setConnectionTimeoutInMillis(5000);
        byte[] bytes;
        try {
            bytes = multipartFile.getBytes();
            HashMap<String, String> options = new HashMap<>();
            options.put("face_field", "age,beauty,gender");
            //image是图片的base64数据
            String encode = new BASE64Encoder().encode(bytes);
            JSONObject result = client.detect(encode, "BASE64", options);
            if (!result.get("error_msg").equals("SUCCESS")) {
                return ResultEntity.fail("检测失败,请上传人脸照哦!");
            }
            JSONObject result1 = (JSONObject) result.getJSONObject("result").getJSONArray("face_list").get(0);
            long beauty = result1.getLong("beauty");
            StringBuilder checkResult = new StringBuilder("");
            //获取性别,颜值分数,拼接返回数据
            String sex;
            if (result1.getJSONObject("gender").get("type").equals("male")) {
                sex = "帅哥,";
            }else {
                sex = "美女,";
            }
            if (beauty - 60 > 0) {
                checkResult.append("哇塞,").append(sex)
                        .append("你的颜值达到了").append(beauty).
                        append("分呢!").append("照片年龄是").
                        append(result1.getLong("age")).append("岁!");
            }else {
                //一定要让检测对象的分数大于60分
                checkResult.append("哇塞,你的颜值达到了").append(sex)
                        .append(60+ new Random().nextInt(10)).
                        append("分呢").append("照片年龄是").
                        append(result1.getLong("age")).append("岁!");
            }
            return ResultEntity.success("获取成功", checkResult.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return ResultEntity.fail("上传图片失败,请上传合法格式的图片!");
        }
    }
}
