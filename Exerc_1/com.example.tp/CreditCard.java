package com.example.tp;

public class CreditCard implements PaymentMethod {
    private String number, holder;
    private double balance;

    public CreditCard(String number, String holder, double balance) {
        this.number = number;
        this.holder = holder;
        this.balance = balance;
    }

    @Override
    public boolean pay(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.printf("CreditCard (%s) : payé %.2f, reste %.2f%n",
                              holder, amount, balance);
            return true;
        }
        System.out.printf("CreditCard (%s) : fonds insuffisants (%.2f < %.2f)%n",
                          holder, balance, amount);
        return false;
    }

    @Override
    public boolean refund(double amount) {
        balance += amount;
        System.out.printf("CreditCard (%s) : remboursé %.2f, solde %.2f%n",
                          holder, amount, balance);
        return true;
    }

    @Override
    public String getName() {
        return "CreditCard[" + holder + "]";
    }
}
