package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class HomeController {
    @Autowired
    TransactionRepository transactionRepository;

    @RequestMapping("/")
    public String listCourses(Model model){
        model.addAttribute("transactions", transactionRepository.findAll());
        return "transactionHistory";
    }

    @GetMapping("/deposit")
    public String depositForm(Model model){
        model.addAttribute("transactions", new Transaction());
        return "depositform";
    }

    @PostMapping("/process")
    public String processDepositForm(@Valid Transaction transaction, BindingResult result){
        if (result.hasErrors()){
            return "depositform";
        }
        transactionRepository.save(transaction);
        return "redirect:/";
    }

    @GetMapping("/withdrawal")
    public String withdrawalForm(Model model){
        model.addAttribute("transactions", new Transaction());
        return "withdrawalform";
    }


    @PostMapping("/process")
    public String processWithdrawalForm(@Valid Transaction transaction, BindingResult result){
        if (result.hasErrors()){
            return "withdrawalform";
        }
        transactionRepository.save(transaction);
        return "redirect:/";
    }






}