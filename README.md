# TP_7 - Interfaces and Extensibility Patterns

This repository contains 2 Java exercises focusing on interfaces, polymorphism, and the Strategy design pattern.

## Repository Structure

```
TP_7/
├── Exerc_1/    # Extensible Payment System
└── Exerc_2/    # Extensible Notification System
```

## Exercises Overview

### Exercise 1: Extensible Payment System
Comprehensive payment processing system with multiple payment methods using interface-based design.

**Key Concepts:**
- Interface as contract definition
- Multi-method payment processing
- Dynamic array management
- Strategy pattern
- Open/Closed Principle

**Interfaces:**
- `PaymentMethod` (pay, refund, getName)

**Classes:**
- `CreditCard` - Card-based payments with balance tracking
- `PayPal` - Online payment with email account
- `Bitcoin` - Cryptocurrency wallet payments
- `PaymentProcessor` - Manager with dynamic array

**Features:**
- Three different payment methods
- Success/failure return values (boolean)
- Automatic refund capability
- Balance validation
- Dynamic payment method array
- Uniform processing interface

**Example:**
```java
PaymentProcessor processor = new PaymentProcessor();
processor.addMethod(new CreditCard("1234-5678-9012-3456",
                                   "Alice Dupont", 500.0));
processor.addMethod(new PayPal("bob@example.com", 200.0));
processor.processPayments(100.0);
// Processes payment and partial refund for each method
```

---

### Exercise 2: Extensible Notification System
Multi-channel notification broadcasting with priority-based sorting and dynamic routing.

**Key Concepts:**
- Interface-based polymorphism
- Priority-based sorting with Comparator
- Method references (Java 8+)
- Multi-channel broadcasting
- Strategy pattern
- Dependency Inversion Principle

**Interfaces:**
- `Notification` (send, getPriority, getType)

**Classes:**
- `EmailNotification` - Email delivery (priority: 1/normal)
- `SMSNotification` - SMS delivery (priority: 2/high)
- `PushNotification` - Push notification (priority: 0/low)
- `NotificationManager` - Manager with sorting

**Features:**
- Three notification channels with different priorities
- Automatic priority-based sorting (high→low)
- Broadcast to multiple channels
- Dynamic channel array
- Comparator with method references
- Uniform notification interface

**Example:**
```java
NotificationManager mgr = new NotificationManager();
mgr.addChannel(new EmailNotification("no-reply@monapp.com"));
mgr.addChannel(new SMSNotification("+33123456789"));
mgr.addChannel(new PushNotification("com.monapp.id"));
mgr.broadcast("user@example.com", "Votre commande est expédiée.");
// Sends via SMS (prio 2), Email (prio 1), Push (prio 0)
```

---

## Comparison Table

| Feature | Exercise 1 (Payment) | Exercise 2 (Notification) |
|---------|---------------------|--------------------------|
| Pattern | Strategy | Strategy |
| Interface Methods | 3 methods | 3 methods |
| Return Type | boolean | void |
| Implementations | 3 classes | 3 classes |
| Sorting | None | Priority-based |
| Special Feature | Refund capability | Multi-priority broadcast |
| Key Algorithm | Balance validation | Comparator sorting |
| Use Case | Financial transactions | Alert distribution |
| Complexity | Moderate | Moderate |

## Core Concepts

### Interfaces in Java

**Definition:** Contracts that define behavior without implementation.

**Purpose:**
1. Define what classes must do, not how
2. Enable multiple implementations
3. Support polymorphism without inheritance
4. Enforce consistency across implementations

**Example from Both Exercises:**
```java
// Exercise 1
public interface PaymentMethod {
    boolean pay(double amount);
    boolean refund(double amount);
    String getName();
}

// Exercise 2
public interface Notification {
    void send(String recipient, String message);
    int getPriority();
    String getType();
}
```

**Key Characteristics:**
- All methods are public and abstract (before Java 8)
- No instance fields (only constants)
- No constructors
- Can be implemented by any class
- Support multiple inheritance

### Strategy Design Pattern

**Definition:** Define a family of algorithms, encapsulate each one, and make them interchangeable.

