package com.chat.controller;

import com.chat.domain.Role;
import com.chat.domain.User;
import com.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.getAllUser());
        return "userList";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/{user:\\d+}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping
    public String userSave(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam Map<String, String> form,
                           @RequestParam("userId") User user) {
        userService.userSave(username, email, password, form, user);
        return "redirect:/user";
    }

    @PostMapping("/profile")
    public String userChange(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam Map<String, String> form,
                           @RequestParam("userId") User user) {
        userService.userSave(username, email, password, form, user);
        return "redirect:/main";
    }

    @GetMapping("/profile")
    public String getUserData(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "/profile";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid User user, BindingResult bindingResult, Model model) {

        if (!bindingResult.hasErrors() ) {
            if (userService.addUser(user)) {
                return "redirect:/user/login";
            }
            else {
                model.addAttribute("message", "UserExist!");
            }
        } else {
            model.mergeAttributes(ControllerUtils.getErrorsMap(bindingResult));
        }

        model.addAttribute("user", user);
        return "/registration";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        if (userService.activateUser(code)) {
            model.addAttribute("message", "User successfully activated");
            return "/login";
        }

        model.addAttribute("message", "An error has occurred");
        return "/login";
    }

}
