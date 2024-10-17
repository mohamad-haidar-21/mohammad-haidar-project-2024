package com.example.task4.service;

import com.example.task4.DTO.request.DonationDTO;
import com.example.task4.model.Campaign;
import com.example.task4.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DonationService {
    @Autowired
    private CampaignRepository campaignRepository;

    public void donate(DonationDTO donationDto) {
        Optional<Campaign> campaignOpt = campaignRepository.findById(donationDto.getCampaignId());
        if (campaignOpt.isPresent()) {
            Campaign campaign = campaignOpt.get();

            // Adding a check for valid donation amount
            if (donationDto.getAmount() > 0) {
                campaign.setCurrentAmount(campaign.getCurrentAmount() + donationDto.getAmount());
                campaignRepository.save(campaign);
            } else {
                throw new IllegalArgumentException("Donation amount must be greater than zero.");
            }
        } else {
            throw new RuntimeException("Campaign not found");
        }
    }
}
