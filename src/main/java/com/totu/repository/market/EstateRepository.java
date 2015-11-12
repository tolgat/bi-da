package com.totu.repository.market;

import com.totu.domain.market.Estate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EstateRepository extends MongoRepository<Estate, String> {

    public Estate findByUrl(String url);
    public List<Estate> findBySiteName(String siteName);
}
