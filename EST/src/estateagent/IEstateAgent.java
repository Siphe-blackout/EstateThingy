package estateagent;

public interface IEstateAgent {

    // Parses and validates the inputs, stores them on the object and returns
// the commission (price * percentage / 100).
// Throws IllegalArgumentException if a value is missing, not a number,
// or outside its allowed range.
    double calculateCommission(String propertyPrice, String agentCommission);
}