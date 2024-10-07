/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javalab1.beans;

import java.util.Date;

/**
 *
 * @author zygis
 */
public class Message implements java.io.Serializable{
    private String name;
    private String msg;
    private Date time;
    
    public Message() {}
    

    public String getName() {
        return name;
    }

    public String getMsg() {
        return msg;
    }

    public Date getTime() {
        return time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setTime(Date time) {
        this.time = time;
    }
    
    
    
    
}