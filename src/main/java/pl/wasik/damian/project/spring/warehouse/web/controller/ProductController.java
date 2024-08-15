package pl.wasik.damian.project.spring.warehouse.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.wasik.damian.project.spring.warehouse.service.ProductService;
import pl.wasik.damian.project.spring.warehouse.web.model.ProductDto;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/products")
public class ProductController {

    private static final Logger LOGGER = Logger.getLogger(ProductController.class.getName());

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String listProducts(Model model) {
        LOGGER.info("listProducts()");
        List<ProductDto> products = productService.findAll();
        model.addAttribute("products", products);
        return "product/list";
    }

    @GetMapping("{id}")
    public String readView(@PathVariable Long id, Model model) {
        LOGGER.info("readView(" + id + ")");
        ProductDto productDto = productService.read(id);
        model.addAttribute("product", productDto);
        return "product/read";
    }

    @GetMapping("/create")
    public String createView(Model model) {
        LOGGER.info("createView()");
        model.addAttribute("product", new ProductDto());
        return "product/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("product") ProductDto productDto, @RequestParam("image") MultipartFile image, RedirectAttributes redirectAttributes) {
        try {
            productService.create(productDto, image);
            redirectAttributes.addFlashAttribute("successMessage", "The product was added successfully.");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Image processing error: " + e.getMessage());
            return "redirect:/products/create";
        }
        return "redirect:/products";
    }

    @GetMapping("/update/{id}")
    public String updateView(@PathVariable Long id, Model model) {
        LOGGER.info("updateView(" + id + ")");
        ProductDto productDto = productService.read(id);
        model.addAttribute("product", productDto);
        return "product/update";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("product") ProductDto productDto, @RequestParam("image") MultipartFile image, RedirectAttributes redirectAttributes) {
        try {
            productService.update(id, productDto, image);
            redirectAttributes.addFlashAttribute("successMessage", "The product was updated successfully.");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Image processing error: " + e.getMessage());
            return "redirect:/products/update/" + id;
        }
        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        LOGGER.info("delete(" + id + ")");
        productService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "The product was deleted successfully.");
        return "redirect:/products";
    }
}