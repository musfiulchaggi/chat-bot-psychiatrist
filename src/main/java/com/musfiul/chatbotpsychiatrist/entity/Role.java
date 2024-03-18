package com.musfiul.chatbotpsychiatrist.entity;

import com.musfiul.chatbotpsychiatrist.constant.UserRole;
import com.musfiul.chatbotpsychiatrist.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ConstantTable.ROLE)
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
