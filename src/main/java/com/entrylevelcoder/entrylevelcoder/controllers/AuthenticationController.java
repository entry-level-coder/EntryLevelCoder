package com.entrylevelcoder.entrylevelcoder.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {

<<<<<<< HEAD

@GetMapping("/users/login")
public String returnUserLoginPage(){
    System.out.println("inside returnLoginPage method");
    return "userLogin";
}



//@GetMapping("/company/login")
//public String returnCompanyLoginPage(){
//    return "companyLogin";
//}

=======
    @GetMapping("/login")
    public String returnUserLoginPage() {
//    System.out.println("inside returnLoginPage method");
        return "login";
    }
>>>>>>> main

}
