package ru.kpfu.stud.garipov.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import ru.kpfu.stud.garipov.aspect.Loggable;
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

    @Operation(summary = "Returns all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All users",
                    content = {
                            @Content(mediaType = "application/json")
                    }
            )
    })
    @GetMapping("/user")
    @ResponseBody
    @Loggable
    public Iterable<UserDto> getAll() {
        return userService.getAll();
    }

    @Operation(summary = "Returns user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User",
                    content = {
                            @Content(mediaType = "application/json")
                    }
            )
    })
    @GetMapping("/user/{id}")
    @ResponseBody
    @Loggable
    public UserDto get(@PathVariable Integer id) {
        return userService.getById(id);
    }

    @Operation(summary = "User signs up")
    @PostMapping("/sign_up")
    @Loggable
    public String signUp(@ModelAttribute(name = "user") CreateUserDto userDto, HttpServletRequest request) throws MessagingException {
        String url = request.getRequestURL().toString().replace(request.getServletPath(), "");
        userService.signUp(userDto, url);
        return "sign_up_success";
    }

    @Operation(summary = "Verifies user")
    @GetMapping("/verify")
    @Loggable
    public String verify(@Param("code") String code) {
        if (userService.verify(code)) {
            return "verification_success";
        } else {
            return "verification_failed";
        }
    }
}
