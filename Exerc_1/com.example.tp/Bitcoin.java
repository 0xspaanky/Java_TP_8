package com.example.tp;

public class Bitcoin implements PaymentMethod {
    private String walletAddress;
    private double balance;

    public Bitcoin(String walletAddress, double balance) {
        this.walletAddress = walletAddress;
        this.balance = balance;
    }

    @Override
    public boolean pay(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.printf("Bitcoin (%s) : payé %.4f BTC, reste %.4f%n",
                              walletAddress, amount, balance);
            return true;
        }
        System.out.printf("Bitcoin (%s) : fonds insuffisants (%.4f < %.4f)%n",
                          walletAddress, balance, amount);
        return false;
    }

    @Override
    public boolean refund(double amount) {
        balance += amount;
        System.out.printf("Bitcoin (%s) : remboursé %.4f BTC, solde %.4f%n",
                          walletAddress, amount, balance);
        return true;
    }

    @Override
    public String getName() {
        return "Bitcoin[" + walletAddress + "]";
    }
}
