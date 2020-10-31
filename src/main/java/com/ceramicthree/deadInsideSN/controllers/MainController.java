package com.ceramicthree.deadInsideSN.controllers;

import com.ceramicthree.deadInsideSN.models.Message;
import com.ceramicthree.deadInsideSN.models.User;
import com.ceramicthree.deadInsideSN.repos.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
public class MainController {
    Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private UserRepository userRepository;

    public User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optionalUser = userRepository.findById(auth.getName());
        if (optionalUser.isEmpty()) {
            logger.info("User not found");
            return null;
        }
        return optionalUser.get();
    }

    @GetMapping("/")
    public String main(Model model) {
        User user = getUser();
        if (user == null) return "main";

        model.addAttribute("name", user.getName());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("gender", user.getGender());
        model.addAttribute("locale", user.getLocale());
        model.addAttribute("userpic", user.getUserpic());
        model.addAttribute("lastvisit", user.getLastVisit());
        return "main";
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public Message sendMessage(@Payload Message message) throws Exception {
        User user = getUser();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm yyyy:MM:mm");
        message.setSenderName(user.getName());
        message.setSenderId(user.getId());
        message.setSenderPic(user.getUserpic());
        message.setTimestamp(dateFormat.format(new Date(System.currentTimeMillis())));
        logger.info("Message from " + message.getSenderName() + "sended");
        return message;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public Message addUser(@Payload Message Message,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", Message.getSenderName());
        return Message;
    }
}
