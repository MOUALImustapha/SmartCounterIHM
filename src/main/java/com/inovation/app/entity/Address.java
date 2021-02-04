package com.inovation.app.entity;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Embeddable
public class Address {
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;
    
    @Override
   public String toString() {
    	return String.format("Boulvard %s , ville de %s , region %s, Maroc. Zip code : %s", streetAddress,city,state,zipCode);
    }
}
