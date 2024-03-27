package com.unitconverter.app;

public class LengthCalculationConverterUnit {
    public enum Unit {
        INCH, CENTIMETER, FOOT, YARD, MILE, KILOMETER;
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

    public LengthCalculationConverterUnit(Unit from, Unit to) {
        double constant = 1;

        switch (from) {
            case INCH:
                if (to == Unit.CENTIMETER) {
                    constant = 2.54;
                }
                break;
            case FOOT:
                if (to == Unit.CENTIMETER) {
                    constant = 30.48;
                }
                break;
            case YARD:
                if (to == Unit.CENTIMETER) {
                    constant = 91.44;
                }
                break;
            case MILE:
                if (to == Unit.KILOMETER) {
                    constant = 1.60934;
                }
                break;
        }
        multiplier = constant;
    }
    public double convert(double input) {
        return input * multiplier;
    }
}