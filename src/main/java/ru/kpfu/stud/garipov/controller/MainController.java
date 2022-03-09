package ru.kpfu.stud.garipov.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kpfu.stud.garipov.dto.CreateUserDto;

@Controller
public class MainController {
    @GetMapping("")
    public String getIndexPage() {
        return "index";
    }

    @GetMapping("/sign_up")
    public String getSignUp(Model model) {
        model.addAttribute("user", new CreateUserDto());
        return "sign_up";
    }

    @GetMapping("/home")
    public String getHome() {
        return "home";
    }
}
