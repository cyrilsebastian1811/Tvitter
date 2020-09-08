package com.cyrilsebastian.tvitter.api.profile;

import com.cyrilsebastian.tvitter.api.connection.Connection;
import com.cyrilsebastian.tvitter.api.post.Post;
import com.cyrilsebastian.tvitter.api.user.User;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "profiles")
public class Profile implements Serializable {
    @Id
    @Column(length = 16)
    private UUID id;

    private String about;
    @NotNull
    private String name;
    @NotNull
    private char gender;
    @Pattern(regexp = "(\\d+)(,\\s*\\d+)*")
    private String interests;
    @Pattern(regexp = "(\\d+)(,\\s*\\d+)*")
    private String languages;

    @NotNull
    @Column(columnDefinition = "FLOAT")
    private float latitude;

    @NotNull
    @Column(columnDefinition = "FLOAT")
    private float longitude;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    @ToString.Exclude @EqualsAndHashCode.Exclude private User user;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude @EqualsAndHashCode.Exclude private List<Post> posts = new ArrayList<>();

    private int postCount=0;
}
