package com.app.app_user;

import com.app.app_user_review.UserReview;
import com.app.enums.Role;
import com.app.ordering.Ordering;
import com.app.ordering_chat.OrderingChat;
import com.app.vehicle.Vehicle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.app.util.Global.round;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class AppUser implements Serializable {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "app_user_g")
    @SequenceGenerator(name = "app_user_g", sequenceName = "app_user_seq", allocationSize = 1)
    private Long id;

    @Size(min = 1, max = 255, message = "username is required length 1-255")
    @NotEmpty(message = "username is required")
    private String username;
    @Size(min = 1, max = 255, message = "password is required length 1-255")
    @NotEmpty(message = "password is required")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    private String fio = "";
    private String dateReg = "";
    private String contact = "";
    private String city = "";
    private String email = "";

    @Column(length = 1000)
    private String img = "/img/avatar.png";
    @Column(length = 1000)
    private String license = "";

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Vehicle> vehicles = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Ordering> orderings = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<UserReview> reviewsOwner = new ArrayList<>();
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserReview> reviewsManager = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<OrderingChat> chats = new ArrayList<>();

    public AppUser(String username) {
        this.username = username;
    }

    public void update(AppUser user) {

    }

    public void updateManager(ManagerDto manager) {
        this.fio = manager.fio();
        this.dateReg = manager.dateReg();
        this.contact = manager.contact();
        this.city = manager.city();
        this.email = manager.email();
    }

    public float getRating() {
        if (reviewsManager.isEmpty()) return 0f;
        return round(reviewsManager.stream().reduce(0f, (i, review) -> i + review.getScore(), Float::sum) / reviewsManager.size());
    }

    public List<Ordering> getOrderingsManager() {
        List<Ordering> res = new ArrayList<>();
        for (Vehicle vehicle : vehicles) res.addAll(vehicle.getOrderings());
        return res;
    }

    public Integer getOrderingsManagerCompleted() {
        return vehicles.stream().reduce(0, (i, vehicle) -> i + vehicle.getCompleted(), Integer::sum);
    }

}