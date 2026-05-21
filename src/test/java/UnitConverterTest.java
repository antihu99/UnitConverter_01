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
    @DisplayName("TC-B-02: convert meter to yard (delta 1e-5)")
    void tcB02_convertMeterToYard() {
        UnitConverter converter = new UnitConverter();
        assertEquals(1.09361, converter.convert("meter", 1.0, "yard"), 1e-5);
    }

    @Test
    @DisplayName("TC-B-03: convert feet to meter reverse (delta 1e-5)")
    void tcB03_convertFeetToMeter() {
        UnitConverter converter = new UnitConverter();
        assertEquals(0.30480, converter.convert("feet", 1.0, "meter"), 1e-5);
    }

    @Test
    @DisplayName("TC-A-02: missing colon throws IllegalArgumentException")
    void tcA02_missingColon_throwsIllegalArgumentException() {
        UnitConverter converter = new UnitConverter();
        assertThrows(IllegalArgumentException.class, () -> converter.parse("meter"));
    }

    @Test
    @DisplayName("TC-A-03: negative value throws IllegalArgumentException")
    void tcA03_negativeValue_throwsIllegalArgumentException() {
        UnitConverter converter = new UnitConverter();
        assertThrows(IllegalArgumentException.class, () -> converter.parse("meter:-1.0"));
    }

    @Test
    @DisplayName("TC-A-04: unknown unit throws IllegalArgumentException")
    void tcA04_unknownUnit_throwsIllegalArgumentException() {
        UnitConverter converter = new UnitConverter();
        assertThrows(IllegalArgumentException.class, () -> converter.parse("parsec:1.0"));
    }
}
