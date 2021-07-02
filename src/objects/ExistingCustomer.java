/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.time.LocalDateTime;

/**
 *
 * @author Morgy
 */
public class ExistingCustomer extends Customer{
    
    String status;
    LocalDateTime customerSince;
    
    
    
    public ExistingCustomer(String custName, String custAddress, String custPhone, int custId, int addressId, String zip, String city, int cityId){
        
        super(custName, custAddress, custPhone, custId, addressId, zip, city, cityId);
        setCustomerSince(LocalDateTime.now());
        setStatus("Existing");
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCustomerSince() {
        return customerSince;
    }

    public void setCustomerSince(LocalDateTime customerSince) {
        this.customerSince = customerSince;
    }
    
}
