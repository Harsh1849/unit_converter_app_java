package com.unitconverter.app;

public class WeightCalculationConverterUnit {
    public enum Unit {
        Tonne, Kilogram, Gram, Pound, Ounce;

        public static Unit fromString(String text) {
            if (text != null) {
                for (Unit unit : Unit.values()) {
                    if (text.equalsIgnoreCase(unit.toString())) {
                        return unit;
                    }
                }
            }

            throw new IllegalArgumentException("Cannot find a value for " + text);
        }
    }

    private final double multiplier;

    public WeightCalculationConverterUnit(Unit from, Unit to) {
        double constant = 1;

        switch (from) {
            case Pound:
                if (to == Unit.Kilogram) {
                    constant = 0.453592;
                }
                break;
            case Tonne:
                if (to == Unit.Kilogram) {
                    constant = 907.185;
                }
                break;
            case Ounce:
                if (to == Unit.Gram) {
                    constant = 28.3495;
                }
                break;
        }
        multiplier = constant;

    }

    public double convert(double input) {
        return input * multiplier;
    }
}
