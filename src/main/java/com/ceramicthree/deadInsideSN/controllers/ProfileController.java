package com.ceramicthree.deadInsideSN.controllers;

import com.ceramicthree.deadInsideSN.models.User;
import com.ceramicthree.deadInsideSN.repos.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
public class ProfileController {
    @Autowired
    private UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal){
        Optional<User> optionalUser = userRepository.findById(principal.getName());
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            model.addAttribute("name", user.getName());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("gender", user.getGender());
            model.addAttribute("locale", user.getLocale());
            model.addAttribute("userpic", user.getUserpic());
            model.addAttribute("lastvisit", user.getLastVisit());
        }else {
            model.addAttribute("name", "null");
            model.addAttribute("email", "null");
            model.addAttribute("gender", "null");
            model.addAttribute("locale", "null");
            model.addAttribute("userpic", "null");
            model.addAttribute("lastvisit", "null");

            logger.error("Cannot load user details. User info set as null");
        }

        return "profile";
    }
}
