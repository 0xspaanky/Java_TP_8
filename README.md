# TP_8 - Interfaces and Extensibility Patterns

This repository contains 2 exercises focusing on interfaces, polymorphism, and the Strategy design pattern.

## Exercises Overview

### Exercise 1: Extensible Payment System
Multi-method payment processing with interface-based design.

**Key Concepts:** Interface contracts, Strategy pattern, Open/Closed Principle

**Interface:**
```java
public interface PaymentMethod {
    boolean pay(double amount);
    boolean refund(double amount);
    String getName();
}
```

**Implementations:** CreditCard, PayPal, Bitcoin

---

### Exercise 2: Extensible Notification System
Multi-channel notification with priority-based sorting.

**Key Concepts:** Priority sorting, Comparator, Method references

**Interface:**
```java
public interface Notification {
    void send(String recipient, String message);
    int getPriority();  // 0=low, 1=normal, 2=high
    String getType();
}
```

**Implementations:** EmailNotification (prio 1), SMSNotification (prio 2), PushNotification (prio 0)

---

## Comparison

| Feature | Exercise 1 | Exercise 2 |
|---------|-----------|-----------|
| Return Type | boolean | void |
| Special Feature | Refund capability | Priority sorting |
| Sorting | None | Comparator-based |
| Use Case | Financial transactions | Alert distribution |

## Key Concepts

### Interface Benefits
- Define contracts without implementation
- Enable polymorphism without inheritance
- Support multiple implementations
- Open/Closed Principle compliance

### Strategy Pattern
```
Context (Manager)
    ↓ uses
Strategy Interface
    ↑ implements
Concrete Strategies (implementations)
```

### Open/Closed Principle
Add new payment methods or notification channels **without modifying** manager classes!

```java
// Add new implementation - no manager changes needed
processor.addMethod(new ApplePay(...));
mgr.addChannel(new SlackNotification(...));
```

## Technologies
- Java 8+
- Interfaces, Strategy Pattern, SOLID Principles

## Compilation
```bash
cd Exerc_X/src
javac com/example/tp/*.java
java com.example.tp.Main
```

## Learning Objectives
- Interface-based design
- Strategy pattern implementation
- Open/Closed Principle
- Dependency Inversion Principle
- Dynamic array management
- Comparator and method references

---

**Course:** Advanced OOP with Java
**Institution:** FST
**Focus:** Interfaces and Extensibility
