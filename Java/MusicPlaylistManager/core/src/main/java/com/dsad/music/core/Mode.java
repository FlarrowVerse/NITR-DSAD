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
}
