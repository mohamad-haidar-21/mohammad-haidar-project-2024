package com.example.task4.controller;

import com.example.task4.DTO.request.CampaignDTO;

import com.example.task4.DTO.request.CreateUserDTO;
import com.example.task4.DTO.request.DonationDTO;
import com.example.task4.model.Campaign;
import com.example.task4.model.User;
import com.example.task4.service.CampaignService;
import com.example.task4.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.dgpad.best_practice.DTO.response.ApiResponse;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/campaigns")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;
    @Autowired
    private UserService userService;

    @GetMapping("/create")
    public String showCreateCampaignForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/users/login"; // Redirect to login if user is not found
        }

        model.addAttribute("createCampaignDto", new CampaignDTO());
        return "createCampaign"; // Return the create campaign view
    }

    @PostMapping("/create")
    public String createCampaign(@ModelAttribute CampaignDTO createCampaignDto, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/users/login"; // Redirect to login if user is not found
        }

        try {
            // Assuming your service has a method to create a campaign
            campaignService.createCampaign(createCampaignDto, user); // Pass the user as well
            return "redirect:/campaigns"; // Redirect to the campaigns list after creation
        } catch (Exception e) {
            model.addAttribute("error", "Error creating campaign: " + e.getMessage());
            return "createCampaign"; // Return to the create campaign view with an error message
        }
    }

    @GetMapping
    public String viewCampaigns(Model model) {
        List<Campaign> campaigns = campaignService.getAllCampaigns();
        model.addAttribute("campaigns", campaigns);
        return "viewCampaigns"; // Name of the HTML file for viewing campaigns
    }
//    @GetMapping("/{id}")
//    public String getCampaignById(@PathVariable Long id, Model model) {
//        model.addAttribute("campaign", campaignService.getCampaignById(id));
//        return "campaignDetail"; // Thymeleaf template for campaign details
//    }

    @PostMapping("/donate")
    public String donateToCampaign(@ModelAttribute DonationDTO donationDTO, Model model) {
        try {
            campaignService.donateToCampaign(donationDTO);
            return "redirect:/campaigns/" + donationDTO.getCampaignId(); // Redirect to campaign details
        } catch (Exception e) {
            model.addAttribute("error", "Error processing donation: " + e.getMessage());
            return "error"; // Redirect to an error page or show error
        }
    }
    @GetMapping("/{id}")
    public String campaignDetails(@PathVariable Long id, Model model){
        Optional<Campaign> campaign = Optional.ofNullable(campaignService.getCampaignById(id));
        if(campaign.isPresent()) {
            model.addAttribute("campaign", campaign.get());
            model.addAttribute("donationDto", new DonationDTO());
            return "campaignDetail";
        }
        else{
            model.addAttribute("error", "Campaign not found");
            return "redirect:/campaigns";  // Redirects back to the campaign list
        }
    }
}

