package controller;


import Service.GzipService;
import common.DataWrapper;
import entity.FileInfo;
import entity.FileProperty;
import entity.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件的上传与下载
 */
@Controller
@RequestMapping("api/gzip")
public class GzipController{

    @Autowired
    private GzipService gzipService;

    /**
     * 上传模型并保存成gzip
     * @param request
     * @param response
     */
    @RequestMapping("upload")
    @ResponseBody
    public DataWrapper<FileInfo> upload(
            @RequestParam(value="part",required = false,defaultValue = "10")Integer part,
            HttpServletRequest request,
            HttpServletResponse response){
       return gzipService.upload(request,response,part);
    }

    /**
     * 下载模型并输出gzip
     */
    @RequestMapping("download")
    @ResponseBody
    public void download(
            @RequestParam(value="fileName",required = false)String fileName,
            HttpServletRequest request,
            HttpServletResponse response){
        gzipService.download(fileName,request,response);
    }

    /**
     * 获取模型列表
     */
    @RequestMapping("list")
    @ResponseBody
    public DataWrapper<List<FileInfo>> list(
            @RequestParam(value="currentPage",required = false,defaultValue = "1") Integer currentPage,
            @RequestParam(value="numberPerPage",required = false,defaultValue = "10")Integer numberPerPage
    ){
        return gzipService.list(currentPage,numberPerPage);
    }


}
