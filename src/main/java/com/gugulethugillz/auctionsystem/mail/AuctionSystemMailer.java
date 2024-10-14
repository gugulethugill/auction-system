package com.gugulethugillz.auctionsystem.mail;

public interface AuctionSystemMailer {
    void sendEmail(String subject, String message, String emailAddress);
}
