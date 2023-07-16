package com.extension.findyourmeme.entity;

import com.extension.findyourmeme.generic.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Image extends BaseEntity{
    @Id
    @SequenceGenerator(name = "Image", sequenceName = "IMAGE_ID_SEQ")
    @GeneratedValue(generator = "Image")
    private Long id;
    private String imagePath;
    @ManyToMany(mappedBy = "images")
    @JsonManagedReference
    private Collection<Tag> tags = new ArrayList<>();
    private boolean isVerified;
}
