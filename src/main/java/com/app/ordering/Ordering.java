package com.app.ordering;

import com.app.app_user.AppUser;
import com.app.enums.OrderingStatus;
import com.app.ordering_chat.OrderingChat;
import com.app.util.Global;
import com.app.vehicle.Vehicle;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.app.util.Global.getDateFormatted;
import static com.app.util.Global.round;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Ordering implements Serializable {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ordering_g")
    @SequenceGenerator(name = "ordering_g", sequenceName = "ordering_seq", allocationSize = 1)
    private Long id;

    private String name;
    private String addressStart;
    private String addressEnd;
    private int length;
    private String type;
    private int weight;
    private String date;
    private float price;

    @Enumerated(EnumType.STRING)
    private OrderingStatus status = OrderingStatus.WAITING;

    @ManyToOne
    private AppUser owner;
    @ManyToOne
    private Vehicle vehicle;

    @OneToMany(mappedBy = "ordering", cascade = CascadeType.ALL)
    private List<OrderingChat> chats = new ArrayList<>();

    public Ordering(String name, String addressStart, String addressEnd, int length, String type, int weight, String date, float price) {
        this.name = name;
        this.addressStart = addressStart;
        this.addressEnd = addressEnd;
        this.length = length;
        this.type = type;
        this.weight = weight;
        this.date = date;
        this.price = price;
    }

    public String getDate() {
        return getDateFormatted(date);
    }
}