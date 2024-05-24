package com.example.Backend.Task1.endpoints;

import com.example.Backend.Task1.models.dtos.PostDTO;
import com.example.Backend.Task1.models.entities.Post;
import com.example.Backend.Task1.services.PostService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PostControllerTest {

    @InjectMocks
    private PostController postController;

    @Mock
    private PostService postService;

    @Test
    public void testCreatePost() {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("Test Title");
        postDTO.setDescription("Test Description with sufficient length");

        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());

        when(postService.createPost(any(Post.class))).thenReturn(post);

        ResponseEntity<Post> response = postController.createPost(postDTO.toPost());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Test Title", Objects.requireNonNull(response.getBody()).getTitle());
        assertEquals("Test Description with sufficient length", response.getBody().getDescription());
    }

    @Test
    public void testCreatePost_TitleTooShort() {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("Shor");
        postDTO.setDescription("Valid description");

        ResponseEntity<Post> response = postController.createPost(postDTO.toPost());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> errors = (Map<String, String>) response.getBody();
        assert errors != null;
        assertEquals("Title must be between 5 and 100 characters", errors.get("title"));
    }

    @Test
    public void testCreatePost_DescriptionTooShort() {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("Valid Title");
        postDTO.setDescription("Short");

        ResponseEntity<Post> response = postController.createPost(postDTO.toPost());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> errors = (Map<String, String>) response.getBody();
        assertEquals("Description must be between 10 and 1000 characters", errors.get("description"));
    }

    @Test
    public void testCreatePost_MissingTitle() {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("");
        postDTO.setDescription("Valid description");

        ResponseEntity<Post> response = postController.createPost(postDTO.toPost());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> errors = (Map<String, String>) response.getBody();
        assertEquals("Title is mandatory", errors.get("title"));
    }

    @Test
    public void testCreatePost_MissingDescription() {
        Post postDTO = new Post();
        postDTO.setTitle("Valid Title");
        postDTO.setDescription("");

        ResponseEntity<Post> response = postController.createPost(postDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> errors = (Map<String, String>) response.getBody();
        assertEquals("Description is mandatory", errors.get("description"));
    }

    @Test
    public void testCreatePost_ValidBoundaryConditions() {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("Valid Title");
        postDTO.setDescription("Valid description");

        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());

        when(postService.createPost(any(Post.class))).thenReturn(post);

        ResponseEntity<Post> response = postController.createPost(postDTO.toPost());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testCreatePost_TitleMaxLength() {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("T".repeat(100));
        postDTO.setDescription("Valid description");

        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());

        when(postService.createPost(any(Post.class))).thenReturn(post);

        ResponseEntity<Post> response = postController.createPost(postDTO.toPost());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(100, response.getBody().getTitle().length());
    }

    @Test
    public void testCreatePost_DescriptionMaxLength() {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("Valid Title");
        postDTO.setDescription("D".repeat(1000));

        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());

        when(postService.createPost(any(Post.class))).thenReturn(post);

        ResponseEntity<Post> response = postController.createPost(postDTO.toPost());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1000, response.getBody().getDescription().length());
    }
}