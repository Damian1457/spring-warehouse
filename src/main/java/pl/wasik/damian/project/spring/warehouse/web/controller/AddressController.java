package pl.wasik.damian.project.spring.warehouse.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.wasik.damian.project.spring.warehouse.service.AddressService;
import pl.wasik.damian.project.spring.warehouse.web.model.AddressDto;

import java.util.logging.Logger;

@Controller
@RequestMapping("/addresses")
public class AddressController {

    private static final Logger LOGGER = Logger.getLogger(AddressController.class.getName());
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public String getAll(Model model) {
        LOGGER.info("getAll()");
        model.addAttribute("addresses", addressService.getAll());
        LOGGER.info("getAll(...) = ");
        return "address/list";
    }

    @GetMapping("/create")
    public String createView(Model model) {
        LOGGER.info("createView()");
        model.addAttribute("address", new AddressDto());
        LOGGER.info("createView(...) = ");
        return "address/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute AddressDto addressDto) {
        LOGGER.info("create(" + addressDto + ")");
        AddressDto created = addressService.create(addressDto);
        LOGGER.info("create(...) = " + created);
        return "redirect:/addresses";
    }

    @GetMapping("/read/{id}")
    public String read(@PathVariable Long id, Model model) {
        LOGGER.info("read(" + id + ")");
        AddressDto addressDto = addressService.read(id);
        model.addAttribute("address", addressDto);
        LOGGER.info("read(...) = " + addressDto);
        return "address/read";
    }

    @GetMapping("/update/{id}")
    public String updateView(@PathVariable Long id, Model model) {
        LOGGER.info("updateView(" + id + ")");
        AddressDto addressDto = addressService.read(id);
        model.addAttribute("address", addressDto);
        LOGGER.info("updateView(...) = " + addressDto);
        return "address/update";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute AddressDto addressDto) {
        LOGGER.info("update(" + id + ", " + addressDto + ")");
        AddressDto updatedAddress = addressService.update(id, addressDto);
        LOGGER.info("update(...) = " + updatedAddress);
        return "redirect:/addresses";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        LOGGER.info("delete(" + id + ")");
        addressService.delete(id);
        LOGGER.info("delete(...) = ");
        return "redirect:/addresses";
    }
}
