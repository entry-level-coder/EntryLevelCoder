package com.entrylevelcoder.entrylevelcoder.controllers;


import com.entrylevelcoder.entrylevelcoder.repositories.UserRepository;
import com.entrylevelcoder.entrylevelcoder.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


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
        System.out.println("inside saveuser");
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        userDao.save(user);
        return "redirect:/users/login";
    }
//
//    @GetMapping("/users/login")
//    public String usersLogin(){
//        return "/userLogin";
//    }


    @GetMapping("/users/{id}/update")
    public String updateUsersGet(@PathVariable long id, Model model) {
        User user = userDao.findById(id);
        model.addAttribute("user", user);
        return "editUserProfile";
    }



    //Updates User Info
    @PostMapping("/users/{id}/update")
    public String updateUser(@PathVariable("id") long id, @RequestParam("firstName") String firstName,  @RequestParam("lastName") String lastName , @RequestParam("username") String username,  @ModelAttribute User user, Model model) {
        User updateUser = userDao.findById(id);
        updateUser.setFirstName(firstName);
        updateUser.setLastName(lastName);
        updateUser.setUsername(username);

        // update the post in database using id
        userDao.save(updateUser);
        model.addAttribute("message", "User updated successfully.");
        return "redirect:/posts";

    }

    //     Delete user by ID
    @PostMapping("/users/{id}/delete")
    public String deleteUserById(@PathVariable("id") long id) {
        // Check if the user exists before deleting
        userDao.deleteById(id);

        return "redirect:/users/login";
    }



    @GetMapping("/users/profile")
    public String getToProfileFromLogin( Model model){
        User user = userDao.findById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        model.addAttribute("user", user);
        return "userProfile";
    }
}
