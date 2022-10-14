package ru.gb.hubr.entity.common;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
public class InfoEntity extends BaseEntity {

    @Version
    @Column(name = "version")
    private int version;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @LastModifiedDate
    @Column(name = "last_modified_at")
    private LocalDateTime lastModifiedAt;

}