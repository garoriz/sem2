package ru.kpfu.stud.garipov.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kpfu.stud.garipov.aspect.Loggable;
import ru.kpfu.stud.garipov.dto.CreateUserDto;

@Controller
public class MainController {

    @Operation(summary = "Returns index page")
    @GetMapping("")
    @Loggable
    public String getIndexPage() {
        return "index";
    }

    @Operation(summary = "Returns sign up page")
    @GetMapping("/sign_up")
    @Loggable
    public String getSignUp(Model model) {
        model.addAttribute("user", new CreateUserDto());
        return "sign_up";
    }

    @Operation(summary = "Returns home page")
    @GetMapping("/home")
    @Loggable
    public String getHome() {
        return "home";
    }
}
