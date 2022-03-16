package ru.kpfu.stud.garipov.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import ru.kpfu.stud.garipov.dto.CreateUserDto;
import ru.kpfu.stud.garipov.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.stud.garipov.service.UserService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    @ResponseBody
    public Iterable<UserDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public UserDto get(@PathVariable Integer id) {
        return userService.getById(id);
    }

    @PostMapping("/sign_up")
    public String signUp(@ModelAttribute(name = "user") CreateUserDto userDto, HttpServletRequest request) throws MessagingException {
        String url = request.getRequestURL().toString().replace(request.getServletPath(), "");
        userService.signUp(userDto, url);
        return "sign_up_success";
    }

    @GetMapping("/verify")
    public String verify(@Param("code") String code) {
        if (userService.verify(code)) {
            return "verification_success";
        } else {
            return "verification_failed";
        }
    }
}
