package com.versed.rl_trainer_svc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.versed.rl_trainer_svc.model.Points;

public interface PointsRepository extends JpaRepository<Points, Long>{

}
