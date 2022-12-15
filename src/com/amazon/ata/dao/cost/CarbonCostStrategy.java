package com.amazon.ata.dao.cost;

import com.amazon.ata.types.Material;
import com.amazon.ata.types.Packaging;
import com.amazon.ata.types.ShipmentCost;
import com.amazon.ata.types.ShipmentOption;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CarbonCostStrategy implements CostStrategy {

    private final Map<Material, BigDecimal> CarbonCostPerGram;

    public CarbonCostStrategy() {
        CarbonCostPerGram = new HashMap<>();
        CarbonCostPerGram.put(Material.CORRUGATE, BigDecimal.valueOf(0.017));
        CarbonCostPerGram.put(Material.LAMINATED_PLASTIC, BigDecimal.valueOf(0.012));
    }

    @Override
    public ShipmentCost getCost(ShipmentOption shipmentOption) {
        Packaging packaging = shipmentOption.getPackaging();
        BigDecimal materialCost = this.CarbonCostPerGram.get(packaging.getMaterial());
        BigDecimal cost = packaging.getMass().multiply(materialCost);
        return new ShipmentCost(shipmentOption, cost);
    }

    public BigDecimal getCarbonCostPerGram(Material material) {
        return CarbonCostPerGram.get(material);
    }
}


