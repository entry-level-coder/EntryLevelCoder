package com.entrylevelcoder.entrylevelcoder.repositories;

import com.entrylevelcoder.entrylevelcoder.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findById(Long id);

    Post findById(long id);

    Post deleteById(long id);


}
