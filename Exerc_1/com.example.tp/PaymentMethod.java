package com.example.tp;

/** Contrat pour tout moyen de paiement. */
public interface PaymentMethod {
    boolean pay(double amount);
    boolean refund(double amount);
    String getName();
}
