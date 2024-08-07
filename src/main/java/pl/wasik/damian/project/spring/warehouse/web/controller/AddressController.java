package pl.wasik.damian.project.spring.warehouse.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
}
