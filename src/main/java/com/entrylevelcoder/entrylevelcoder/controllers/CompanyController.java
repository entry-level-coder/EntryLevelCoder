package com.entrylevelcoder.entrylevelcoder.controllers;

import com.entrylevelcoder.entrylevelcoder.models.Company;
import com.entrylevelcoder.entrylevelcoder.repositories.CompanyRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CompanyController {
    private final CompanyRepository companyDao;
    private  final PasswordEncoder passwordEncoder;

    public CompanyController(CompanyRepository companyDao, PasswordEncoder passwordEncoder) {
        this.companyDao = companyDao;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/company/signup")
    public String createCompany(Model model) {
        model.addAttribute("company", new Company());
        return "companySignup";
    }

    @PostMapping("/company/signup")
    public String createCompanyPost(@ModelAttribute Company company) {
        String hashedPassword = passwordEncoder.encode(company.getPassword());
        company.setPassword(hashedPassword);
        companyDao.save(company);
        return "redirect:/company/login";
    }

    @GetMapping("/company/login")
    public String companyLogin(Model model) {
        model.getAttribute("company");
        return "companyLogin";
    }

    @GetMapping("/company/{id}/profile")
    public String companyProfile(@PathVariable long id, Model model) {
        Company company = companyDao.findById(id);
        model.addAttribute("company", company);
        return "companyProfile";
    }

    @GetMapping("company/{id}/update")
    public String updateCompany(@PathVariable long id, Model model) {
        Company company = companyDao.findById(id);
        model.addAttribute("company", company);
        return "editCompanyProfile";
    }

    @PostMapping("/company/update")
    public String updateCompanyPost(@ModelAttribute Company company) {
        companyDao.save(company);
        return "redirect:/";
    }

    @PostMapping("company/{id}/delete")
    public String deleteCompany(@PathVariable long id) {
        companyDao.deleteById(id);
        return null;
    }


}
