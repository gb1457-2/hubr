package ru.gb.hubr.entity;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.gb.hubr.entity.common.InfoEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "event_user")
@EntityListeners(AuditingEntityListener.class)
public class EventUser extends InfoEntity {


    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "guid_event", unique = true)
    private String guid_event;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "lifetime_seconds")
    private int lifetimeSeconds;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_event",nullable = false)
    private TypeEvent typeEvent;

    @PrePersist
    protected void onCreate() {
        lifetimeSeconds = typeEvent.getLifetimeSeconds();
        guid_event = guid_event == null ? java.util.UUID.randomUUID().toString(): guid_event;
        deletedAt = deletedAt == null? getCreatedAt().plusSeconds(typeEvent.getLifetimeSeconds()): deletedAt;
    }


}
