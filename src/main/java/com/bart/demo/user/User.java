package com.bart.demo.user;

import com.bart.demo.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractEntity {

    public static final int USERNAME_MAX_SIZE = 20;

    @Column(unique = true)
    private String username;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "users_subscribed_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "subscribed_user_id")
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<User> subscribedUsers = new HashSet<>();
}
