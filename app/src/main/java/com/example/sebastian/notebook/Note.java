package com.example.sebastian.notebook;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Sebastian on 2017-02-09.
 */
public class Note extends RealmObject {
    @PrimaryKey
    private int id;
    private String head;
    private String content;
    private Long dataCreated;

    public Note(){}

public Note(String head, String content){
    this.head = head;
    this.content = content;
}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public Long getDataCreated() {
        return dataCreated;
    }

    public void setDataCreated(Long dataCreated) {
        this.dataCreated = dataCreated;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent(){
        return content;
    }
}
