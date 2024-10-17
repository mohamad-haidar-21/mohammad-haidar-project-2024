package com.example.task4.DTO.request;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampaignDTO {
    private Long id;
    private String title;
    private String description;
    private double goalAmount;
    private double currentAmount;
    private Instant createdAt;
    private Long userId; // Reference to the user who created the campaign
}

