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
        return "userSignup";
    }


    // Saves User Input To Database
    @PostMapping("/users/signup")
    public String saveUser(@ModelAttribute User user){
        String hash = passwordEncoder.encode(user.getPassword());
        user.setCompany(false);
        user.setPassword(hash);
        userDao.save(user);
        return "redirect:/login";
    }
//
//    @GetMapping("/users/login")
//    public String usersLogin(){
//        return "/userLogin";
//    }


    @GetMapping("/users/{id}/edit")
    public String updateUsersGet(@PathVariable("id") long id, Model model) {
        User user = userDao.findById(id);
        model.addAttribute("user", user);
        return "editUserProfile";
    }



    //Updates User Info
    @PostMapping("/users/{id}/update")
    public String updateUser(@ModelAttribute User updateUsers, Model model) {
        User updateUser = userDao.findById(updateUsers.getId());
        updateUser.setFirstName(updateUsers.getFirstName());
        updateUser.setLastName(updateUsers.getLastName());
        updateUser.setUsername(updateUsers.getUsername());
        updateUser.setDescription(updateUsers.getDescription());
        updateUser.setPassword(passwordEncoder.encode(updateUsers.getPassword()));


        // update the post in database using id
        userDao.save(updateUser);
        System.out.println("Saved!");
        model.addAttribute("message", "User updated successfully.");
        return "redirect:/users/profile";

    }

    //     Delete user by ID
    @PostMapping("/users/{id}/delete")
    public String deleteUserById(@PathVariable("id") long id) {
        // Check if the user exists before deleting
        userDao.deleteById(id);

        return "redirect:/login";
    }



    @GetMapping("/users/profile")
    public String getToProfileFromLogin(Model model){
        User user = userDao.findById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        if (!user.getCompany()) {
            model.addAttribute("user", user);
            return "userProfile";
        } else {
            model.addAttribute("company", user);
            return "companyProfile";
        }
    }


    // Company mapping

    @GetMapping("/company/signup")
    public String createCompany(Model model) {
        model.addAttribute("company", new User());
        return "companySignup";
    }

    @PostMapping("/company/signup")
    public String createCompanyPost(@ModelAttribute User company) {
        String hashedPassword = passwordEncoder.encode(company.getPassword());
        company.setPassword(hashedPassword);
        company.setCompany(true);
        userDao.save(company);
        return "redirect:/login";
    }


    @GetMapping("/company/profile")
    public String companyProfile(Model model) {
        User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User company = userDao.findById(sessionUser.getId());
        if(company.getCompany()) {
            model.addAttribute("company", company);
        } else {
            return "redirect:/";
        }
        return "companyProfile";
    }

    @GetMapping("/company/{id}/edit")
    public String updateCompany(@PathVariable("id") long id, Model model) {
        User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User company = userDao.findById(id);
        System.out.println(company.getCompany());
        if(company.getCompany()) {
            model.addAttribute("company", company);
        } else {
            return "redirect:/";
        }
        return "editCompanyProfile";
    }


    @PostMapping("/company/{id}/update")
    public String updateCompanyPost(@ModelAttribute User updateCompanies) {
        System.out.println("Inside updateCompanyPost");
        User updateCompany = userDao.findById(updateCompanies.getId());
        updateCompany.setCompanyName(updateCompanies.getCompanyName());
        updateCompany.setUsername(updateCompanies.getUsername());
        updateCompany.setCity(updateCompanies.getCity());
        updateCompany.setState(updateCompanies.getState());
        updateCompany.setIndustry(updateCompanies.getIndustry());
        updateCompany.setUrl(updateCompanies.getUrl());
        updateCompany.setDescription(updateCompanies.getDescription());
        userDao.save(updateCompany);
        return "redirect:/company/profile";
    }

    @PostMapping("/company/{id}/delete")
    public String deleteCompany(@PathVariable long id) {
        userDao.deleteById(id);
        return "redirect:/login";
    }




}