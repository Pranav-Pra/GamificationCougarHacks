package com.example.gamification;

public class Code {
    String code;
    String bossUid;

    public Code(String code, String bossUid) {
        this.code = code;
        this.bossUid = bossUid;
    }
    public Code(){

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBossUid() {
        return bossUid;
    }

    public void setBossUid(String bossUid) {
        this.bossUid = bossUid;
    }
    public boolean equals(Code current, Code other){
        return(current.getCode() == other.getCode());
    }
}
