package com.cyrilsebastian.tvitter.api.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/profiles", consumes = {MediaType.ALL_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping
    public void signUp(@RequestBody(required = true) ProfileDto profileDto) {
        profileService.addProfileDto(profileDto);
    }

    @GetMapping
    public List<ProfileDto> getProfiles() {
        return profileService.getProfileDtos();
    }

    @GetMapping("/{profileId}")
    public ProfileDto getProfile(@PathVariable(required = true) UUID profileId) {
        return profileService.getProfileDto(profileId);
    }

    @PutMapping("/{profileId}")
    public void putProfile(@PathVariable UUID profileId, @RequestBody(required = true) ProfileDto profileDto) {
        profileService.updateProfileDto(profileDto);
    }

    @DeleteMapping("/{profileId}")
    public void deleteProfileDto(@PathVariable UUID profileId) {
        profileService.deleteProfileDto(profileId);
    }

    @GetMapping("/{profileId}/neighbors")
    public List<ProfileDto> getNearByProfiles(@PathVariable(required = true) UUID profileId, @RequestParam(defaultValue = "10") int distance, @RequestParam(defaultValue = "0") int page) {
        return profileService.getNearByProfileDtos(profileId, distance, page);
    }

    @PostMapping("/{requesterId}/request/{accepterId}")
    public void request(@PathVariable(required = true) UUID requesterId, @PathVariable(required = true) UUID accepterId) {
        profileService.request(requesterId, accepterId);
    }

    @PostMapping("/{accepterId}/accept/{requesterId}")
    public void accept(@PathVariable(required = true) UUID accepterId, @PathVariable(required = true) UUID requesterId) {
        profileService.accept(accepterId, requesterId);
    }

    @GetMapping("/{accepterId}/pending")
    public List<ProfileDto> pending(@PathVariable(required = true) UUID accepterId) {
        return profileService.pendingDtos(accepterId);
    }

    @GetMapping("/{accepterId}/followers")
    public List<ProfileDto> getFollowers(@PathVariable(required = true) UUID accepterId) {
        return profileService.getFollowerDtos(accepterId);
    }

    @GetMapping("/{requesterId}/following")
    public List<ProfileDto> getFollowing(@PathVariable(required = true) UUID requesterId) {
        return profileService.getFollowingDtos(requesterId);
    }

    @GetMapping("/{requesterId}/view/{accepterId}")
    public ProfileDto viewProfile(@PathVariable(required = true) UUID requesterId, @PathVariable(required = true) UUID accepterId) {
        return profileService.viewSubscribedProfileDto(requesterId, accepterId);
    }
}
