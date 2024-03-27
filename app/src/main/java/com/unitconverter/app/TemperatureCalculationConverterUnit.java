package com.unitconverter.app;

public class TemperatureCalculationConverterUnit {
    public enum Unit {
        Celsius, Fahrenheit, Kelvin;
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

    public TemperatureCalculationConverterUnit(Unit from, Unit to) {
        double constant = 1;

        switch (from) {
            case Celsius:
                if (to == Unit.Fahrenheit) {
                    constant = (9/5) + 32 ;
                } else if (to == Unit.Kelvin) {
                    constant = constant + 273.15;
                }
                break;
            case Fahrenheit:
                if (to == Unit.Celsius) {
                    constant = -32.0 / 1.8;
                }
                break;
            case Kelvin:
                if (to == Unit.Celsius) {
                    constant = -273.15;
                }
                break;
        }
        multiplier = constant;
    }

    public double convert(double input) {
        return input * multiplier;
    }
}