package com.chat.controller;

import com.chat.domain.Message;
import com.chat.domain.User;
import com.chat.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
public class AppController {
    @Autowired
    private MessageRepo messageRepo;
    @Value("${assets.path}")
    private String assetsPath;


    @GetMapping("/greeting")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String search, Model model) {
        Iterable<Message> messages = messageRepo.findAll();

        if(search.isEmpty()) {
            messages = messageRepo.findAll();
        } else {
            messages = messageRepo.findByTextMessageIgnoreCase(search);
        }
        Collections.reverse((List)messages);
        model.addAttribute("messages", messages);
        model.addAttribute("search", search);

        return "main";
    }

    @PostMapping("/main")
    public String add(@AuthenticationPrincipal User author,
                      @Valid Message message,
                      BindingResult bindingResult,
                      Model model,
                      @RequestParam("file") MultipartFile file) throws IOException {
        message.setAuthor(author);

        if (bindingResult.hasErrors()) {
            model.mergeAttributes(ControllerUtils.getErrorsMap(bindingResult));
            model.addAttribute("message", message);
        } else {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                File dirFile = new File(assetsPath + "/upload");
                if (!dirFile.exists()) {
                    dirFile.mkdir();
                }

                String filename = UUID.randomUUID().toString() + "." + file.getOriginalFilename();
                file.transferTo(new File(assetsPath + "/upload/" + filename));

                message.setFilename(filename);
            }

            messageRepo.save(message);
        }


        Iterable<Message> messages = messageRepo.findAll();
        Collections.reverse((List)messages);

        model.addAttribute("messages", messages);

        return "/main";
    }
}