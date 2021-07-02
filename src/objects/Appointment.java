/*
 *
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.time.LocalDateTime;

/**
 *
 * @author Morgynn
 */
public class Appointment {
    
    private int custId, apptId, userId;
    private String title, description, location, contact, type, url;
    private LocalDateTime apptEnd, apptStart;
    
    public Appointment(int apptId, int custId, int userId, String title, String description, String location, String contact, String type, String url, LocalDateTime apptStart, LocalDateTime apptEnd){
        setApptId(apptId);
        setCustId(custId);
        setUserId(userId);
        setTitle(title);
        setDescription(description);
        setLocation(location);
        setContact(contact);
        setType(type);
        setURL(url);
        setApptStart(apptStart);
        setApptEnd(apptEnd);
    }
    
    public void setApptId(int apptId){
        this.apptId = apptId;
    }
    
    public void setCustId(int custId){
        this.custId = custId;
    }
    
    public void setUserId(int userId){
        this.userId = userId;
    }
    
    public void setTitle(String title){
        this.title = title;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public void setLocation(String location){
        this.location = location;
    }
    
    public void setContact(String contact){
        this.contact = contact;
    }    
    
    public void setType(String type){
        this.type = type;
    }
    
    public void setURL(String url){
        this.url = url;
    }
    
    public void setApptStart(LocalDateTime apptStart){
        this.apptStart = apptStart;
    }
    
    public void setApptEnd(LocalDateTime apptEnd){
        this.apptEnd = apptEnd;
    }
    
    public int getApptId(){
        return this.apptId;
    }
    
    public int getCustId(){
        return this.custId;
    }
    
    public int getUserId(){
        return this.userId;
    }
    
    public String getTitle(){
        return this.title;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public String getLocation(){
        return this.location;
    }
    
    public String getContact(){
        return this.contact;
    }
    
    public String getType(){
        return this.type;
    }
    
    public String getURL(){
        return this.url;
    }
    
    public LocalDateTime getApptStart(){
        return this.apptStart;
    }
    
    public LocalDateTime getApptEnd(){
        return this.apptEnd;
    }
    
}
