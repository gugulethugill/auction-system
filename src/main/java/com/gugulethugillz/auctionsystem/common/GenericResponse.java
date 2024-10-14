package com.gugulethugillz.auctionsystem.common;

import com.gugulethugillz.auctionsystem.common.enums.ResponseCode;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenericResponse<T> {
    private ResponseCode responseCode;
    private String narrative;
    private T entity;

    public GenericResponse(ResponseCode responseCode, String narrative, T entity) {
        this.responseCode = responseCode;
        this.narrative = narrative;
        this.entity = entity;
    }
}
