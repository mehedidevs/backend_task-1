package com.example.Backend.Task1.services;

import com.example.Backend.Task1.models.dtos.PostDTO;
import com.example.Backend.Task1.models.entities.Post;
import com.example.Backend.Task1.repositories.PostRepository;
import com.example.Backend.Task1.utils.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PostServiceImpl extends PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Post createPost(Post post) {
        validatePostDTO(post);
        return postRepository.save(post);
    }

    private void validatePostDTO(Post post) {
        Map<String, String> errors = new HashMap<>();

        if (post.getTitle() == null || post.getTitle().isBlank()) {
            errors.put("title", "Title is mandatory");
        } else if (post.getTitle().length() < 5 || post.getTitle().length() > 100) {
            errors.put("title", "Title must be between 5 and 100 characters");
        }

        if (post.getDescription() == null || post.getDescription().isBlank()) {
            errors.put("description", "Description is mandatory");
        } else if (post.getDescription().length() < 10 || post.getDescription().length() > 1000) {
            errors.put("description", "Description must be between 10 and 1000 characters");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}