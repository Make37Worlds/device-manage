package com.dlut.efmsystem.controller.util;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/uploadimg")
public class uploadImg {

    @Value("${file.uploadPath}")
    String uploadPath;  //项目地址
    @Value("${file.accessPath}")
    String accessPath;   //虚拟地址

    @PostMapping
    public String storeImage(HttpServletRequest request, @RequestParam("imgFile") MultipartFile file){
        //获取文件读取路径前缀
        String imageUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        //返回结果类
        JSONObject res = new JSONObject();
        //图片为空
        if (file == null) {
            try{
                res.put("errno",1);
                res.put(",message","文件为空，上传失败");
            }catch (JSONException e){
                e.printStackTrace();
            }
            return res.toString();
        }

        //图片真实路径
        String fileRealPath = file.getOriginalFilename();
        //获取文件后缀
        String suffix = fileRealPath.substring(fileRealPath.lastIndexOf("."));
        //创建新文件名
        String fileName = System.currentTimeMillis()+suffix;
        String path = uploadPath;
        File targetFile = new File(path, fileName);
        if(!targetFile.exists()){
            try {
                targetFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //把服务器路径和图片路径拼接起来
        String filePath =imageUrl+accessPath+fileName;
        JSONObject data=new JSONObject();
        try{
            data.put("url",filePath);
            data.put("alt",fileRealPath);
            res.put("errno",0);
            res.put("data",data);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return res.toString();
    }

}
