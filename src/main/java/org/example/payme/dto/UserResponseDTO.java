package org.example.payme.dto;

public class UserResponseDTO {

    private Long id;
    private String fullName;
    private String phoneNumber;

    public UserResponseDTO(Long id, String fullName, String phoneNumber) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }


    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
