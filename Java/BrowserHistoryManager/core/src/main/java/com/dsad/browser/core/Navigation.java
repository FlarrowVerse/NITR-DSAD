package com.dsad.browser.core;

public enum Navigation {
    NEWPAGE,
    BACK,
    FORWARD,
    BOOKMARK,
    NONE;

    /**
     * Mapping from integer values to Navigation variables
     * @param navValue
     * @return Mode
     */
    public static Navigation fromInt(int navValue) {
        switch (navValue) {
            case 1:
                return NEWPAGE;
            case 2:
                return BACK;
            case 3:
                return FORWARD;
            case 4:
                return BOOKMARK;
            default:
                return NONE;
        }
    }

    public static Navigation getReverse(Navigation nav) {
        switch (nav) {
            case BACK:
                return FORWARD;
            case FORWARD:
                return BACK;
            default:
                return NONE;
        }
    }
}
