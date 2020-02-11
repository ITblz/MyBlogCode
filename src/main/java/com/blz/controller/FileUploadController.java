package com.blz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("fileUpload")
public class FileUploadController {

 @RequestMapping(value = "/editorMDImageFileUpload" ,method = RequestMethod.POST)
    public @ResponseBody Map<String,Object>
    editorMDImageFileUpload2(HttpServletRequest request,
                            @RequestParam(value = "editormd-image-file", required = true) MultipartFile upload) {
        Map<String, Object> map = new HashMap<>();

        //使用fileupload组件完成文件上传
        String path = request.getSession().getServletContext().getRealPath("/upload/editdMDImages");
        //判断该路径是否存在
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }

        //文件上传
        String filename = upload.getOriginalFilename();
        //唯一值UUID
        String uuid = UUID.randomUUID().toString().replace("-","");
        String lastFileName = uuid+"_"+filename;
        //完成上传
        try {
            upload.transferTo(new File(path,lastFileName));
            map.put("success",1);
            map.put("message","上传成功");
            map.put("url","/upload/editdMDImages/"+lastFileName);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            map.put("success",0);
            return map;
        }
    }
}
