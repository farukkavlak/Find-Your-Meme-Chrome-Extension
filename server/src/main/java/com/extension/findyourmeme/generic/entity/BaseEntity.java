package com.extension.findyourmeme.generic.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity implements BaseModel, Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    @Embedded
    private BaseAdditionalFields baseAdditionalFields;
}

