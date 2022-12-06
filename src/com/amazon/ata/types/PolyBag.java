package com.amazon.ata.types;

import java.math.BigDecimal;

public class PolyBag extends Packaging{

    private BigDecimal volume;

    public PolyBag(Material material, BigDecimal length, BigDecimal width, BigDecimal height) {
        super(material, length, width, height);
    }

    public boolean canFitItem(Item item) {
        return this.getLength().compareTo(item.getLength()) > 0 &&
                this.getWidth().compareTo(item.getWidth()) > 0 &&
                this.getHeight().compareTo(item.getHeight()) > 0;
    }
//    public BigDecimal getMass() {
//        return Math.ceil(Math.sqrt(volume.multiply(BigDecimal.valueOf(0.6));
//    }
//
}
