package com.app.ordering_chat;

import com.app.app_user.AppUser;
import com.app.enums.OrderingStatus;
import com.app.ordering.Ordering;
import com.app.util.Global;
import com.app.vehicle.Vehicle;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import static com.app.util.Global.getDateAndTimeNow;
import static com.app.util.Global.getDateTimeFormatted;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class OrderingChat implements Serializable {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ordering_chat_g")
    @SequenceGenerator(name = "ordering_chat_g", sequenceName = "ordering_chat_seq", allocationSize = 1)
    private Long id;

    private String date = getDateAndTimeNow();

    private String role;

    @Column(length = 5000)
    private String text;

    @ManyToOne
    private Ordering ordering;
    @ManyToOne
    private AppUser owner;

    public OrderingChat(String text, Ordering ordering, AppUser owner) {
        this.text = text;
        this.ordering = ordering;
        this.owner = owner;
        this.role = owner.getRole().getName();
    }

    public String getDate() {
        return getDateTimeFormatted(date);
    }
}