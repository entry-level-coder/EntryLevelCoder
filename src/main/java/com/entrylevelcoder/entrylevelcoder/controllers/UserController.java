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
        System.out.println("inside saveuser");
        String hash = passwordEncoder.encode(user.getPassword());
        user.setCompany(false);
        user.setPassword(hash);
        userDao.save(user);
        return "redirect:/users/login";
    }
//
    @GetMapping("/users/login")
    public String usersLogin(){
        return "/userLogin";
    }


    @GetMapping("/users/{id}/update")
    public String updateUsersGet(@PathVariable long id, Model model) {
        User user = userDao.findById(id);
        model.addAttribute("user", user);
        return "editUserProfile";
    }



    //Updates User Info
    @PostMapping("/users/{id}/update")
    public String updateUser(@PathVariable("id") long id, @ModelAttribute User user) {
        User updateUser = userDao.findById(id);
        updateUser.setFirstName(user.getFirstName());
        updateUser.setLastName(user.getLastName());
        updateUser.setUsername(user.getUsername());
        updateUser.setPassword(user.getPassword());

        // update the post in database using id
        userDao.save(updateUser);
        return "redirect:posts";

    }

    //     Delete user by ID
    @PostMapping("/users/{id}/delete")
    public String deleteUserById(@PathVariable("id") long id, User user) {
        // Check if the user exists before deleting
        userDao.deleteById(id);
        return "redirect:/login";
    }



    @GetMapping("/users/{id}/profile")
    public String getToProfileFromLogin(@PathVariable long id, Model model){
        User user = userDao.findById(id);
        model.addAttribute("user", user);
        return "userProfile";
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
        return "redirect:/company/login";
    }

//    @GetMapping("/company/login")
//    public String companyLogin(Model model) {
//        model.getAttribute("company");
//        return "companyLogin";
//    }

    @GetMapping("/company/{id}/profile")
    public String companyProfile(@PathVariable long id, Model model) {
        User company = userDao.findById(id);
        model.addAttribute("company", company);
        return "companyProfile";
    }

    @GetMapping("company/{id}/update")
    public String updateCompany(@PathVariable long id, Model model) {
        User company = userDao.findById(id);
        model.addAttribute("company", company);
        return "editCompanyProfile";
    }

    @PostMapping("company/update")
    public String updateCompanyPost(@ModelAttribute User company) {
        userDao.save(company);
        return "redirect: /";
    }

    @PostMapping("company/{id}/delete")
    public String deleteCompany(@PathVariable long id) {
        userDao.deleteById(id);
        return null;
    }
}
