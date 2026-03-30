package com.authentication.connexion.services.impl;

import com.authentication.connexion.dto.UserDto;
import com.authentication.connexion.exception.UserNotFoundException;
import com.authentication.connexion.repository.IUserRepository;
import com.authentication.connexion.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private IUserRepository repository;

    @Autowired
    public UserServiceImpl(IUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDto save(UserDto dto) {
        return null;
    }

    @Override
    public UserDto findById(Integer id) {
        return null;
    }

    @Override
    public List<UserDto> findAll() {
        return List.of();
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public UserDto findByEmail(String email) {
        return repository.findByEmail(email)
                .map(UserDto::fromEntity)
                .orElseThrow(() -> new UserNotFoundException(
                        "Aucun utilisateur avec l'email = " + email + " n'a ete trouve dans la BDD", "connexion"));
    }
}