**Components:**
1. **Strategy Interface:** Defines common operations
2. **Concrete Strategies:** Different implementations
3. **Context:** Uses strategies through interface

**Exercise 1 Implementation:**
```
PaymentProcessor (Context)
       ↓ uses
PaymentMethod (Strategy Interface)
       ↑ implements
  ┌────┴────┬────────┐
CreditCard  PayPal  Bitcoin
```

**Exercise 2 Implementation:**
```
NotificationManager (Context)
       ↓ uses
Notification (Strategy Interface)
       ↑ implements
  ┌────┴─────┬──────────┐
Email       SMS       Push
```

**Benefits:**
- Algorithms interchangeable at runtime
- Easy to add new strategies
- Context independent of implementations
- Follows Open/Closed Principle

### Open/Closed Principle (SOLID)

**Definition:** Software entities should be open for extension, closed for modification.

**In These Exercises:**

**Adding New Payment Method (No Manager Changes!):**
```java
public class ApplePay implements PaymentMethod {
    @Override
    public boolean pay(double amount) { ... }

    @Override
    public boolean refund(double amount) { ... }

    @Override
    public String getName() { return "ApplePay"; }
}

// No changes to PaymentProcessor needed!
processor.addMethod(new ApplePay(...));
```

**Adding New Notification Channel (No Manager Changes!):**
```java
public class SlackNotification implements Notification {
    @Override
    public void send(String r, String m) { ... }

    @Override
    public int getPriority() { return 1; }

    @Override
    public String getType() { return "Slack"; }
}

// No changes to NotificationManager needed!
mgr.addChannel(new SlackNotification(...));
```

**Key Principle:** Manager classes depend on interfaces, not concrete implementations!

### Dependency Inversion Principle (SOLID)

**Definition:** Depend on abstractions, not concretions.

**Implementation in Both Exercises:**
```java
// High-level modules depend on abstractions
public class PaymentProcessor {
    private PaymentMethod[] methods;  // Interface, not CreditCard[]!
}

public class NotificationManager {
    private Notification[] channels;  // Interface, not EmailNotification[]!
}
```

**Benefits:**
- Low coupling between modules
- Easy to swap implementations
- Simplifies testing (mock implementations)
- Enhances flexibility

### Polymorphism Through Interfaces

**Example from Exercise 1:**
```java
PaymentMethod[] methods = {
    new CreditCard("1234", "Alice", 500),
    new PayPal("bob@example.com", 200),
    new Bitcoin("1A2b3C", 0.10)
};

// Uniform interface - different behaviors
for (PaymentMethod m : methods) {
    boolean success = m.pay(100.0);  // Polymorphic call
    System.out.println(m.getName() + ": " + success);
}
```

**Example from Exercise 2:**
```java
Notification[] channels = {
    new EmailNotification("no-reply@app.com"),
    new SMSNotification("+33123456789"),
    new PushNotification("com.app.id")
};

// Uniform interface - different priorities
for (Notification n : channels) {
    System.out.println(n.getType() + " priority: " +
                       n.getPriority());
    n.send("user@example.com", "Test message");
}
```

### Dynamic Array Management (Both Exercises)

**Pattern Used:**
```java
private Item[] items = new Item[3];  // Initial capacity
private int count = 0;

public void add(Item item) {
    if (count == items.length) {
        // Double capacity
        Item[] tmp = new Item[items.length * 2];
        System.arraycopy(items, 0, tmp, 0, items.length);
        items = tmp;
    }
    items[count++] = item;
}
```

**Used in:**
- Exercise 1: PaymentProcessor (payment methods array)
- Exercise 2: NotificationManager (channels array)

**Benefits:**
- No fixed size limit
- Automatic expansion
- Efficient doubling strategy
- Stores heterogeneous objects via interface

## Advanced Features

### Priority-Based Sorting (Exercise 2)

**Using Java 8 Comparator:**
```java
// Create copy and sort by priority (descending)
Notification[] copy = Arrays.copyOf(channels, count);
Arrays.sort(copy,
    Comparator.comparingInt(Notification::getPriority).reversed());
```

**How it works:**
1. `Comparator.comparingInt()` - Creates comparator from int function
2. `Notification::getPriority` - Method reference (Java 8 feature)
3. `.reversed()` - Inverts order (high→low priority)

