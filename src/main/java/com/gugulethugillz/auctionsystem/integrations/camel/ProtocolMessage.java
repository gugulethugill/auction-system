package com.gugulethugillz.auctionsystem.integrations.camel;

import lombok.Data;

@Data
public class ProtocolMessage {
    private String channel;
    private String message;
}
