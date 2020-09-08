package com.cyrilsebastian.tvitter.api.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/profiles", consumes = {MediaType.ALL_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/{profileId}/posts")
    public void addPost(@PathVariable(required = true) UUID profileId, @RequestBody(required = true) PostDto postDto) {
        postService.addPostDto(profileId, postDto);
    }

    @PutMapping("/{profileId}/posts")
    public void putPost(@PathVariable(required = true) UUID profileId, @RequestBody(required = true) PostDto postDto) {
        postService.updatePostDto(postDto);
    }

    @GetMapping("/{profileId}/posts")
    public List<PostDto> viewPosts(@PathVariable(required = true) UUID profileId) {
        return postService.getMyPostDtos(profileId);
    }

    @GetMapping("/{subscriberId}/subscribed/{subscribedId}/posts")
    public List<PostDto> viewSubscribedPosts(@PathVariable(required = true) UUID subscriberId, @PathVariable(required = true) UUID subscribedId) {
        return postService.getSubscribedPostDtos(subscriberId, subscribedId);
    }
}
