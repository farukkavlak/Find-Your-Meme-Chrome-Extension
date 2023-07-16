package com.extension.findyourmeme.entity;

import com.extension.findyourmeme.generic.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class _User extends BaseEntity {
    @Id
    @SequenceGenerator(name = "User", sequenceName = "USER_ID_SEQ")
    @GeneratedValue(generator = "User")
    private Long id;
    @Column(length = 50,nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    private String role;
}
