package com.example.ecommerce.controller;


import com.example.ecommerce.model.dto.UserPasswordDTO;
import com.example.ecommerce.model.dto.UserRegisterDTO;
import com.example.ecommerce.model.dto.UserVerifyDTO;
import com.example.ecommerce.service.UserService;
import com.example.ecommerce.service.impl.userDetailServiceimpl;
import com.example.ecommerce.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private userDetailServiceimpl userDetailServiceimpl;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordUtils passwordUtils;


    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegisterDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserRegisterDTO userRegisterDTO, BindingResult bindingResult, Model model) {
        // Khởi tạo các thuộc tính lỗi để tránh lỗi null
        model.addAttribute("emailExist", null);
        model.addAttribute("passwordMatch", null);

        if (bindingResult.hasErrors()) {
            return "/register.html";
        }

        if (!userService.checkEmail(userRegisterDTO.getEmail())) {
            model.addAttribute("emailExist", "Email existed, please try another email!");
            return "/register.html";
        }

        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getRePassword())) {
            model.addAttribute("passwordMatch", "Passwords do not match!");
            return "/register.html";
        }

        userService.saveUser(userRegisterDTO);
        return "redirect:/login";
    }


    @GetMapping("/changepass")
    public String changePassword(Model model) {
        model.addAttribute("user", new UserPasswordDTO());
        return "changepass";
    }

    @PostMapping("/changepass")
    public String changePassword(@Valid @ModelAttribute("user") UserPasswordDTO userPasswordDTO, BindingResult bindingResult,
                                 Model model) {
        model.addAttribute("passwordMatch", null);
        model.addAttribute("passwordWrong", null);
        model.addAttribute("specialWord", null);

        if (bindingResult.hasErrors()) {
            return "/changepass.html";
        }

        //check pass
        if (!passwordUtils.isValidPassword(userPasswordDTO.getPassword())) {
            model.addAttribute("specialWord", "eror");
            return "/changepass.html";
        }

        if (!userService.checkPassword(userPasswordDTO.getCurrentPassword())) {
            model.addAttribute("passwordWrong", "wrong password !");
            return "/changepass.html";
        }

        if (!userPasswordDTO.getPassword().equals(userPasswordDTO.getRePassword())) {
            model.addAttribute("passwordMatch", "Passwords do not match!");
            return "/changepass.html";
        }

        userService.saveNewPassword(userPasswordDTO.getPassword());
        return "redirect:/home";
    }

    @GetMapping("/forgot/verifyaccount")
    public String getAccountVerify(Model model) {
        model.addAttribute("user", new UserVerifyDTO()); // Thêm email vào Model
        return "verifyAccount";
    }

    @PostMapping("/forgot/verifyaccount")
    public String accountVerify(@ModelAttribute("user") UserVerifyDTO userVerifyDTO, Model model) {
        model.addAttribute("emailNotExist", null);
        if (userService.checkEmail(userVerifyDTO.getEmail())) {
            model.addAttribute("emailNotExist", "error");
            return "verifyAccount";
        }
        return "redirect:/forgot/password";
    }

    @GetMapping("/forgot/password")
    public String forgotPassword(Model model) {
        model.addAttribute("user", new UserVerifyDTO());
        return "passwordForgort";
    }

    @PostMapping("/forgot/password")
    public String handleForgotPassword(@ModelAttribute("user") UserVerifyDTO userVerifyDTO, Model model) {

        model.addAttribute("passMatch", null);
        model.addAttribute("specialWord", null);

        if (!passwordUtils.isValidPassword(userVerifyDTO.getPassword())) {
            model.addAttribute("specialWord", "error");
            return "/passwordForgort";
        }

        if (!userVerifyDTO.getRePassword().equals(userVerifyDTO.getPassword())) {
            model.addAttribute("passMatch", "error");
            return "/passwordForgort";
        }
        userService.saveNewPassword(userVerifyDTO.getPassword());
        return "redirect:/login";
    }



}