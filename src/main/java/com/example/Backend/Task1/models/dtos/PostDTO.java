package com.example.Backend.Task1.models.dtos;

import com.example.Backend.Task1.models.entities.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDTO {

    private String title;
    private String description;

    public Post toPost() {
        Post post = new Post();
        post.setTitle(this.title);
        post.setDescription(this.description);
        return post;
    }
}