package com.cyrilsebastian.tvitter.api.profile;

import com.cyrilsebastian.tvitter.api.connection.Connection;
import com.cyrilsebastian.tvitter.api.connection.ConnectionService;
import com.cyrilsebastian.tvitter.api.user.User;
import com.cyrilsebastian.tvitter.api.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ProfileService {
    @Autowired
    private ProfileRepository repository;

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileMapper mapper;

    public void addProfile(Profile profile) {
        User user = profile.getUser();
        userService.addUser(user);
        profile.setId(user.getId());
        repository.save(profile);
    }

    public void addProfileDto(ProfileDto profileDto) {
        Profile profile = mapper.profileDtoToProfile(profileDto);
        addProfile(profile);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<ProfileDto> getProfileDtos() {
        List<ProfileDto> profileDtos = new ArrayList<>();
        repository.findAll().forEach(profile -> profileDtos.add(mapper.profileToProfileDto(profile)));
        return profileDtos;
    }


    public Profile getProfile(UUID id) {
        return (id==null) ? null : repository.findById(id).orElse(null);
    }

    @Cacheable(cacheNames = "profiles", key="#id", condition = "#id!=null", unless="#result == null")
    public ProfileDto getProfileDto(UUID id) {
        System.out.println("Cacheable");
        Profile profile = getProfile(id);
        return mapper.profileToProfileDto(profile);
    }


    public void updateProfile(Profile profile) {
        repository.save(profile);
    }

    @CachePut(cacheNames = "profiles", key="#profileDto.id", condition = "#profileDto!=null && #profileDto.id!=null", unless="#result == null")
    public ProfileDto updateProfileDto(ProfileDto profileDto) {
        System.out.println("CachePut");
        Profile profile = getProfile(profileDto.getId());
        mapper.updateUserFromUserDto(profileDto, profile);
        if(profile!=null) updateProfile(profile);
        return mapper.profileToProfileDto(profile);
    }


    @Caching(evict = {
            @CacheEvict(cacheNames = "profiles", key="#id", condition = "#id!=null"),
            @CacheEvict(cacheNames = "neighbors", key="#id", condition = "#id!=null"),
    })
    public void deleteProfileDto(UUID id) {
        Profile profile = getProfile(id);
        if(profile!=null) {
            repository.deleteById(id);
            userService.deleteUserDto(profile.getUser().getId());
        }
    }


    @Caching(evict = {
            @CacheEvict(cacheNames = "profiles", key="#id", condition = "#id!=null"),
            @CacheEvict(cacheNames = "neighbors", key="#id", condition = "#id!=null"),
            @CacheEvict(cacheNames = "users", key="#id", condition = "#id!=null"),
    })
    public void logout(UUID id) {
        System.out.println("logout Successful");
    }


    public List<Profile> getNearByProfiles(UUID id, int distance, int page) {
        Profile profile = getProfile(id);
        double latitude = profile.getLatitude();
        double longitude = profile.getLongitude();
        return repository.findAllByDistance(latitude, longitude, distance, id, page);
    }

    @Cacheable(cacheNames = "neighbors", key="#id", condition = "#id!=null", unless = "#result.size()==0")
    public List<ProfileDto> getNearByProfileDtos(UUID id, int distance, int page) {
        return getNearByProfiles(id, distance, page)
                .stream().map(profile -> mapper.profileToProfileDto(profile))
                .collect(Collectors.toList());
    }


    public void request(UUID requesterId, UUID accepterId) {
        Profile requester = repository.findById(requesterId).get();
        Profile accepter = repository.findById(accepterId).get();
        Connection connection = new Connection();
        connection.setRequester(requester);
        connection.setAccepter(accepter);
        connectionService.addConnection(connection);
    }
    public void accept(UUID accepterId, UUID requesterId) {
        Connection connection = connectionService.getPendingConnection(requesterId, accepterId);
        if(connection!=null) {
            connection.setEstablished(true);
            connectionService.updateConnection(connection);
        }
    }

    public List<ProfileDto> pendingDtos(UUID accepterId) {
        return connectionService.getPending(accepterId).stream()
                .map(connection -> mapper.profileToProfileDto(connection.getRequester()))
                .collect(Collectors.toList());
    }

    public List<ProfileDto> getFollowerDtos(UUID accepterId) {
        return connectionService.getFollowers(accepterId).stream()
                .map( connection -> mapper.profileToProfileDto(connection.getRequester()) )
                .collect(Collectors.toList());
    }

    public List<ProfileDto> getFollowingDtos(UUID requesterId) {
        return connectionService.getFollowing(requesterId).stream()
                .map( connection -> mapper.profileToProfileDto(connection.getAccepter()) )
                .collect(Collectors.toList());
    }

    public Profile viewSubscribedProfile(UUID requesterId, UUID accepterId) {
        Connection connection = connectionService.getEstablishedConnection(requesterId, accepterId);
        return (connection==null) ? null : connection.getAccepter();
    }

    public ProfileDto viewSubscribedProfileDto(UUID requesterId, UUID accepterId) {
        Profile subscribed = viewSubscribedProfile(requesterId, accepterId);
        return (subscribed==null) ? null : mapper.profileToProfileDto(subscribed);
    }
}
