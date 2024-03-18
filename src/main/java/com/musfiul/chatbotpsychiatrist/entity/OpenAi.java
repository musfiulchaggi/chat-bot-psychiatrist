package com.musfiul.chatbotpsychiatrist.entity;

import com.musfiul.chatbotpsychiatrist.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = ConstantTable.OPEN_AI_TABLE)
public class OpenAi {
    @Id
    private String id;

    @Column(name = "request_message")
    private String requestMessage;

    @Column(name = "model")
    private String model;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", updatable = false)
    private Date created;

    @Column(name = "response_message", columnDefinition="TEXT")
    private String responseMessage;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
