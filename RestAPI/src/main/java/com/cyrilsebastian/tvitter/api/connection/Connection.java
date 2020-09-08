package com.cyrilsebastian.tvitter.api.connection;

import com.cyrilsebastian.tvitter.api.profile.Profile;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "connections")
public class Connection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 16)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "requester")
    @ToString.Exclude @EqualsAndHashCode.Exclude private Profile requester;

    @ManyToOne
    @JoinColumn(name = "accepter")
    @ToString.Exclude @EqualsAndHashCode.Exclude private Profile accepter;

    private boolean established = false;
}
