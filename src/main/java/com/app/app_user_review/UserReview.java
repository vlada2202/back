package com.app.app_user_review;

import com.app.app_user.AppUser;
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
public class UserReview implements Serializable {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_review_g")
    @SequenceGenerator(name = "user_review_g", sequenceName = "user_review_seq", allocationSize = 1)
    private Long id;

    private String date = getDateAndTimeNow();
    private int score;

    @Column(length = 5000)
    private String text;

    @ManyToOne
    private AppUser owner;
    @ManyToOne
    private AppUser manager;

    public UserReview(int score, String text) {
        this.score = score;
        this.text = text;
    }

    public UserReview(int score, String text, AppUser owner, AppUser manager) {
        this.score = score;
        this.text = text;
        this.owner = owner;
        this.manager = manager;
    }

    public String getDate() {
        return getDateTimeFormatted(date);
    }
}