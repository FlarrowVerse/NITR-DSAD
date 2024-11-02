package com.dsad.music.core;

public enum Mode {
    NORMAL,
    SHUFFLE,
    REPEAT,
    SOLOLOOP,
    REVERSE;

    /**
     * Mapping from integer values to Mode variables
     * @param modeValue
     * @return Mode
     */
    public static Mode fromInt(int modeValue) {
        switch (modeValue) {
            case 1:
                return NORMAL;
            case 2:
                return SHUFFLE;
            case 3:
                return REPEAT;
            case 4:
                return SOLOLOOP;
            case 5:
                return REVERSE;
            default:
                throw new IllegalArgumentException("Invalid mode value: " + modeValue);
        }
    }

    /**
     * Prints the string value
     * @param mode
     * @return
     */
    public static String toString(Mode mode) {
        switch (mode) {
            case NORMAL:
                return "Normal";
            case SHUFFLE:
                return "Shuffle";
            case REPEAT:
                return "Repeat";
            case SOLOLOOP:
                return "Single Loop";
            case REVERSE:
                return "Reverse";
            default:
                throw new IllegalArgumentException("Invalid mode value: " + mode);
        }
    }
}
