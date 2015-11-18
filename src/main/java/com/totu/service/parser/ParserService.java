package com.totu.service.parser;


import com.totu.repository.market.EstateRepository;
import com.totu.service.parser.estate.Sahibinden;
import org.springframework.stereotype.Service;

import javax.inject.Inject;


@Service
public class ParserService {

    @Inject
    EstateRepository estateRepository;

    public void parseAll() {

        String sahibindenUrl = "http://www.sahibinden.com/satilik-daire?pagingSize=50&address_quarter=23179&address_quarter=23089&address_town=440&address_town=447&address_country=1&address_city=34";
        new Sahibinden(estateRepository).parse(sahibindenUrl);

    }
}
