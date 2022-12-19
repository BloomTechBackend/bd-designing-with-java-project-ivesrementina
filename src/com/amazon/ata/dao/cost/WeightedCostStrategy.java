package com.amazon.ata.dao.cost;

import com.amazon.ata.types.Material;
import com.amazon.ata.types.Packaging;
import com.amazon.ata.types.ShipmentCost;
import com.amazon.ata.types.ShipmentOption;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class WeightedCostStrategy implements CostStrategy {

    private static final BigDecimal LABOR_COST = BigDecimal.valueOf(0.33);
    private final Map<Material, BigDecimal> weightedCostPerGram;

    /**
     * WeightedCostStrategy.
     * @param monetaryWrapper monetaryWrapper
     * @param carbonWrapper carbonWrapper
     */
    public WeightedCostStrategy(MonetaryCostStrategy monetaryWrapper, CarbonCostStrategy carbonWrapper) {
        weightedCostPerGram = new HashMap<>();

        BigDecimal weightedCostBox = ((carbonWrapper.getCarbonCostPerGram(Material.CORRUGATE))
                .multiply(BigDecimal.valueOf(0.20)))
                .add((monetaryWrapper.getMaterialCostPerGram(Material.CORRUGATE))
                        .multiply(BigDecimal.valueOf(0.80)));
        weightedCostPerGram.put(Material.CORRUGATE, weightedCostBox);

        BigDecimal weightedCostPolyBag = ((carbonWrapper.getCarbonCostPerGram(Material.LAMINATED_PLASTIC))
                .multiply(BigDecimal.valueOf(0.20)))
                .add((monetaryWrapper.getMaterialCostPerGram(Material.LAMINATED_PLASTIC))
                        .multiply(BigDecimal.valueOf(0.80)));
        weightedCostPerGram.put(Material.LAMINATED_PLASTIC, weightedCostPolyBag);
    }

    @Override
    public ShipmentCost getCost(ShipmentOption shipmentOption) {
        Packaging packaging = shipmentOption.getPackaging();
        BigDecimal weightCostPerGram = this.weightedCostPerGram.get(packaging.getMaterial());

        BigDecimal weightedCost = packaging.getMass().multiply(weightCostPerGram).add(LABOR_COST);

        return new ShipmentCost(shipmentOption, weightedCost);
    }

}

