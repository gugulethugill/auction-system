package com.gugulethugillz.auctionsystem.winner.model;

import com.gugulethugillz.auctionsystem.bid.model.Bid;
import com.gugulethugillz.auctionsystem.common.BaseEntity;
import com.gugulethugillz.auctionsystem.person.model.User;
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

