package com.app.vehicle;

import com.app.app_user.AppUser;
import com.app.enums.OrderingStatus;
import com.app.ordering.Ordering;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Vehicle implements Serializable {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "vehicle_g")
    @SequenceGenerator(name = "vehicle_g", sequenceName = "vehicle_seq", allocationSize = 1)
    private Long id;

    private String name;
    private String model;
    private int year;
    private int mileage;
    private int capacity;
    private int volume;
    private float price;

    @Column(length = 5000)
    private String description = "";

    @Column(length = 1000)
    private String img = "";

    @ManyToOne
    private AppUser owner;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<Ordering> orderings = new ArrayList<>();

    public Vehicle(String name, String model, int year, int mileage, int capacity, int volume, float price, String description) {
        this.name = name;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.capacity = capacity;
        this.volume = volume;
        this.price = price;
        this.description = description;
    }

    public void update(Vehicle update) {
        this.name = update.getName();
        this.model = update.getModel();
        this.year = update.getYear();
        this.mileage = update.getMileage();
        this.capacity = update.getCapacity();
        this.volume = update.getVolume();
        this.price = update.getPrice();
        this.description = update.getDescription();
    }

    public int getCompleted() {
        return orderings.stream().reduce(0, (i, ordering) -> {
            if (ordering.getStatus() == OrderingStatus.DONE) return i + 1;
            return i;
        }, Integer::sum);
    }

}