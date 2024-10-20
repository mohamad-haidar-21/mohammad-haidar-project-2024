package com.example.task4.repository;

import com.example.task4.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    List<Campaign> findByUserId(Long userId);

    List<Campaign> findByTitleContainingIgnoreCase(String keyword);
}

