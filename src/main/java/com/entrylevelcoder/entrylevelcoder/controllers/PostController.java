package com.entrylevelcoder.entrylevelcoder.controllers;


import com.entrylevelcoder.entrylevelcoder.models.Post;
import com.entrylevelcoder.entrylevelcoder.repositories.PostRepository;
import com.entrylevelcoder.entrylevelcoder.repositories.UserRepository;
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

    @GetMapping("/posts")
    public String returnPost(Model model){
        model.addAttribute("posts", postDao.findAll());
        return "browseJobs";
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
        model.addAttribute("newPost", new Post());
        return "createJob";
    }

    @PostMapping("/post/create")
    public String savePost(@ModelAttribute Post post){
        post.setCompany(companyDao.findById(post.getCompany().getId()));
        postDao.save(post);
        return "redirect:company/{id}/profile";
    }

    @GetMapping("posts/{id}/update")
    public String updateForm(@PathVariable long id, Model model){
        Post updatePost = postDao.findById(id);
        model.addAttribute("updatePost", updatePost);
        return "editJobPosting";
    }
//
//    @PostMapping("")
//    public String saveUpdatePost(@ModelAttribute Post postUpdates){
//        Post postToUpdate = postDao.findById(postUpdates.getId()).get();
//        postToUpdate.setTitle(postUpdates.getTitle());
//        postToUpdate.setDescription(postUpdates.getDescription());
//        postToUpdate.setMinSalary(postUpdates.getMinSalary());
//        postToUpdate.setMaxSalary(postUpdates.getMaxSalary());
//        postToUpdate.setLocation(postUpdates.getLocation());
//        postToUpdate.setModality(postUpdates.getModality());
//        postToUpdate.setPostUrl(postUpdates.getPostUrl());
//        postDao.save(postToUpdate);
//        return null;
//    }
//
    @PostMapping("posts/delete")
    public String deletePost(@PathVariable long id){
        postDao.deleteById(id);
        return null;
    }

}
