package com.amazon.ata;

import com.amazon.ata.dao.PackagingDAO;
import com.amazon.ata.dao.cost.CarbonCostStrategy;
import com.amazon.ata.dao.cost.CostStrategy;
import com.amazon.ata.dao.cost.MonetaryCostStrategy;
import com.amazon.ata.dao.cost.WeightedCostStrategy;
import com.amazon.ata.datastore.PackagingDatastore;
import com.amazon.ata.service.ShipmentService;

public class App {
    /* don't instantiate me */
    private App() {}

    private static PackagingDatastore getPackagingDatastore() {
        return new PackagingDatastore();
    }

    private static PackagingDAO getPackagingDAO() {
        return new PackagingDAO(getPackagingDatastore());
    }

    private static CostStrategy getCostStrategy() {
        return new WeightedCostStrategy(new MonetaryCostStrategy(), new CarbonCostStrategy());
    }

    public static ShipmentService getShipmentService() {
        return new ShipmentService(getPackagingDAO(), getCostStrategy());
    }
}
