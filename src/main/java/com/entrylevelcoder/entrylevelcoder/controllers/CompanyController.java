package com.entrylevelcoder.entrylevelcoder.controllers;

import com.entrylevelcoder.entrylevelcoder.models.Company;
import com.entrylevelcoder.entrylevelcoder.repositories.CompanyRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public class CompanyController {
    private CompanyRepository companyDao;

    public CompanyController(CompanyRepository companyDao) {
        this.companyDao = companyDao;
    }


    //Needs to be updated with correct View Which currently is being created
    @GetMapping("company/create")
    public String createCompany(Model model) {
        return null;
    }

    @PostMapping("/company/create")
    public String createCompanyPost(@ModelAttribute Company company) {
        companyDao.save(company);
        return "redirect: company/{id}/profile";
    }

    @GetMapping("company/{id}/update")
    public String updateCompany(@PathVariable long id, Model model) {
        Company company = companyDao.findById(id);
        return null;
    }

    @PostMapping("company/update")
    public String updateCompanyPost(@ModelAttribute Company company) {
        companyDao.save(company);
        return null;
    }

    @PostMapping("company/{id}/delete")
    public String deleteCompany(@PathVariable long id) {
        companyDao.deleteById(id);
        return null;
    }


}
