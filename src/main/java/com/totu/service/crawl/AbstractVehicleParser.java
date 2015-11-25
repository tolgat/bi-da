package com.totu.service.crawl;


import com.totu.domain.market.Vehicle;
import com.totu.repository.market.VehicleRepository;
import com.totu.service.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class AbstractVehicleParser {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractVehicleParser.class);

    protected VehicleRepository vehicleRepository;

    protected void saveOne(Vehicle item) {
        List<Vehicle> currentVehicles = vehicleRepository.findByRemoteId(item.getRemoteId());
        boolean found = false;
        for (Vehicle currentVehicle : currentVehicles) {
            if (Utils.equalOrBothNull(currentVehicle.getPrice(), item.getPrice())) {
                LOG.info("Arac fiyatlari ayni, kaydetme! {}.id: {}", new Object[]{currentVehicle.getResourceSite().name(), currentVehicle.getId()});
                found = true;
            } else {
                LOG.info("Arac fiyatlari farkli, kaydet! {}.id: {} -------", new Object[]{currentVehicle.getResourceSite().name(), currentVehicle.getId()});
            }
        }
        if (!found) {
            vehicleRepository.save(item);
        }

    }

}
