package cn.haiwai.newsnotification.web.vo;

import cn.haiwai.newsnotification.service.ContentBO;
import cn.haiwai.newsnotification.service.TagBO;

import java.util.List;

public class IndexContentVo {
    private List<ContentBO> contents;
    private List<TagBO> tags;
    private String keyDate;

    public List<ContentBO> getContents() {
        return contents;
    }

    public void setContents(List<ContentBO> contents) {
        this.contents = contents;
    }

    public List<TagBO> getTags() {
        return tags;
    }

    public void setTags(List<TagBO> tags) {
        this.tags = tags;
    }

    public String getKeyDate() {
        return keyDate;
    }

    public void setKeyDate(String keyDate) {
        this.keyDate = keyDate;
    }
}
