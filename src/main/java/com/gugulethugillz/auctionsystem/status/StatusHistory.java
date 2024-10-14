package com.gugulethugillz.auctionsystem.status;

import com.gugulethugillz.auctionsystem.asset.model.Asset;
import com.gugulethugillz.auctionsystem.common.BaseEntity;
import com.gugulethugillz.auctionsystem.payment.model.PaymentRequest;
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
