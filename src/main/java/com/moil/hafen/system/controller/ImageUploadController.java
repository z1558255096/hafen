package com.moil.hafen.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.factory.CloudStorageConfig;
import com.moil.hafen.common.factory.OssFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.TextUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * 文件上传
 *
 * @author song
 */
@Slf4j
@RestController
@RequestMapping("/fileupload")
@Api("文件上传")
public class ImageUploadController {
    @Resource
    private CloudStorageConfig config;

    @PostMapping
    @ApiOperation("文件上传")
    public Result<JSONObject> doulefileupload(MultipartFile multipartFile) throws Exception {
        JSONObject json = new JSONObject();
        //上传文件
        String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        String url = OssFactory.build(config).uploadSuffix(multipartFile.getBytes(), suffix);
        json.put("url", url);
        json.put("type", getFileType(multipartFile.getOriginalFilename()));
        return Result.OK(json);
    }

    @RequestMapping("simditor")
    public String simditorFileupload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        if (CollectionUtils.isEmpty(fileMap)) {
            JSONObject json = new JSONObject();
            json.put("url", "");
            return json.toJSONString();
        }
        MultipartFile multipartFile = null;
        String fileName = null;
        for (Map.Entry<String, MultipartFile> set : fileMap.entrySet()) {
            multipartFile = set.getValue();// 文件名
            System.out.println(multipartFile);
        }
        JSONObject json = new JSONObject();
        if (multipartFile.isEmpty()) {
            json.put("url", "");
            return json.toJSONString();
        }
        //上传文件
        try {
            ;
            String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
            String url = OssFactory.build(config).uploadSuffix(multipartFile.getBytes(), suffix);
//			files.setFileUrl(OssFactory.build().uploadSuffix(file.getBytes(), suffix));
//			filesService.saveFiles(files, file.getInputStream());
            json.put("file_path", url);
            json.put("type", getFileType(multipartFile.getOriginalFilename()));
            json.put("success", true);
//			return json.toJSONString();
            out.print(json.toJSONString()); //返回msg信息
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            json.put("url", "");
            return json.toJSONString();
        }
    }

    /**
     * Get the Mime Type from a File
     */
    private final static String PREFIX_VIDEO = "video/";
    private final static String PREFIX_img = "image/";

    private static String getMimeType(String fileName) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String type = fileNameMap.getContentTypeFor(fileName);
        return type;
    }

    /**
     * 根据文件后缀名判断 文件是否是视频文件
     *
     * @param fileName 文件名
     *
     * @return 是否是视频文件
     */
    public static String getFileType(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return "unKnow";
        }
        String mimeType = getMimeType(fileName);
        if (mimeType == null) {
            if (fileName.toUpperCase().endsWith(".MPEG-4")) {
                mimeType = "video/mpeg";
            } else if (fileName.toUpperCase().endsWith(".MP4")) {
                mimeType = "video/mpeg";
            } else {
                mimeType = "";
            }
        }

        if (mimeType.contains(PREFIX_VIDEO)) {
            return "video";
        } else if (mimeType.contains(PREFIX_img)) {
            return "image";
        }

        return "unKnow";
    }


    @PostMapping("import")
    public String importExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            if (CollectionUtils.isEmpty(fileMap)) {
                JSONObject json = new JSONObject();
                json.put("success", false);
                json.put("message", "文件为空");
                json.put("code", 500);
                return json.toJSONString();
            }
            MultipartFile multipartFile = null;
            String fileName = null;
            for (Map.Entry<String, MultipartFile> set : fileMap.entrySet()) {
                multipartFile = set.getValue();// 文件名
                System.out.println(multipartFile);
            }
            List<String> successList = Lists.newArrayList();
            readExcel(multipartFile, successList);
            JSONObject json = new JSONObject();
            json.put("success", true);
            json.put("message", "");
            json.put("code", 200);
            json.put("data", successList);
            return json.toJSONString();
        } catch (Exception e) {
            String message = "导入卡密失败失败";
            log.error(message, e);
            JSONObject json = new JSONObject();
            json.put("success", false);
            json.put("message", "导入卡密失败失败");
            json.put("code", 500);
            return json.toJSONString();
        }

    }

    public static void readExcel(MultipartFile file, List<String> list) throws Exception {
        Workbook hssfWorkbook = null;
        if (file.getOriginalFilename().endsWith("xlsx")) {
            hssfWorkbook = new XSSFWorkbook(file.getInputStream());//Excel 2007
        } else if (file.getOriginalFilename().endsWith("xls")) {
            hssfWorkbook = new HSSFWorkbook(file.getInputStream());//Excel 2003
        }
        // 循环工作表Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            //HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // 循环行Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                //HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                Row hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    Cell name = hssfRow.getCell(0);
                    name.setCellType(CellType.STRING);
                    list.add(name.getStringCellValue());
                }
            }
        }
    }
}