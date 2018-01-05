package entity;

import java.util.Date;
import java.util.List;

public class Model {
    private Integer modelId;
    private String modelName;
    private Date created;
    private List<FileInfo> fileInfoList;

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<FileInfo> getFileInfoList() {
        return fileInfoList;
    }

    public void setFileInfoList(List<FileInfo> fileInfoList) {
        this.fileInfoList = fileInfoList;
    }

    public Model() {
    }

    public Model(Integer modelId, String modelName, Date created, List<FileInfo> fileInfoList) {
        this.modelId = modelId;
        this.modelName = modelName;
        this.created = created;
        this.fileInfoList = fileInfoList;
    }

    @Override
    public String toString() {
        return "Model{" +
                "modelId=" + modelId +
                ", modelName='" + modelName + '\'' +
                ", created=" + created +
                ", fileInfoList=" + fileInfoList +
                '}';
    }
}
