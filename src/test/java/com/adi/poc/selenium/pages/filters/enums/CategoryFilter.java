package com.adi.poc.selenium.pages.filters.enums;

public enum CategoryFilter {
    TOPS("Tops"),
    DRESSES("Dresses"),
    T_SHIRTS("T-shirts"),
    BLOUSES("Blouses"),
    CASUAL("Casual Dresses"),
    EVENING("Evening Dresses"),
    SUMMER("Summer Dresses");

    public final String label;

    CategoryFilter(String label) {
        this.label = label;
    }
}
