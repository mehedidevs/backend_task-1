package com.example.Backend.Task1.services;

import com.example.Backend.Task1.models.dtos.PostDTO;
import com.example.Backend.Task1.models.entities.Post;


public abstract class PostService {
    public abstract Post createPost(Post post);
}