package com.musfiul.chatbotpsychiatrist.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.musfiul.chatbotpsychiatrist.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = ConstantTable.USER)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "mobile_phone_no")
    private String mobilePhone;

    @Column(name = "address")
    private String address;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "status")
    private Boolean status;

    @OneToMany(mappedBy = "user")
    private List<OpenAi> prompts;

    @OneToOne
    @JoinColumn(name = "user_account_id", unique = true)
    private UserAccount userAccount;

}