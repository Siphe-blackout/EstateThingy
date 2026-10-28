package estateagent;

public class EstateAgent implements IEstateAgent {
    private String agentLocation;
    private String agentName;
    private double propertyPrice;
    private double commissionPercentage;

    public EstateAgent() {
    }

    public EstateAgent(String agentLocation, String agentName,
                       double propertyPrice, double commissionPercentage) {
        setAgentLocation(agentLocation);
        setAgentName(agentName);
        setPropertyPrice(propertyPrice);
        setCommissionPercentage(commissionPercentage);
    }

    public String getAgentLocation() {
        return agentLocation;
    }

    public void setAgentLocation(String agentLocation) {
        this.agentLocation = agentLocation;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public double getPropertyPrice() {
        return propertyPrice;
    }

    public void setPropertyPrice(double propertyPrice) {
        if (!Double.isFinite(propertyPrice) || propertyPrice <= 0) {
            throw new IllegalArgumentException("Property price must be greater than 0.");
        }
        this.propertyPrice = propertyPrice;
    }

    public double getCommissionPercentage() {
        return commissionPercentage;
    }

    public void setCommissionPercentage(double commissionPercentage) {
        if (!Double.isFinite(commissionPercentage)
                || commissionPercentage < 0 || commissionPercentage > 100) {
            throw new IllegalArgumentException("Commission percentage must be between 0 and 100.");
        }
        this.commissionPercentage = commissionPercentage;
    }

    /** Commission for the values currently stored on this object. */
    public double getCommission() {
        return propertyPrice * commissionPercentage / 100.0;
    }

    @Override
    public double calculateCommission(String propertyPrice, String agentCommission) {
        // Parse and validate everything first so a failure leaves the object unchanged.
        double price = parseNumber(propertyPrice, "Property price");
        double percentage = parseNumber(agentCommission, "Commission percentage");

        double oldPrice = this.propertyPrice;
        double oldPercentage = this.commissionPercentage;
        try {
            setPropertyPrice(price);
            setCommissionPercentage(percentage);
        } catch (IllegalArgumentException ex) {
            this.propertyPrice = oldPrice;
            this.commissionPercentage = oldPercentage;
            throw ex;
        }
        return getCommission();
    }

    private static double parseNumber(String text, String fieldName) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " is required.");
        }
        try {
            return Double.parseDouble(text.trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(fieldName + " must be a number (for example 1500000.50).");
        }
    }
}
