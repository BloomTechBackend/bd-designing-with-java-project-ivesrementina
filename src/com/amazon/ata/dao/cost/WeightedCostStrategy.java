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
        BigDecimal weightedCostPerGram = this.weightedCostPerGram.get(packaging.getMaterial());

        BigDecimal weightedCost = packaging.getMass().multiply(weightedCostPerGram).add(LABOR_COST);

        return new ShipmentCost(shipmentOption, weightedCost);
    }

}


//public class WeightedCostStrategy implements CostStrategy {
//
//    private static final BigDecimal LABOR_COST = BigDecimal.valueOf(0.33);
//    private final Map<Material, BigDecimal> weightedCostPerGram;
//
//    public WeightedCostStrategy(MonetaryCostStrategy monetaryWrapper, CarbonCostStrategy carbonWrapper) {
//        weightedCostPerGram = new HashMap<>();
//
////        BigDecimal weightedCostBox = ((carbonWrapper.getCarbonCostPerGram(Material.CORRUGATE)) // carbon cost of box
////                .multiply(BigDecimal.valueOf(0.20)))  // multiplied by weight 20%
////                .add((monetaryWrapper.getMonetaryCostPerGram(Material.CORRUGATE)) // add monetary cost
////                        .multiply(BigDecimal.valueOf(0.80))); // multiplied by weight 80%
////        weightedCostPerGram.put(Material.CORRUGATE, weightedCostBox);
//
//
//        BigDecimal carbonCostPerGram = carbonWrapper.getCarbonCostPerGram(Material.CORRUGATE);
//        BigDecimal carbonCostWeighted = carbonCostPerGram.multiply(BigDecimal.valueOf(0.20));
//        BigDecimal monetaryCostPerGram = monetaryWrapper.getMonetaryCostPerGram(Material.CORRUGATE);
//        BigDecimal monetaryCostWeighted = monetaryCostPerGram.multiply(BigDecimal.valueOf(0.80));
//        BigDecimal weightedCostBox = carbonCostWeighted.add(monetaryCostWeighted);
//
//        weightedCostPerGram.put(Material.CORRUGATE, weightedCostBox);
//
//
//        BigDecimal weightedCostPolyBag = ((carbonWrapper.getCarbonCostPerGram(Material.LAMINATED_PLASTIC))
//                .multiply(BigDecimal.valueOf(0.20)))
//                .add((monetaryWrapper.getMonetaryCostPerGram(Material.LAMINATED_PLASTIC))
//                        .multiply(BigDecimal.valueOf(0.80)));
//        weightedCostPerGram.put(Material.LAMINATED_PLASTIC, weightedCostPolyBag);
//    }
//
//    @Override
//    public ShipmentCost getCost(ShipmentOption shipmentOption) {
//        Packaging packaging = shipmentOption.getPackaging();
//        BigDecimal weightedCostPerGram = this.weightedCostPerGram.get(packaging.getMaterial());
//
//        BigDecimal weightedCost = packaging.getMass().multiply(weightedCostPerGram).add(LABOR_COST);
//
//        return new ShipmentCost(shipmentOption, weightedCost);
//    }
//}
