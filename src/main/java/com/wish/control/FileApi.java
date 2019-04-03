package com.wish.control;


import com.wish.Config;
import com.wish.bean.UserBean;
import com.wish.common.util.FileDownload;
import com.wish.common.util.FileUploadUtil;
import com.wish.common.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

@Controller
@RequestMapping("/file")
public class FileApi {

    @RequestMapping("/tt")
    public Object login(){
        System.out.println("xxxxx");
        return "login";
    }

    @RequestMapping("/uploadImg")
    @ResponseBody
    public Object uploadImg(HttpServletRequest request) {
        String msg = StringUtils.EMPTY;
        String imgUrl = StringUtils.EMPTY;

        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile(iter.next());
                if (FileUploadUtil.allowUpload(file.getContentType())) {
                    imgUrl = FileUploadUtil.upload(file, Config.UPLOAD_DIR);
                } else {
                    msg = "文件类型不合法,只能是 jpg、gif、png、jpeg 类型！";
                }
            }
            if (StringUtils.isEmpty(imgUrl) && StringUtils.isEmpty(msg)) {
                msg = "文件上传失败";
            }
        } else {
            msg = "没有要上传的文件";
        }

        if (StringUtils.isEmpty(msg)) {
            return JsonUtil.genSuccess(imgUrl);
        } else {
            return JsonUtil.genError(msg);
        }
    }
    @RequestMapping("/toUpload")
    public String toUpload() {
        return "upload";
    }



    /**
     * 上传一个文件到工程根目录
     * @param file
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public Object upload(@RequestParam("file") CommonsMultipartFile file){
        Long time = System.currentTimeMillis()/1000;
        String fileName = "temp-"+time+"-"+file.getOriginalFilename();
        String path = Config.UPLOAD_DIR+fileName;
        File newFile = new File(path);
        try {
            file.transferTo(newFile);
            return JsonUtil.genSuccess(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JsonUtil.genError("保存文件失败");
    }

    /**
     * 导出文件
     * @param response
     */
    @RequestMapping("/export")
    @ResponseBody
    public void export(HttpServletResponse response, String path) {
        FileDownload.download(response, Config.EXCEL_SAVE_PATH + path);
    }
}
