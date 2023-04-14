package com.entrylevelcoder.entrylevelcoder.controllers;


import com.entrylevelcoder.entrylevelcoder.repositories.UserRepository;
import com.entrylevelcoder.entrylevelcoder.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserController {

    private final UserRepository userDao;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userDao, PasswordEncoder passwordEncoder){
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    // Allows User To Input Info
    @GetMapping("/users/signup")
    public String showSignUpForm(Model model){
        model.addAttribute("user", new User());
        return "/userSignup";
    }


    // Saves User Input To Database
    @PostMapping("/users/signup")
    public String saveUser(@ModelAttribute User user){
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        userDao.save(user);
        return "redirect:/users/1/profile";
    }

    @GetMapping("/users/login")
    public String usersLogin(){
        return "/userLogin";
    }

    @GetMapping("users/{id}/profile")
    public String usersProfile(@PathVariable long id) {
        return "userProfile";
    }



    //Updates User Info
    @PostMapping("/users/{id}/update")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute User user) {
        User updateUser = userDao.findById(id).get();
        updateUser.setFirstName(user.getFirstName());
        updateUser.setLastName(user.getLastName());
        updateUser.setUsername(user.getUsername());
        updateUser.setPassword(user.getPassword());

        // update the post in database using id
        userDao.save(updateUser);
        return "redirect:/posts";

    }

    //     Delete user by ID
    @PostMapping("/users/{id}/delete")
    public String deleteUserById(@PathVariable("id") long id, User user) {
        // Check if the user exists before deleting
        userDao.deleteById(id);
        return "redirect:/login";
    }
}
