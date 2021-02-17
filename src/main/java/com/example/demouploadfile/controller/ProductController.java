package com.example.demouploadfile.controller;


import com.example.demouploadfile.Model.Product;
import com.example.demouploadfile.Service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;
import java.util.List;
@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private IProductService productService;

    @Value("${upload.path}")
    private String fileUpload;


    @GetMapping
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView("/list");
        List<Product> products = (List<Product>) productService.findAll();
        modelAndView.addObject("products", products);
        modelAndView.addObject("message", "Thanh cong");
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateProduct() {
        ModelAndView modelAndView = new ModelAndView("/create");
        modelAndView.addObject("product", new Product());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView saveProduct(@ModelAttribute("product") Product product) {
        MultipartFile multipartFile = product.getImage();
        String fileName = multipartFile.getOriginalFilename();

        try {
            FileCopyUtils.copy(product.getImage().getBytes(), new File(fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        product.setImgSrc(fileName);
        productService.save(product);
        ModelAndView modelAndView = new ModelAndView("/create");
        modelAndView.addObject("product", new Product());
        modelAndView.addObject("message", "New Product Created Successfully");
        return modelAndView;
    }
}
