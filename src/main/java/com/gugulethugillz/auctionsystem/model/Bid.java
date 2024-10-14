package com.gugulethugillz.auctionsystem.model;

import com.gugulethugillz.auctionsystem.common.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@Inheritance
@Entity
public class Bid extends BaseEntity {
    private BigDecimal value;
    private OffsetDateTime paymentDate;

    @ManyToOne(targetEntity = Asset.class)
    private Asset asset;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @OneToOne(mappedBy = "bid")
    private PaymentRequest paymentRequest;

    @OneToOne(mappedBy = "bid")
    private Winner winner;

    @Override
    public String toString() {
        return "Bid{" +
                "value=" + value +
                ", paymentDate=" + paymentDate +
                '}';
    }
}
