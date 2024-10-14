package com.gugulethugillz.auctionsystem.person.model;

import com.gugulethugillz.auctionsystem.bid.model.Bid;
import com.gugulethugillz.auctionsystem.common.enums.UserRole;
import com.gugulethugillz.auctionsystem.winner.model.Winner;
import com.gugulethugillz.auctionsystem.payment.model.PaymentRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class User extends Person {
    @OneToMany(mappedBy = "user")
    private List<Winner> winners;

    @OneToMany(mappedBy = "user")
    private List<PaymentRequest> paymentRequests;

    @OneToMany(mappedBy = "user")
    private List<Bid> bids;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(
                new SimpleGrantedAuthority("ROLE_" + UserRole.USER.name())
        );
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
