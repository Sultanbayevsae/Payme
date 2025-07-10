package org.example.payme.service;

import org.example.payme.dao.UserDao;
import org.example.payme.dto.LoginDTO;
import org.example.payme.dto.RegisterDTO;
import org.example.payme.dto.UserResponseDTO;
import org.example.payme.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public String registerUser(RegisterDTO registerDTO) {
        if (userDao.existsByPhone(registerDTO.getPhoneNumber())) {
            return "Phone number already in use";
        }
        User user = new User();
        User.builder()
                .fullName(registerDTO.getFullName())
                .password(registerDTO.getPassword())
                .phoneNumber(registerDTO.getPhoneNumber())
                .build();
        userDao.save(user);
        return "User successfully registered";
    }

    public UserResponseDTO loginUser(LoginDTO loginDTO) {
        User user = userDao.findByPhone(loginDTO.getPhoneNumber());
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        if (!user.getPassword().equals(loginDTO.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        return new UserResponseDTO(user.getId(), user.getFullName(), user.getPhoneNumber());
    }


}