**Equivalent pre-Java 8:**
```java
Arrays.sort(copy, new Comparator<Notification>() {
    @Override
    public int compare(Notification n1, Notification n2) {
        return Integer.compare(n2.getPriority(), n1.getPriority());
    }
});
```

### Return Values and Error Handling

**Exercise 1: Boolean Returns**
```java
public interface PaymentMethod {
    boolean pay(double amount);  // true = success, false = failure
}

// Usage
if (method.pay(100.0)) {
    System.out.println("Payment successful");
} else {
    System.out.println("Payment failed");
}
```

**Exercise 2: Void Returns**
```java
public interface Notification {
    void send(String recipient, String message);  // No return
}

// Side effects only (printing)
notification.send("user@example.com", "Message");
```

## Interface vs. Abstract Class

| Feature | Interface | Abstract Class |
|---------|-----------|----------------|
| Multiple inheritance | Yes | No |
| Fields | Only constants | Any fields |
| Constructors | No | Yes |
| Method implementation | None (pre-Java 8) | Partial allowed |
| When to use | Pure contract | Partial implementation |
| Access modifiers | All public | Any modifier |
| Example | PaymentMethod, Notification | Employe, FsItem (TP_6) |

**These exercises use interfaces because:**
- No common implementation needed
- Each class is independent
- Multiple inheritance possible
- Pure contract definition
- Maximum flexibility

## Technologies
- **Language:** Java 8+
- **Concepts:** Interfaces, Strategy Pattern, SOLID Principles
- **Features:** Method references, Comparator, Dynamic arrays

## Compilation & Execution

Both exercises use the same structure:

```bash
# Navigate to exercise directory
cd Exerc_X

# Compile
cd src
javac com/example/tp/*.java

# Run
java com.example.tp.Main
```

## Learning Objectives

### Fundamental Concepts
- Understanding interfaces as contracts
- Interface vs. abstract class decision
- Polymorphism without inheritance
- Multiple implementations of same interface

### Intermediate Concepts
- Strategy design pattern
- Open/Closed Principle
- Dependency Inversion Principle
- Dynamic array management
- Boolean vs void return types

### Advanced Concepts
- Priority-based sorting with Comparator
- Method references (Java 8+)
- Lambda expressions
- Interface extensibility
- Decoupling strategies

## When to Use Interfaces

### Use Interfaces When:
1. **Multiple implementations expected**
   - Payment: Credit card, PayPal, Bitcoin
   - Notification: Email, SMS, Push

2. **No shared implementation**
   - Each payment method is completely different
   - Each notification channel uses different protocols

3. **Multiple inheritance needed**
   - Class can implement multiple interfaces
   - Cannot extend multiple abstract classes

4. **Pure contract definition**
   - Define what, not how
   - Leave all implementation to classes

### Real-World Use Cases:

**Payment Systems:**
- E-commerce platforms
- Point-of-sale systems
- Subscription services
- Financial applications

**Notification Systems:**
- Alert platforms
- Messaging services
- Event notification systems
- Multi-channel campaigns

## Best Practices

### 1. Always Use @Override
```java
@Override
public boolean pay(double amount) {
    // Compiler catches signature errors
}
```

### 2. Keep Interfaces Focused
```java
// Good - single responsibility
public interface PaymentMethod {
    boolean pay(double amount);
    boolean refund(double amount);
    String getName();
}

// Bad - too many responsibilities
public interface PaymentMethod {
    boolean pay(...);
    void logTransaction(...);
    void sendReceipt(...);
    void updateDatabase(...);
}
```

### 3. Name Interfaces Clearly
```java
// Good - describes capability
public interface PaymentMethod { ... }
public interface Notification { ... }

// Avoid - vague or technical
public interface IPayment { ... }      // Don't prefix with 'I'
public interface PaymentImpl { ... }   // 'Impl' suggests implementation
```

### 4. Document Interface Contracts
```java
public interface PaymentMethod {
    /**
     * Attempts to process payment.
     * @param amount Amount to charge (must be positive)
     * @return true if payment succeeds, false otherwise
     */
    boolean pay(double amount);
}
```

