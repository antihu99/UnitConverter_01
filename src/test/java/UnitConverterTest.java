import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UnitConverterTest {

    @Test
    @DisplayName("TC-B-01: convert meter to feet (delta 1e-5)")
    void tcB01_convertMeterToFeet() {
        UnitConverter converter = new UnitConverter();
        assertEquals(8.20210, converter.convert("meter", 2.5, "feet"), 1e-5);
    }

    @Test
    @DisplayName("TC-A-02: missing colon throws IllegalArgumentException")
    void tcA02_missingColon_throwsIllegalArgumentException() {
        UnitConverter converter = new UnitConverter();
        assertThrows(IllegalArgumentException.class, () -> converter.parse("meter"));
    }
}
