package com.totu.domain.market;

public enum Site {

    HURRIYET_EMLAK(1),
    SAHIBINDEN(2),
    HEPSIBURADA(3);

    private final int id;

    Site(int id) {
        this.id = id;
    }

    public static Site getEnumByValue(int val) {
        for (Site site : values()) {
            if (site.getValue() == (val)) {
                return site;
            }
        }
        return null;
    }

    public int getValue() {
        return id;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
