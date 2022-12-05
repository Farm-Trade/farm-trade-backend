package com.farmtrade.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "messages")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_user_id", updatable = false)
    private User fromUser;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "conversation_id", updatable = false)
    private Conversation conversation;
    private String text;
    @CreationTimestamp
    private Timestamp createdAt;
}
