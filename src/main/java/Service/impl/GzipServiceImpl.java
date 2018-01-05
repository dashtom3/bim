package Service.impl;

import Service.GzipService;
import common.DataWrapper;
import common.Page;
import dao.GzipDao;
import entity.FileInfo;
import entity.FileProperty;
import entity.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@Service
public class GzipServiceImpl implements GzipService {

    private static final String FILE_DIRTORY="/usr/local/model/";//文件目录

    private static final String ZONE_FILE_DIRTORY="/usr/local/model/test/";

    @Autowired
    private GzipDao gzipDao;

    @Override
    public DataWrapper<FileInfo> upload(HttpServletRequest request, HttpServletResponse response,Integer part) {
        DataWrapper<FileInfo> dataWrapper=new DataWrapper<FileInfo>();
        //创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求  
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request    
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                //记录上传过程起始时的时间，用来计算上传时间  
                int pre = (int) System.currentTimeMillis();
                //取得上传文件  
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    //取得当前上传文件的文件名称  
                    String myFileName = file.getOriginalFilename();
                    if (myFileName.trim() != "") {
                        System.out.println(myFileName);
                        //obj
                            //重命名上传后的文件名  
                            String fileName = System.currentTimeMillis() +file.getOriginalFilename();
                            //定义上传路径  
                            String path = FILE_DIRTORY+ fileName;
                            byte[] buffer = new byte[1024];
                            try {
//                            GZIPOutputStream gos=new GZIPOutputStream(new FileOutputStream(path+".gz"));
                                    FileOutputStream gos=new FileOutputStream(path);
                                    InputStream is = file.getInputStream();
                                    int length;
                                    while ((length = is.read(buffer)) > 0) {
                                        gos.write(buffer, 0, length);
                                    }
                                    is.close();
                                    gos.close();
                                    System.out.println("File Compressed!!");
                                    //获取文件的大小
//                            File gzipFile=new File(path+".gz");
                                    File gzipFile=new File(path);
                                    long fileLength=gzipFile.length();
                                    //对文件进行分割
                                    FileInfo fileInfo=new FileInfo();
                                    fileInfo.setFileName(myFileName.substring(0,myFileName.indexOf(".")));
                                    fileInfo.setFileFormat(myFileName.substring(myFileName.indexOf(".")+1));
                                    if(fileLength/1048576!=0){
                                        fileInfo.setFileLength(fileLength/1048576+"M");
                                    }else{
                                        fileInfo.setFileLength(fileLength+"Bytes");
                                    }
                                    //分多少部分
                                    int piece=0;
                                if(myFileName.endsWith(".obj")){
                                    piece=part;
                                    fileInfo.setType(1);
                                    //获取自增id
                                }else if(myFileName.endsWith(".mtl")){
                                    piece=1;
                                    fileInfo.setType(2);
                                }
                                fileInfo.setFilePart(piece);
                                List<FileProperty> filePropertyList=splitFile(fileName,fileLength,piece);
                                fileInfo.setFilePropertyList(filePropertyList);
                                gzipDao.addFileInfo(fileInfo);
                                gzipDao.addFileProperty(fileInfo.getFilePropertyList(),fileInfo.getFileId());
                                dataWrapper.setData(fileInfo);
                                dataWrapper.setMsg("上传成功");
                            } catch (Exception e) {
                                e.printStackTrace();
                                dataWrapper.setMsg("上传失败");
                            }
                        //记录上传该文件后的时间  
                        int finaltime = (int) System.currentTimeMillis();
                        System.out.println(finaltime - pre);
                    }
                }
            }
        }
        return dataWrapper;
    }

    @Override
    public void download(String filePath, HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        File file =new File(filePath);
        String name=file.getName();
        System.out.println("name"+name);
        response.setHeader("Content-Disposition","attachment;filename="+name);
        response.setHeader("Content-Type","application/octet-stream");
        ServletOutputStream out=null;
        FileInputStream gos=null;
        try{
            gos=new FileInputStream(file);
            System.out.println(gos+"inputstream");
            out = response.getOutputStream();
            int b = 0;
            byte[] buffer = new byte[1024];
            while ((b = gos.read(buffer)) != -1) {
                out.write(buffer, 0, b);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                gos.close();
                out.flush();
                out.close();
            }catch (Exception e1){
                e1.printStackTrace();
            }finally {
                if(gos!=null){
                    try {
                        gos.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
                if(out!=null){
                    try{
                        out.flush();
                        out.close();
                    }catch (IOException e3){
                        e3.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public DataWrapper<List<FileInfo>> list(Integer currentPage,Integer numberPerPage) {
        DataWrapper<List<FileInfo>> dataWrapper=new DataWrapper<List<FileInfo>>();
        Page page=new Page();
        page.setNumberPerPage(numberPerPage);
        page.setCurrentPage(currentPage);
        int totalNumber=gzipDao.getFileInfoTotalNumber();
        dataWrapper.setPage(page,totalNumber);
        List<FileInfo> fileInfoList=gzipDao.getFileInfoList(page);
        dataWrapper.setData(fileInfoList);
        return dataWrapper;
    }

    /**
     * linux中处理文件切割
     */
    public List<FileProperty> splitFile(String fileName,long fileLength,Integer part)  {
        //获得每一部分的文件的大小
        long zoneLength=0L;
        if(fileLength%part==0){
            zoneLength=fileLength/part;
        }else{
            zoneLength=fileLength/(part-1);
        }
        String[] command=new String[]{"sh","-c","split --verbose -a 2 -d -b "+zoneLength+" "+FILE_DIRTORY+fileName+" "+ZONE_FILE_DIRTORY+fileName+"_"};
        Process process=null;
        Runtime runtime=Runtime.getRuntime();
        try {
            process=runtime.exec(command);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s=null;
            while((s=bufferedReader.readLine()) != null){
                System.out.println(s);
            }
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fillFilePropertyList(part,fileName,fileLength,zoneLength);
    }

    /**
     * 填充所有的数据
     */
    public List<FileProperty> fillFilePropertyList(Integer part,String fileName,long fileLength,long zoneLength){
        List<FileProperty> filePropertyList=new ArrayList<FileProperty>();
        for(int i=0;i<part;i++){
            FileProperty fileProperty=new FileProperty();
            String realFileName="";
            if(i<10){
                realFileName=fileName+"_0"+i;
            }else{
                realFileName=fileName+i;
            }
            if(i==part-1){
                fileProperty.setFileBytes(fileLength-(part-1)*zoneLength);
            }else{
                fileProperty.setFileBytes(zoneLength);
            }
            fileProperty.setFileName(realFileName);
            fileProperty.setFilePath("http://116.62.228.3:8080/file/api/gzip/download?fileName="+ZONE_FILE_DIRTORY+fileProperty.getFileName());
            filePropertyList.add(fileProperty);
        }
        return filePropertyList;
    }
}