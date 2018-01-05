package entity;

import java.util.List;

public class FileInfo {
    private Integer fileId;
    private String fileName;
    private String fileFormat;
    private String fileLength;
    private Integer filePart;
    private Integer type;//文件类型,此处可能是材质或者模型
    private List<FileProperty> filePropertyList;

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public String getFileLength() {
        return fileLength;
    }

    public void setFileLength(String fileLength) {
        this.fileLength = fileLength;
    }

    public Integer getFilePart() {
        return filePart;
    }

    public void setFilePart(Integer filePart) {
        this.filePart = filePart;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<FileProperty> getFilePropertyList() {
        return filePropertyList;
    }

    public void setFilePropertyList(List<FileProperty> filePropertyList) {
        this.filePropertyList = filePropertyList;
    }

    public FileInfo() {
    }

    public FileInfo(Integer fileId, String fileName, String fileFormat, String fileLength, Integer filePart, Integer type, List<FileProperty> filePropertyList) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileFormat = fileFormat;
        this.fileLength = fileLength;
        this.filePart = filePart;
        this.type = type;
        this.filePropertyList = filePropertyList;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "fileId=" + fileId +
                ", fileName='" + fileName + '\'' +
                ", fileFormat='" + fileFormat + '\'' +
                ", fileLength='" + fileLength + '\'' +
                ", filePart=" + filePart +
                ", type=" + type +
                ", filePropertyList=" + filePropertyList +
                '}';
    }
}
