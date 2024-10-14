package com.gugulethugillz.auctionsystem.model;

import com.gugulethugillz.auctionsystem.common.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Inheritance
@Entity
public class StatusHistory extends BaseEntity {
    private String entityStatus;
    private String otherStatus;
    private String description;

    @ManyToOne(targetEntity = Asset.class)
    private Asset asset;

    @ManyToOne(targetEntity = PaymentRequest.class)
    private PaymentRequest paymentRequest;
}
