package com.gugulethugillz.auctionsystem.model;

import com.gugulethugillz.auctionsystem.common.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Inheritance
@Entity

public class Winner extends BaseEntity {

    private String name;

    @OneToOne(targetEntity = Bid.class)
    private Bid bid;

    @ManyToOne(targetEntity = User.class)
    private User user;



}

