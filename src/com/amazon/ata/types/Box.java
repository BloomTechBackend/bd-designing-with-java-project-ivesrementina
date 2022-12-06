package com.amazon.ata.types;

import java.math.BigDecimal;

public class Box extends Packaging {
    Material box = Material.CORRUGATE;
    private BigDecimal length;
    private BigDecimal width;
    private BigDecimal height;

    public Box(Material material, BigDecimal length, BigDecimal width, BigDecimal height) {
        super(material);
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public boolean canFitItem(Item item) {
        return this.length.compareTo(item.getLength()) > 0 &&
                this.width.compareTo(item.getWidth()) > 0 &&
                this.height.compareTo(item.getHeight()) > 0;
    }

    public BigDecimal getMass() {
        BigDecimal endsArea = length.multiply(width).multiply(BigDecimal.valueOf(2));
        BigDecimal shortSidesArea = length.multiply(height).multiply(BigDecimal.valueOf(2));
        BigDecimal longSidesArea = width.multiply(height).multiply(BigDecimal.valueOf(2));
        return endsArea.add(shortSidesArea).add(longSidesArea);
    }

    public BigDecimal getLength() {
        return length;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public BigDecimal getHeight() {
        return height;
    }
}
