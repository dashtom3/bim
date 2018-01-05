package Service;

import common.DataWrapper;
import entity.FileInfo;
import entity.FileProperty;
import entity.Model;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface GzipService {
    DataWrapper<FileInfo> upload(HttpServletRequest request, HttpServletResponse response, Integer part);

    void download(String fileName,HttpServletRequest request, HttpServletResponse response);

    DataWrapper<List<FileInfo>> list(Integer currentPage,Integer numberPerPage);
}
