package com.blz.domain;

import java.io.Serializable;
import java.util.Date;

public class Guestbook implements Serializable {
    private String id;
    private String name;
    private String content;
    private Date date;
    private Integer checked;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "Guestbook{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }
}

