package org.ecomapp.app.Dto;

import lombok.Data;
import org.ecomapp.app.Entity.Address;

@Data
public class UserResponse {

    private Long id;
    private String firstName;
    private String lastName;

    private String email;
    private String phone;
    private AddressDTO address;

}
