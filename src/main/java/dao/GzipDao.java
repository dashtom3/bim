package dao;

import common.Page;
import entity.FileInfo;
import entity.FileProperty;
import entity.Model;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GzipDao {





    int getFileInfoTotalNumber();

    List<FileInfo> getFileInfoList(Page page);

    void addFileInfo(FileInfo fileInfo);


    void addFileProperty(@Param("filePropertyList")List<FileProperty> filePropertyList,@Param("autoId") int autoId);
}
