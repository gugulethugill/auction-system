package com.gugulethugillz.auctionsystem.forms;

import com.gugulethugillz.auctionsystem.model.Asset;
import com.gugulethugillz.auctionsystem.model.Bid;
import com.gugulethugillz.auctionsystem.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class BidForm {

    private Long id;
    @NotNull(message = "Value is required")
    private BigDecimal value;

    @NotNull(message = "Asset is required")
    private Long assetId;

    public Bid getBid(Asset asset, User user) {
        Bid bid = new Bid();
        bid.setValue(value);
        bid.setUser(user);
        bid.setAsset(asset);
        return bid;
    }


    public static BidForm createBidForm(Bid bid) {
        if (bid == null) return BidForm.builder().build();
        BidForm bidForm = BidForm.builder()
                .assetId(bid.getAsset().getId())
                .value(bid.getValue())
                .build();
        return bidForm;
    }
}

