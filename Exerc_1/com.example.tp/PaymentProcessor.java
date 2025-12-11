package com.example.tp;

public class PaymentProcessor {
    private PaymentMethod[] methods;
    private int count;

    public PaymentProcessor() {
        methods = new PaymentMethod[3];
        count = 0;
    }

    public void addMethod(PaymentMethod m) {
        if (count == methods.length) {
            PaymentMethod[] tmp = new PaymentMethod[methods.length * 2];
            System.arraycopy(methods, 0, tmp, 0, methods.length);
            methods = tmp;
        }
        methods[count++] = m;
    }

    public void processPayments(double amount) {
        System.out.printf("=== Traitement des paiements de %.2f ===%n", amount);

        for (int i = 0; i < count; i++) {
            PaymentMethod m = methods[i];
            System.out.println("-> Via " + m.getName());

            boolean paid = m.pay(amount);
            if (paid) m.refund(amount / 2);

            System.out.println();
        }
    }
}
