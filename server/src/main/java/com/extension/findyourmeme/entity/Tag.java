package com.extension.findyourmeme.entity;

import com.extension.findyourmeme.generic.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tag extends BaseEntity{
    @Id
    @SequenceGenerator(name = "Tag", sequenceName = "TAG_ID_SEQ")
    @GeneratedValue(generator = "Tag")
    private Long id;
    private String tagName;
    @ManyToMany()
    @JoinTable(
            name="image_tag"
            ,joinColumns = @JoinColumn(name="tag_id")
            ,inverseJoinColumns = @JoinColumn(name="image_id")
    )
    @JsonBackReference
    private Collection<Image> images = new ArrayList<>();
}
