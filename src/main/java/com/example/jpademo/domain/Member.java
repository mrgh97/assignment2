package com.example.jpademo.domain;

import org.springframework.context.annotation.Primary;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
public class Member implements Serializable {

    private static final Long serialVersionUID = 1L;
    @Id
    @Size(min = 6,message = "Username must longer than 6!")
    @NotEmpty(message = "Username is required")
    private String userName;
    @NotEmpty(message = "Password is required.")
    private String password;

    @Size(min = 11, max = 11, message = "Mobile no. must be 11 digits.")
    @NotEmpty(message = "Mobile no. is required.")
    private String mobileNumber;
    @NotEmpty(message = "Address no. is required.")
    private String address;

    @Override
    public String toString() {
        return "Member{" +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
