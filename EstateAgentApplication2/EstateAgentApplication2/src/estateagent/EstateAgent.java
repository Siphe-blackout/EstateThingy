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
        this.agentLocation = agentLocation;
        this.agentName = agentName;
        this.propertyPrice = propertyPrice;
        this.commissionPercentage = commissionPercentage;
    }

    public String getAgentLocation() { return agentLocation; }
    public void setAgentLocation(String agentLocation) { this.agentLocation = agentLocation; }

    public String getAgentName() { return agentName; }
    public void setAgentName(String agentName) { this.agentName = agentName; }

    public double getPropertyPrice() { return propertyPrice; }
    public void setPropertyPrice(double propertyPrice) { this.propertyPrice = propertyPrice; }

    public double getCommissionPercentage() { return commissionPercentage; }
    public void setCommissionPercentage(double commissionPercentage) {
        this.commissionPercentage = commissionPercentage;
    }

    @Override
    public double CalculateCommission(String propertyPrice, String agentCommission) {
        double price = Double.parseDouble(propertyPrice);
        double percentage = Double.parseDouble(agentCommission);
        return price * percentage / 100.0;
    }
}
