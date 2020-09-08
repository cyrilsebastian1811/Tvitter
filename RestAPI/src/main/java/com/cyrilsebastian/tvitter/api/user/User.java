package com.cyrilsebastian.tvitter.api.user;

import com.cyrilsebastian.tvitter.api.role.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 16)
    private UUID id;

//    The type java.util.Date contains both date and time information, up to millisecond precision. But it doesn't directly relate to any SQL type.
//    This is why we need another annotation to specify the desired SQL type:
    @Column // mapping is relatively straight-forward. We can use either the @Basic or the @Column annotation:
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(length = 30, unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
    private String email;


    @ToString.Exclude
    @NotNull
    private String password;

    @NotNull
    @ManyToMany(fetch=FetchType.EAGER)
    @ToString.Exclude @EqualsAndHashCode.Exclude private Set<Role> roles;

//    @OneToOne(optional = false, cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.LAZY)
////    @ToString.Exclude
//    private Profile profile;
}
