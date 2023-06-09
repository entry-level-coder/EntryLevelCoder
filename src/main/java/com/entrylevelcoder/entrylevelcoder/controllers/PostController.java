package com.entrylevelcoder.entrylevelcoder.controllers;


import com.entrylevelcoder.entrylevelcoder.models.Post;
import com.entrylevelcoder.entrylevelcoder.models.User;
import com.entrylevelcoder.entrylevelcoder.repositories.PostRepository;
import com.entrylevelcoder.entrylevelcoder.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;
@Controller
public class
PostController {

    private final UserRepository companyDao;
    private final PostRepository postDao;

    public PostController( UserRepository companyDao, PostRepository postDao) {
        this.companyDao = companyDao;
        this.postDao = postDao;
    }


    @GetMapping("/posts/{id}/post")
    public String returnPost(@PathVariable Long id, Model model) {
        Optional<Post> optionalPost = postDao.findById(id);
        if (optionalPost.isPresent()) {
            Post post = postDao.findById(id).get();
            model.addAttribute("post", post);
            return null;
        } else {
            return "redirect:/posts?error=notfound";
        }
    }


    @GetMapping("/posts/create")
    public String returnPostCreateForm(Model model){
        User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = new Post();
        post.setCompany(companyDao.findById(sessionUser.getId()));
        model.addAttribute("newPost", post);
        return "createJob";
    }

    @PostMapping("/posts/create")
    public String savePost(@ModelAttribute Post post){
        User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setCompany(companyDao.findById(sessionUser.getId()));
        postDao.save(post);
        return "redirect:/posts";
    }

    @GetMapping("posts/{id}/update")
    public String updateForm(@PathVariable long id, Model model){
        User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post updatePost = postDao.findById(id);
        if (updatePost.getId() == sessionUser.getId()) {
            model.addAttribute("updatePost", updatePost);
        } else {
            return "redirect:/company/" + sessionUser.getId() + "/profile";
        }
        return "editJobPosting";
    }

    @PostMapping("posts/delete")
    public String deletePost(){
        User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return null;
    }


}
