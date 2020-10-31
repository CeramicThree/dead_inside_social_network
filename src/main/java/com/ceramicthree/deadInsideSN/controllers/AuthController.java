package com.ceramicthree.deadInsideSN.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/auth")
    public String auth(){
        return "auth";
    }
}
