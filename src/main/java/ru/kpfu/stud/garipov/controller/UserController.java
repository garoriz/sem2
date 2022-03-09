package ru.kpfu.stud.garipov.controller;

import org.springframework.stereotype.Controller;
import ru.kpfu.stud.garipov.dto.CreateUserDto;
import ru.kpfu.stud.garipov.dto.UserDto;
import ru.kpfu.stud.garipov.helper.PasswordHelper;
import ru.kpfu.stud.garipov.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.stud.garipov.repository.UserRepository;
import ru.kpfu.stud.garipov.service.UserService;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.kpfu.stud.garipov.dto.UserDto.fromModel;

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

    @PostMapping("/user")
    @ResponseBody
    public UserDto createUser(@Valid @RequestBody CreateUserDto user) {
        return userService.save(user);
    }

    @PostMapping("/sign_up")
    public String signUp(@ModelAttribute(name = "user") CreateUserDto userDto) {
        userService.save(userDto);
        return "sign_up_success";
    }
}
