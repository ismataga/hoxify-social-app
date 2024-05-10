package com.hoxify.hoxify_new.user;

import com.hoxify.hoxify_new.configration.CurrentUser;
import com.hoxify.hoxify_new.email.EmailService;
import com.hoxify.hoxify_new.exception.ActivationNotificationException;
import com.hoxify.hoxify_new.exception.InvalidTokenException;
import com.hoxify.hoxify_new.exception.NotFoundException;
import com.hoxify.hoxify_new.exception.NotUniqueEmailException;
import com.hoxify.hoxify_new.file.FileService;
import com.hoxify.hoxify_new.user.dto.UserDTO;
import com.hoxify.hoxify_new.user.dto.UserProjection;
import com.hoxify.hoxify_new.user.dto.UserUpdate;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    FileService fileService;

    @Transactional(rollbackOn = MailException.class)
    public void save(User user) {
        try {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            user.setActivationToken(UUID.randomUUID().toString());
            emailService.sendActivationEmail(user.getEmail(), user.getActivationToken());
            userRepository.saveAndFlush(user);
        } catch (DataIntegrityViolationException e) {
            throw new NotUniqueEmailException();
        } catch (MailException e) {
            throw new ActivationNotificationException();

        }
    }


    public void activateUser(String token) {
        User inDB = userRepository.findByActivationToken(token);
        if (inDB == null) {
            throw new InvalidTokenException();
        }
        inDB.setActive(true);
        inDB.setActivationToken(null);
        userRepository.save(inDB);
    }

    public Page<User> getUsers(Pageable pageable, CurrentUser currentUser) {

        if (currentUser == null) {
            return userRepository.findAll(pageable);
        }

        return userRepository.findByIdNot(currentUser.getId(), pageable);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User updateUser(Long id, UserUpdate user) {
        User inDB = getUser(id);
        inDB.setUsername(user.username());

        if(user.image() != null) {
            String dileName = fileService.saveBaseAsFile(user.image());
            fileService.deletePtofiliImage(inDB.getImage());
            inDB.setImage(dileName);
        }
        return userRepository.save(inDB);
    }
}
