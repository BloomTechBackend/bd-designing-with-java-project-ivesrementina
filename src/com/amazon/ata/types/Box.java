package com.amazon.ata.types;

import java.math.BigDecimal;
import java.util.Objects;

public class Box extends Packaging {
    private BigDecimal length;
    private BigDecimal width;
    private BigDecimal height;

    /**
     * Box.
     * @param material material
     * @param length length
     * @param width width
     * @param height height
     */
    public Box(Material material, BigDecimal length, BigDecimal width, BigDecimal height) {
        super(material);
        this.length = length;
        this.width = width;
        this.height = height;
    }
    @Override
    public boolean canFitItem(Item item) {
        return this.length.compareTo(item.getLength()) > 0 &&
                this.width.compareTo(item.getWidth()) > 0 &&
                this.height.compareTo(item.getHeight()) > 0;
    }
    @Override
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
    @Override
    public boolean equals(Object o) {
        // Can't be equal to null
        if (o == null) {
            return false;
        }

        // Referentially equal
        if (this == o) {
            return true;
        }

        // Check if it's a different type
        if (getClass() != o.getClass()) {
            return false;
        }

        Box box = (Box) o;
        return Objects.equals(getLength(), box.getLength()) && Objects.equals(getHeight(),
                box.getHeight()) && Objects.equals(getWidth(), box.getWidth());
    }

    /**
     * hashCode.
     * @return Objects.
     */
    public int hashCode() {
        return Objects.hash(getMaterial(), length, width, height);
    }
}
