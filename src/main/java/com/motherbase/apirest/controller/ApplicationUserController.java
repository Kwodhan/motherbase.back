package com.motherbase.apirest.controller;

import com.motherbase.apirest.model.motherbase.MotherBase;
import com.motherbase.apirest.model.security.ApplicationUser;
import com.motherbase.apirest.repository.security.ApplicationUserRepository;
import com.motherbase.apirest.service.MotherBaseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Security sign-up
 */
@RestController
@RequestMapping("/users")
public class ApplicationUserController {

    static Logger log = Logger.getLogger(ApplicationUserController.class.getName());

    private ApplicationUserRepository applicationUserRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    MotherBaseService motherBaseService;

    public ApplicationUserController(ApplicationUserRepository applicationUserRepository,
                                     BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody ApplicationUser user) {
        if (applicationUserRepository.findByUsername(user.getUsername()) != null) {
            log.warn("[WARN] " + user.getUsername() + " already exist");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        log.info("[INFO] " + user.getUsername() + " join Diamond Dogs");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        applicationUserRepository.save(user);
        motherBaseService.create(new MotherBase(user.getUsername()));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
