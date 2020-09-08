package com.cyrilsebastian.tvitter.api.role;

import com.cyrilsebastian.tvitter.api.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 16)
    private UUID id;

    @Column(updatable = false)
    private String name;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.REMOVE)
    @ToString.Exclude @EqualsAndHashCode.Exclude List<User> users;
}
