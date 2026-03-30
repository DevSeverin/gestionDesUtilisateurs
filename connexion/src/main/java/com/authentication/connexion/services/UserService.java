package com.authentication.connexion.services;

import com.authentication.connexion.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto save(UserDto dto);

    UserDto findById(Integer id);

    List<UserDto> findAll();

    void delete(Integer id);

    UserDto findByEmail(String email);

}
