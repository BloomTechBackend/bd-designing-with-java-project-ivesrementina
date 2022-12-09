package com.amazon.ata.types;

import java.math.BigDecimal;

public class PolyBag extends Packaging {

    private BigDecimal volume;

    public PolyBag(Material material, BigDecimal volume) {
        super(material);
        this.volume = volume;
    }

    @Override
    public boolean canFitItem(Item item) {
        BigDecimal itemHeight = item.getHeight();
        BigDecimal itemLength = item.getLength();
        BigDecimal itemWidth = item.getWidth();
        BigDecimal itemVolume = itemHeight.multiply(itemLength).multiply(itemWidth);
        return volume.compareTo(itemVolume) < 0;
    }

    public BigDecimal getMass() {
        double mass = Math.ceil(Math.sqrt(volume.doubleValue()) * 0.6);
        return BigDecimal.valueOf(mass);
    }
    public BigDecimal getVolume() {
        return volume;
    }
}
