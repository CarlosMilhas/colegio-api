package com.milhas.colegio.service;

import com.milhas.colegio.model.Director;
import com.milhas.colegio.model.User;
import com.milhas.colegio.repository.DirectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class DirectorService {

    private final DirectorRepository directorRepository;
    private final UserService userService;

    @Transactional
    public void createDirector(User user) {

        // La creación y persistencia de credenciales se delega completamente
        User newAccount = userService.createUserAccount(user);

        Director director = new Director();
        director.setUser(newAccount);

        directorRepository.save(director);
    }
}

