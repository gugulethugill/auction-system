package com.gugulethugillz.auctionsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gugulethugillz.auctionsystem.common.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@NoArgsConstructor
@Entity

public class Category extends BaseEntity {
    private String name;

    @Override
    public String toString() {
        return "Category{" +
                "CategoryName='" + name + '\'' +
                '}';
    }

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Asset> assets;

}