### 5. Design for Extension
```java
// Easy to extend with new implementations
public class GooglePay implements PaymentMethod { ... }
public class WhatsAppNotification implements Notification { ... }

// No changes to existing code!
```

## Common Mistakes to Avoid

### 1. Trying to Instantiate Interface
```java
// Compile error!
PaymentMethod payment = new PaymentMethod();

// Correct
PaymentMethod payment = new CreditCard(...);
```

### 2. Not Implementing All Methods
```java
// Compile error!
public class CreditCard implements PaymentMethod {
    public boolean pay(double amount) { ... }
    // Missing refund() and getName()!
}
```

### 3. Coupling to Concrete Classes
```java
// Bad - defeats purpose of interface
public class PaymentProcessor {
    private CreditCard card;
    private PayPal paypal;
}

// Good - uses interface
public class PaymentProcessor {
    private PaymentMethod[] methods;
}
```

### 4. Forgetting Method References
```java
// Verbose
Arrays.sort(copy, new Comparator<Notification>() {
    public int compare(Notification n1, Notification n2) {
        return Integer.compare(n1.getPriority(), n2.getPriority());
    }
});

// Concise (Java 8+)
Arrays.sort(copy,
    Comparator.comparingInt(Notification::getPriority));
```

### 5. Modifying Instead of Copying
```java
// Bad - modifies original array order
Arrays.sort(channels, ...);

// Good - preserves original order
Notification[] copy = Arrays.copyOf(channels, count);
Arrays.sort(copy, ...);
```

## Verification Tips

For both exercises:
- [ ] Cannot instantiate interfaces directly
- [ ] All interface methods implemented
- [ ] @Override annotation used
- [ ] Polymorphism works correctly
- [ ] Dynamic arrays expand when needed
- [ ] Can add new implementations without modifying managers
- [ ] Output matches specifications
- [ ] No compile errors

## Extensions and Projects

### Exercise 1 Extensions
- Transaction fees per payment type
- Exception handling for insufficient funds
- Transaction logging and history
- Multi-currency support
- Payment reversals and disputes
- Scheduled payments

### Exercise 2 Extensions
- Slack/Discord/Telegram channels
- Rate limiting per channel
- Message templates with variables
- Asynchronous sending (CompletableFuture)
- Notification filtering by recipient type
- Delivery status tracking

### Combined Project Ideas

**1. E-Commerce Platform:**
- Use payment system for checkout
- Use notification system for order updates
- Track payment success → send confirmation
- Handle payment failures → send alerts

**2. Subscription Service:**
- Process recurring payments (Exercise 1)
- Send renewal notifications (Exercise 2)
- Priority alerts for payment failures
- Multi-channel payment reminders

**3. Event Management System:**
- Payment for event tickets
- Multi-channel event reminders
- Priority notifications for urgent updates
- Payment refunds for cancellations

## Documentation

Each exercise directory contains:
- `README.md`: Detailed specifications and examples
- `subject.txt`: Original French requirements
- Java source files: Complete implementations
- `image.png`: UML or concept diagrams

---

**Course:** Advanced Object-Oriented Programming with Java
**Institution:** FST
**Focus:** Interfaces and Extensibility Patterns
**Difficulty:** Intermediate

## Key Takeaways

1. **Interfaces** define contracts without imposing implementation details
2. **Strategy Pattern** enables runtime algorithm selection through polymorphism
3. **Open/Closed Principle** allows extension without modification
4. **Dependency Inversion** decouples high-level from low-level modules
5. **Dynamic arrays** provide flexibility without collection frameworks
6. **Method references** (Java 8+) create cleaner, more maintainable code
7. **Priority-based sorting** demonstrates Comparator power and flexibility

## Related TPs

- **TP_6**: Abstract classes and design patterns (Template Method, Composite)
- **TP_5**: Polymorphism and generics (dynamic binding, bounded wildcards)
- **TP_4**: Inheritance and polymorphism (multi-level hierarchies)
- **TP_3**: OOP fundamentals (associations, encapsulation)

---

**Next Steps:** Consider combining interface-based design with generics (TP_5) or abstract classes (TP_6) for more sophisticated architectures!
