package com.model;

public class Space {
    private Integer actual;
    private final int expected;
    private final boolean isFixed;

    public Space(final int expected, final boolean isFixed) {
        this.expected = expected;
        this.isFixed = isFixed;
        if (isFixed) {
            actual = expected;
        }
    }

    public Integer getActual() {
        return actual;
    }

    public int getExpected() {
        return expected;
    }

    public boolean isFixed() {
        return isFixed;
    }

    public void setActual(final Integer actual) {
        if (isFixed) return;
        this.actual = actual;
    }

    public void clearSpace() {
        setActual(null);
    }
}
