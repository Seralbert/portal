package ys.project.controllers;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ys.project.model.CustomDoc;
import ys.project.model.Note;
import ys.project.model.User;
import ys.project.service.DocService;
import ys.project.service.NoteService;
import ys.project.service.UserService;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by zorrax on 06.09.2018.
 * Основной контроллер проекта, проводит работу с доступом
 */
@Controller
public class MainController {
    @Autowired
    private UserService serviceUser;

    @Autowired
    public void setUserService(UserService serviceUser) {this.serviceUser = serviceUser;}


    @Value("${upload.path}")
    private String uploadPath;
    @Value("${var.server.addr}")
    private String serverAddr;

    @RequestMapping("/login")
    public String getLogin(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model
            ){
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        model.addAttribute("serverAddr", serverAddr);
        return "login";
    }

    @RequestMapping("/reg")
    public String newUser(
            Model model
    ){
        model.addAttribute("serverAddr", serverAddr);
        return "reg";
    }

    @PostMapping("/reg")
    public String addUser(@RequestParam("username")String username,
                          @RequestParam("password")String password,
                          Model model){
        model.addAttribute("serverAddr", serverAddr);
        if (!serviceUser.addUser(username, password)) {
            model.addAttribute("usernameError", "User exists!");
            return "reg";
        }

        return "redirect:/login";
    }
}
