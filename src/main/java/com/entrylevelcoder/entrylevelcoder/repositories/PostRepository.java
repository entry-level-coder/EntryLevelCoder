package com.entrylevelcoder.entrylevelcoder.repositories;

import com.entrylevelcoder.entrylevelcoder.models.Post;

import java.util.Optional;

public interface PostRepository {

    Optional<Post> findById(Long id);

    Post findAll();

    Post deleteById(long id);


}
