package entity;

/**
 * 文件的实体类
 */
public class FileProperty {

    private Integer filePropertyId;
    private String fileName;//文件名
    private String filePath;//文件路径
    private Long fileBytes;//文件大小

    public Integer getFilePropertyId() {
        return filePropertyId;
    }

    public void setFilePropertyId(Integer filePropertyId) {
        this.filePropertyId = filePropertyId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(Long fileBytes) {
        this.fileBytes = fileBytes;
    }

    public FileProperty() {
    }

    public FileProperty(Integer filePropertyId, String fileName, String filePath, Long fileBytes) {
        this.filePropertyId = filePropertyId;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileBytes = fileBytes;
    }

    @Override
    public String toString() {
        return "FileProperty{" +
                "filePropertyId=" + filePropertyId +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileBytes=" + fileBytes +
                '}';
    }
}
