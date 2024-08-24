package com.himluck.trading_app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.himluck.trading_app.domain.UserRoles;
import jakarta.persistence.*;
import lombok.Data;

@Entity     //map the User to user table
@Data       //provides getters and setters
public class User {
    @Id     //annotation used for annotating primary key in the table - user
    @GeneratedValue(strategy = GenerationType.AUTO)     // the value for id is generated automatically by the dbms
    private long id;

    private String fullname;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)      //Access setting that means that the property may only be written (set) for deserialization, but will not be read (get) on serialization, that is, the value of the property is not included in serialization.
    private String password;

    @Embedded       //this will embed this entity into user entity
    private TwoFactorAuth auth = new TwoFactorAuth();

    private UserRoles role = UserRoles.USER;


}
