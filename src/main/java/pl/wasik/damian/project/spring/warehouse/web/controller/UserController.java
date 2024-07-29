package pl.wasik.damian.project.spring.warehouse.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.wasik.damian.project.spring.warehouse.service.UserService;
import pl.wasik.damian.project.spring.warehouse.web.model.UserDto;

import java.util.logging.Logger;

@Controller
@RequestMapping("/users")
public class UserController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAll(Model model) {
        LOGGER.info("getAll()");
        model.addAttribute("users", userService.getAll());
        LOGGER.info("getAll(...) = ");
        return "user/list";
    }

    @GetMapping("/create")
    public String createView(Model model) {
        LOGGER.info("createView()");
        model.addAttribute("user", new UserDto());
        LOGGER.info("createView(...) = ");
        return "user/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute UserDto userDto) {
        LOGGER.info("create(" + userDto + ")");
        UserDto created = userService.create(userDto);
        LOGGER.info("create(...) = " + created);
        return "redirect:/users";
    }
}