/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Morgynn
 */
public class TypeDisplay {
    
    private String type;
    private int noOfType;
    private ObservableList<TypeDisplay> typeDisplayList = FXCollections.observableArrayList();
    
    public TypeDisplay(String type, int noOfType){
        setType(type);
        setNoOfType(noOfType);
    }

    public void setType(String type){
        this.type = type;
    }
    
    public void setNoOfType(int noOfType){
        this.noOfType = noOfType;
    }
    
    public String getType(){
        return this.type;
    }
    
    public int getNoOfType(){
        return this.noOfType;
    }
    
    public void addTypeDisplay(TypeDisplay td){
        typeDisplayList.add(td);
    }
    
    public ObservableList<TypeDisplay> getAllTypeDisplay(){
        return typeDisplayList;
        
    }
    
}
