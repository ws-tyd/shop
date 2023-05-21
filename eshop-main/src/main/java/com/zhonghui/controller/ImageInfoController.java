package com.zhonghui.controller;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.zhonghui.common.core.exception.BadRequestException;
import com.zhonghui.common.core.result.FwResult;
import com.zhonghui.common.core.utils.PageUtil;
import com.zhonghui.model.ImageInfo;
import com.zhonghui.resp.RespImage;
import com.zhonghui.service.ImageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

@RestController
public class ImageInfoController {

    @Autowired
    private ImageInfoService imageInfoService;

    @Value("${file.path}")
    private String filePath;

//    @PutMapping("/image/upload")
//    @PreAuthorize("hasAuthority('user') or hasAuthority('staff')")
//    public FwResult uploadImage(@RequestBody Map<String, String> img,HttpServletResponse response) throws IOException {
//        String imgData = img.get("data");
//        byte[] imageData;
//        try {
//             imageData= Base64.getDecoder().decode(imgData);
//        }catch (Exception e){
//            throw new BadRequestException(422,"编码格式错误");
//        }
//        String filename = UUID.randomUUID().toString()+".png";
////        String filename = file.getOriginalFilename();
//        File image = new File("image");
//        if (!image.exists()||!image.isDirectory()){
//            image.mkdir();
//        }
//        File target = new File(image.getPath(), filename);
//        if (target.exists()){
//            throw new BadRequestException(200,"文件名重复");
//        }else {
////            InputStream is = file.getInputStream();
//            ByteInputStream is = new ByteInputStream(imageData,imageData.length);
//            FileOutputStream os = new FileOutputStream(target);
//            int len;
//            byte[] buf = new byte[1024];
//            while ((len=is.read(buf))!=-1){
//                os.write(buf,0,len);
//            }
//            is.close();
//            os.close();
//            ImageInfo imageInfo = imageInfoService.uploadImage(filename, "/api/"+target.getPath().replace("\\", "/"));
//            response.setStatus(201);
//            return FwResult.ok(new RespImage(imageInfo),201);
//        }
//    }

    @PutMapping("/image/upload")
    @PreAuthorize("hasAuthority('user') or hasAuthority('staff')")
    public FwResult uploadImage(MultipartFile file,HttpServletResponse response) throws IOException {
        String filename = UUID.randomUUID()+".png";
//        String filename = file.getOriginalFilename();
        File image = new File("image");
        if (!image.exists()||!image.isDirectory()){
            image.mkdir();
        }
        File target = new File(image.getPath(), filename);
        if (target.exists()){
            throw new BadRequestException(200,"文件名重复");
        }else {
            InputStream is = file.getInputStream();
            FileOutputStream os = new FileOutputStream(target);
            int len;
            byte[] buf = new byte[1024];
            while ((len=is.read(buf))!=-1){
                os.write(buf,0,len);
            }
            is.close();
            os.close();
            ImageInfo imageInfo = imageInfoService.uploadImage(filename, "/api/"+target.getPath().replace("\\", "/"));
            response.setStatus(201);
            return FwResult.ok(new RespImage(imageInfo),201);
        }
    }
    @PostMapping("/image")
    public FwResult queryImagePage(@Valid @RequestBody PageUtil<ImageInfo> pageUtil)  {
        return  imageInfoService.queryAllImage(pageUtil);
    }

    @GetMapping("/image")
    public FwResult queryImage() {
        return  imageInfoService.queryImage();
    }
    @DeleteMapping("/image/{id}")
    public FwResult deleteImage(@PathVariable("id") Integer id) throws IOException {
        return  imageInfoService.deleteImage(id);
    }

    @GetMapping("/image/{url}")
    public void GetImage(@PathVariable("url") String url, HttpServletResponse response) throws IOException {
        File image = new File("image");
        if (!image.exists()||!image.isDirectory()){
            image.mkdir();
            throw new BadRequestException(404,"没有找到图片");
        }else {
            File file = new File(image.getPath(), url);
            if (!file.exists()){
                throw new BadRequestException(404,"没有找到图片");
            }
            response.reset();
            ServletOutputStream os = response.getOutputStream();
            FileInputStream is = new FileInputStream(file);
            int len;
            byte[] buf = new byte[1024];
            while ((len=is.read(buf))!=-1){
                os.write(buf,0,len);
            }
            is.close();
            os.close();
        }
    }

    @GetMapping("/file/pic/{url}")
    public void GetImagePic(@PathVariable("url") String url, HttpServletResponse response) throws IOException {
//        File pic = new File("pic");
//        if (!pic.exists()||!pic.isDirectory()){
//            pic.mkdir();
//            throw new BadRequestException(404,"没有找到图片");
//        }else {
            File file = new File(filePath, url);
            if (!file.exists()){
                throw new BadRequestException(404,"没有找到图片");
            }
            response.reset();
            ServletOutputStream os = response.getOutputStream();
            FileInputStream is = new FileInputStream(file);
            int len;
            byte[] buf = new byte[1024];
            while ((len=is.read(buf))!=-1){
                os.write(buf,0,len);
            }
            is.close();
            os.close();
//        }
    }
}
