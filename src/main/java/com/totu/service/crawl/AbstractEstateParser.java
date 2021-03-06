package com.totu.service.crawl;


import com.totu.domain.market.Estate;
import com.totu.repository.market.EstateRepository;
import com.totu.service.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class AbstractEstateParser {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractEstateParser.class);

    protected EstateRepository estateRepository;

    protected void saveOne(Estate item) {
        List<Estate> currentEstates = estateRepository.findByRemoteId(item.getRemoteId());
        boolean found = false;
        for (Estate currentEstate : currentEstates) {
            if (Utils.equalOrBothNull(currentEstate.getPrice(), item.getPrice())) {
                LOG.info("emlak fiyatlari ayni, kaydetme! {}.id: {}", new Object[]{currentEstate.getResourceSite().name(), currentEstate.getId()});
                found = true;
            } else {
                LOG.info("Emlak fiyatlari farkli, kaydet! {}.id: {} -------", new Object[]{currentEstate.getResourceSite().name(), currentEstate.getId()});
            }
        }
        if (!found) {
            estateRepository.save(item);
        }

    }

}
