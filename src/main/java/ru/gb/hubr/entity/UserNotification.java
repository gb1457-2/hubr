package ru.gb.hubr.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.gb.hubr.entity.common.CreateDeleteEventEntity;
import ru.gb.hubr.entity.user.AccountUser;
import ru.gb.hubr.enumeration.UserNotificationType;
import javax.persistence.*;



@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_notification")
public class UserNotification extends CreateDeleteEventEntity {

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private AccountUser recipient;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private AccountUser author;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_notification_type")
    private UserNotificationType userNotificationType;

    @Column(name = "message")
    private String message;

    @Column(name = "is_read")
    private boolean isRead;


}

