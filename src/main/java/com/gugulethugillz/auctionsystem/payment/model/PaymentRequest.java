package com.gugulethugillz.auctionsystem.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gugulethugillz.auctionsystem.bid.model.Bid;
import com.gugulethugillz.auctionsystem.common.BaseEntity;
import com.gugulethugillz.auctionsystem.common.enums.PaymentMethod;
import com.gugulethugillz.auctionsystem.common.enums.PaymentStatus;
import com.gugulethugillz.auctionsystem.asset.model.Asset;
import com.gugulethugillz.auctionsystem.status.StatusHistory;
import com.gugulethugillz.auctionsystem.person.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Inheritance
@Entity
public class PaymentRequest extends BaseEntity {
    private BigDecimal value;
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime date;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private String pollUrl;
    private String transactionStatus;
    private String item;
    private String mobileNumber;
    private String description;
    private String merchantReference;
    private String paynowReference;
    private String systemReference;

    @JsonIgnore
    @OneToOne(targetEntity = Asset.class)
    private Asset asset;

    @JsonIgnore
    @OneToOne(targetEntity = Bid.class)
    private Bid bid;

    @JsonIgnore
    @ManyToOne(targetEntity = User.class)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "paymentRequest")
    private List<StatusHistory> statusHistories;
}
