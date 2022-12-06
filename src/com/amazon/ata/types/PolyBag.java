package com.amazon.ata.types;

import java.math.BigDecimal;

public class PolyBag extends Packaging{

    private BigDecimal volume;

    public PolyBag(Material material, BigDecimal volume) {
        super(material);
        this.volume = volume;
    }

    public boolean canFitItem(Item item) {
        return this.volume.compareTo(item.getHeight().multiply(item.getLength().multiply(item.getWidth()))) > 0;
    }

    public BigDecimal getMass() {
        double mass = Math.ceil(Math.sqrt(volume.doubleValue()) * 0.6);
        return BigDecimal.valueOf(mass);
    }
}
