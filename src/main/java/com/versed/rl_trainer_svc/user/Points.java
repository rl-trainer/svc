package com.versed.rl_trainer_svc.user;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "points")
public class Points {

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    private long userId;

    private long balance;

    private BigDecimal multiplier;

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getBalance() {
        return this.balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public BigDecimal getMultiplier() {
        return this.multiplier;
    }

    public void setMultiplier(BigDecimal multiplier) {
        this.multiplier = multiplier;
    }

}
