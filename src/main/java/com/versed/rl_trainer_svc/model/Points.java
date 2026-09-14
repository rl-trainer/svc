package com.versed.rl_trainer_svc.model;

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
    private Long userId;

    private Long balance;

    private BigDecimal multiplier;

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBalance() {
        return this.balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public BigDecimal getMultiplier() {
        return this.multiplier;
    }

    public void setMultiplier(BigDecimal multiplier) {
        this.multiplier = multiplier;
    }

}
