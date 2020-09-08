package com.cyrilsebastian.tvitter.api.post;

import com.cyrilsebastian.tvitter.api.connection.Connection;
import com.cyrilsebastian.tvitter.api.connection.ConnectionService;
import com.cyrilsebastian.tvitter.api.profile.Profile;
import com.cyrilsebastian.tvitter.api.profile.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class PostService {
    @Autowired
    private PostRepository repository;

//    @Autowired
//    private ConnectionService connectionService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private PostMapper mapper;

    public void addPost(Profile profile, Post post) {
        post.setOwner(profile);
        repository.save(post);
        profile.getPosts().add(post);
        profile.setPostCount(profile.getPostCount()+1);
        profileService.updateProfile(profile);
    }

    public void addPostDto(UUID requesterId, PostDto postDto) {
        Profile profile = profileService.getProfile(requesterId);
        Post post = mapper.postDtoToPost(postDto);
        addPost(profile, post);
    }

    public void updatePost(Post post) {
        repository.save(post);
    }

    public PostDto updatePostDto(PostDto postDto) {
        Post post = repository.findById(postDto.getId()).orElse(null);
        mapper.updatePostFromPostDto(postDto, post);
        if(post!=null) updatePost(post);
        return mapper.postToPostDto(post);
    }

    public List<PostDto> getMyPostDtos(UUID profileId) {
        Profile profile = profileService.getProfile(profileId);
        return profile.getPosts().stream()
                .map(post -> mapper.postToPostDto(post))
                .collect(Collectors.toList());
    }

    public List<PostDto> getSubscribedPostDtos(UUID requesterId, UUID accepterId) {
        Profile subscribed = profileService.viewSubscribedProfile(requesterId, accepterId);
        return subscribed.getPosts().stream()
                .map(post -> mapper.postToPostDto(post))
                .collect(Collectors.toList());
    }
}
