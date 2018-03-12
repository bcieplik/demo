package com.bart.demo.message;

import com.bart.demo.AbstractEntity;
import com.bart.demo.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message extends AbstractEntity {

    public static final int MESSAGE_MAX_SIZE = 140;

    @ManyToOne
    private User creator;

    private String text;
}
