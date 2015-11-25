package com.totu.repository.market;

import com.totu.domain.market.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VehicleRepository extends MongoRepository<Vehicle, String> {

    public List<Vehicle> findByRemoteId(String remoteId);

}
