import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductCalculationsTest {

    // Unit Test 1
    @Test
    void GetVat_WithValidData_ReturnsCorrectValue() {
        ProductCalculations calc = new ProductCalculations(3500);

        double expected = 525.0;
        double actual = calc.GetVat();

        assertEquals(expected, actual, 0.001);
    }

    // Unit Test 2
    @Test
    void GetTotal_WithValidData_ReturnsCorrectValue() {
        ProductCalculations calc = new ProductCalculations(3500);

        double expected = 4200.0;
        double actual = calc.GetTotal();

        assertEquals(expected, actual, 0.001);
    }
}