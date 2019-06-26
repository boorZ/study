package com.zl.study.traversal.demo.bean;

import common.model.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pre_plugin_mini_joke_1")
public class PrePluginMiniJoke1Entity extends BaseEntity{

    @Column(name = "diynum")
    private Integer diynum;

    @Column(name = "topid")
    private Integer topid;

    @Column(name = "cid")
    private Integer cid;

    @Column(name = "uid")
    private Integer uid;

    @Column(name = "author")
    private String author;

    @Column(name = "title")
    private String title;

    @Column(name = "sd1")
    private String sd1;

    @Column(name = "sd2")
    private String sd2;

    @Column(name = "sd3")
    private String sd3;

    @Column(name = "sd4")
    private String sd4;

    @Column(name = "sd5")
    private String sd5;

    @Column(name = "info")
    private String info;

    @Column(name = "voter")
    private Integer voter;

    @Column(name = "total")
    private Integer total;

    @Column(name = "display")
    private Integer display;

    @Column(name = "top")
    private Integer top;

    @Column(name = "dateline")
    private Integer dateline;

    public Integer getDiynum() {
        return diynum;
    }

    public void setDiynum(Integer diynum) {
        this.diynum = diynum;
    }

    public Integer getTopid() {
        return topid;
    }

    public void setTopid(Integer topid) {
        this.topid = topid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSd1() {
        return sd1;
    }

    public void setSd1(String sd1) {
        this.sd1 = sd1;
    }

    public String getSd2() {
        return sd2;
    }

    public void setSd2(String sd2) {
        this.sd2 = sd2;
    }

    public String getSd3() {
        return sd3;
    }

    public void setSd3(String sd3) {
        this.sd3 = sd3;
    }

    public String getSd4() {
        return sd4;
    }

    public void setSd4(String sd4) {
        this.sd4 = sd4;
    }

    public String getSd5() {
        return sd5;
    }

    public void setSd5(String sd5) {
        this.sd5 = sd5;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getVoter() {
        return voter;
    }

    public void setVoter(Integer voter) {
        this.voter = voter;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public Integer getDateline() {
        return dateline;
    }

    public void setDateline(Integer dateline) {
        this.dateline = dateline;
    }

    public PrePluginMiniJoke1Entity() {
    }

    public PrePluginMiniJoke1Entity(Integer diynum,Integer topid,Integer cid,Integer uid,String author,String title,String sd1,String sd2,String sd3,String sd4,String sd5,String info,Integer voter,Integer total,Integer display,Integer top,Integer dateline ) {
            this.diynum = diynum;
            this.topid = topid;
            this.cid = cid;
            this.uid = uid;
            this.author = author;
            this.title = title;
            this.sd1 = sd1;
            this.sd2 = sd2;
            this.sd3 = sd3;
            this.sd4 = sd4;
            this.sd5 = sd5;
            this.info = info;
            this.voter = voter;
            this.total = total;
            this.display = display;
            this.top = top;
            this.dateline = dateline;
    }

    @Override
    public String toString() {
        return "PrePluginMiniJoke1Entity{" +
                "diynum=" + diynum +
                ", topid=" + topid +
                ", cid=" + cid +
                ", uid=" + uid +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", sd1='" + sd1 + '\'' +
                ", sd2='" + sd2 + '\'' +
                ", sd3='" + sd3 + '\'' +
                ", sd4='" + sd4 + '\'' +
                ", sd5='" + sd5 + '\'' +
                ", info='" + info + '\'' +
                ", voter=" + voter +
                ", total=" + total +
                ", display=" + display +
                ", top=" + top +
                ", dateline=" + dateline +
                '}';
    }
}