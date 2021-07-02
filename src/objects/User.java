
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

/**
 *
 * @author Morgynn
 */
public class User {
    
    private String username;
    private int userId;
    
    public User(String username, int userId){
        setUsername(username);
        setUserId(userId);
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public void setUserId(int userId){
        this.userId = userId;
    }
    
    public String getUsername(){
        return this.username;
    }
    
    public int getUserId(){
        return this.userId;
    }
    
}
