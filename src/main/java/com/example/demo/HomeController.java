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
    public String listTransactions(Model model){
        model.addAttribute("transactions", transactionRepository.findAll());
        return "transactionHistory";
    }

    @GetMapping("/deposit")
    public String depositForm(Model model){
        Transaction depositTransaction = new Transaction();
        depositTransaction.setAction("deposit");

        model.addAttribute("transaction", depositTransaction);
        return "depositform";
    }

    @PostMapping("/processDeposit")
    public String processDepositForm(@Valid Transaction transaction, BindingResult result){
        if (result.hasErrors()){
            return "depositform";
        }
        int currentBalance = getCurrentBalance(transaction.getAccount());
        transaction.setBalance(currentBalance + transaction.getAmount());
        transactionRepository.save(transaction);
        return "redirect:/";
    }

    @GetMapping("/withdrawal")
    public String withdrawalForm(Model model){
        Transaction withdrawalTransaction = new Transaction();
        withdrawalTransaction.setAction("withdrawal");

        model.addAttribute("transaction", withdrawalTransaction);
        return "withdrawalform";
    }

    @PostMapping("/processWithdrawal")
    public String processWithdrawalForm(@Valid Transaction transaction, BindingResult result){
        if (result.hasErrors()){
            return "withdrawalform";
        }
        int currentBalance = getCurrentBalance(transaction.getAccount());
        transaction.setBalance(currentBalance - transaction.getAmount());
        transactionRepository.save(transaction);
        return "redirect:/";
    }

    private int getCurrentBalance(String account){
        Iterable<Transaction> transactions = transactionRepository.findAll();
        int balance = 0;
        for(Transaction t: transactions) {
            if(t.getAccount().equals(account)){
                balance += t.getAction().equals("deposit")? t.getAmount(): -t.getAmount();
            }

        }
        return balance;
    }
}
