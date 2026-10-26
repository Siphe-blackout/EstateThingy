package estateagent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EstateAgentTest {
    private EstateAgent estateAgent;

    @BeforeEach
    void setUp() {
        estateAgent = new EstateAgent();
    }

    @Test
    void calculateCommissionCalculatedSuccessfully() {
        assertEquals(80000.0,
                estateAgent.CalculateCommission("800000", "10"),
                0.001);
    }
}
