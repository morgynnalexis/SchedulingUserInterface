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
public class Customer {
    
    private String custName, custAddress, custPhone, zip, city;
    private int custId, addressId, cityId;
    
    public Customer(String custName, String custAddress, String custPhone, int custId, int addressId, String zip, String city, int cityId){
        setCustName(custName);
        setCustAddress(custAddress);
        setCustPhone(custPhone);
        setCustId(custId);
        setAddressId(addressId);
        setZip(zip);
        setCity(city);
        setCityId(cityId);
    }
    
    public void setCustName(String custName){
        this.custName = custName;
    }
    
    public void setCustAddress(String custAddress){
        this.custAddress = custAddress;
    }
    
    public void setCustPhone(String custPhone){
        this.custPhone = custPhone;
    }
    
    public void setCustId(int custId){
        this.custId = custId;
    }
    
    public void setAddressId(int addressId){
        this.addressId = addressId;
    }
    
    public void setZip(String zip){
        this.zip = zip;
    }
    
    public void setCity(String city){
        this.city = city;
    }
    
    public void setCityId(int cityId){    
        this.cityId = cityId;
    }
    
    public String getCustName(){
        return this.custName;
    }
    
    public String getCustAddress(){
        return this.custAddress;
    }
    
    public String getCustPhone(){
        return this.custPhone;
    }
    
    public int getCustId(){
        return this.custId;
    }
    
    public int getAdressId(){
        return this.addressId;
    }
    
    public String getZip(){
        return this.zip;
    }
    
    public String getCity(){
        return this.city;
    }
    
    public int getCityId(){
        return this.cityId;
    }
    
}
