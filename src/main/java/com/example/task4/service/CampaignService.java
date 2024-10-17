package com.example.task4.service;

import com.example.task4.DTO.request.CampaignDTO;

import com.example.task4.DTO.request.DonationDTO;
import com.example.task4.model.Campaign;

import com.example.task4.model.User;
import com.example.task4.repository.CampaignRepository;
import com.example.task4.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class CampaignService {
   @Autowired
   private CampaignRepository campaignRepository;

   @Autowired
   private UserRepository userRepository;
   private List<Campaign> campaigns;

   public Campaign createCampaign(CampaignDTO campaignDto, User user) {
      Campaign campaign = new Campaign();
      campaign.setTitle(campaignDto.getTitle());
      campaign.setDescription(campaignDto.getDescription());
      campaign.setGoalAmount(campaignDto.getGoalAmount());
      campaign.setCurrentAmount(0);
      campaign.setCreatedAt(Instant.now());
      campaign.setUser(user);
      return campaignRepository.save(campaign);
   }

   public Campaign updateCampaign(Long id, CampaignDTO campaignDto) {
      Optional<Campaign> optionalCampaign = campaignRepository.findById(id);
      if (optionalCampaign.isPresent()) {
         Campaign campaign = optionalCampaign.get();
         campaign.setTitle(campaignDto.getTitle());
         campaign.setDescription(campaignDto.getDescription());
         campaign.setGoalAmount(campaignDto.getGoalAmount());
         return campaignRepository.save(campaign);
      } else {
         // Handle campaign not found
         return null; // or throw a custom exception
      }
   }

   public void deleteCampaign(Long id) {
      Optional<Campaign> optionalCampaign = campaignRepository.findById(id);
      if (optionalCampaign.isPresent()) {
         campaignRepository.deleteById(id);
      } else {
         // Handle campaign not found
      }
   }

   public Campaign getCampaignById(Long id) {
      return campaignRepository.findById(id).orElse(null);
   }

   public List<Campaign> getAllCampaigns() {
      return campaignRepository.findAll();
   }

   public void donateToCampaign(DonationDTO donationDTO) {
      Optional<Campaign> optionalCampaign = campaignRepository.findById(donationDTO.getCampaignId());
      if (optionalCampaign.isPresent()) {
         Campaign campaign = optionalCampaign.get();
         campaign.setCurrentAmount(campaign.getCurrentAmount() + donationDTO.getAmount());
         campaignRepository.save(campaign);
      } else {
         // Handle the case where the campaign is not found
         throw new IllegalArgumentException("Campaign not found");
      }

   }
}
