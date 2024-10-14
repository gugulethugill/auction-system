package com.gugulethugillz.auctionsystem.common;

import com.gugulethugillz.auctionsystem.common.enums.EntityStatus;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime dateCreated;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime lastUpdated;

    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    private String createdBy;
    private String lastUpdatedBy;

    @Version
    private Long version;

    @PrePersist
    public void initialSetup() {
        if(status == null) status = EntityStatus.ACTIVE;
    }


}
