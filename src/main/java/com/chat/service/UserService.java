package com.chat.service;

import com.chat.domain.Role;
import com.chat.domain.User;
import com.chat.repos.UserRepo;
import com.sun.mail.iap.BadCommandException;
import javafx.scene.control.Hyperlink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.swing.text.html.HTML;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${urlEndPoint}")
    private String urlEndPoint;

    @Value("${mailSending}")
    private boolean mailSending;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if(user == null) {

            throw new LockedException("User not correct!");
        }

        return user;
    }
    public List getAllUser() {
        return userRepo.findAll();
    }

    public boolean addUser(User user) {
        User username = userRepo.findByUsername(user.getUsername());
        if(username == null) {
            user.setRoles(Collections.singleton(Role.USER));
            reActivateUser(user);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);

            return true;
        }
        return false;
    }

    private void reActivateUser(User user) {
        if(mailSending) {
            user.setActive(false);
            user.setActivationCode(UUID.randomUUID().toString());
            if (!StringUtils.isEmpty(user.getEmail())) {
                new Thread(new Runnable() {
                    public void run() {
                        mailSender.send(user.getEmail(), "Activation code",
                                String.format("Hello %s! \nWelcome to Chat. Please, visit next link for registration: \n%s/user/activate/%s",
                                        user.getUsername(), urlEndPoint, user.getActivationCode()));

                    }
                }).start();
            }
        } else {
            user.setActive(true);
        }
    }

    public boolean userSave(String username, String email, String password, Map<String, String> form, User user) {
        if (username != null && !username.isEmpty() && (user.getUsername() != null && !user.getUsername().equals(username))) {
            user.setUsername(username);
        }
        if (email != null && !email.isEmpty() && (user.getEmail() != null && !user.getEmail().equals(email))) {
            user.setEmail(email);
            reActivateUser(user);
        }
        if (password != null && !password.isEmpty() && (user.getPassword() != null && !user.getPassword().equals(password))) {
            user.setPassword(passwordEncoder.encode(password));
        }

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for(String key : form.keySet() ) {
            if(roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepo.save(user);
        return true;
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);

        if(user != null) {
            user.setActivationCode(null);
            user.setActive(true);
            userRepo.save(user);
            return true;
        }

        return false;
    }
}
