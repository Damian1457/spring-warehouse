package pl.wasik.damian.project.spring.warehouse.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/read/{id}")
    public String read(@PathVariable Long id, Model model) {
        LOGGER.info("read(" + id + ")");
        UserDto userDto = userService.read(id);
        model.addAttribute("user", userDto);
        LOGGER.info("read(...) = " + userDto);
        return "user/read";
    }

    @GetMapping("/update/{id}")
    public String updateView(@PathVariable Long id, Model model) {
        LOGGER.info("updateView(" + id + ")");
        UserDto userDto = userService.read(id);
        model.addAttribute("user", userDto);
        LOGGER.info("updateView(...) = " + userDto);
        return "user/update";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute UserDto userDto) {
        LOGGER.info("update(" + id + ", " + userDto + ")");
        UserDto updatedUser = userService.update(id, userDto);
        LOGGER.info("update(...) = " + updatedUser);
        return "redirect:/users";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        LOGGER.info("delete(" + id + ")");
        userService.delete(id);
        LOGGER.info("delete(...) = ");
        return "redirect:/users";
    }
}