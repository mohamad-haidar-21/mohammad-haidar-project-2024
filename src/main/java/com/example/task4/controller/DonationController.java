package com.example.task4.controller;

import com.example.task4.DTO.request.DonationDTO;
import com.example.task4.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class DonationController {
    @Autowired
    private DonationService donationService;

    @PostMapping("/donate")
    public String donate(@ModelAttribute DonationDTO donationDto, Model model) {
        try {
            donationService.donate(donationDto);
            return "redirect:/campaigns/detail/" + donationDto.getCampaignId();  // Redirect back to the campaign details
        } catch (Exception e) {
            model.addAttribute("error", "Error processing donation: " + e.getMessage());
            return "campaignDetail";  // Reload the page with the error message
            }
        }
    }

