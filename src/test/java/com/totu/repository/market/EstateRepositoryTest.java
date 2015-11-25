package com.totu.repository.market;

import com.totu.Application;
import com.totu.domain.market.Estate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
@Transactional
public class EstateRepositoryTest {


    @Inject
    private EstateRepository repository;


    @Test
    public void addEstate() {

        Estate estate1 = new Estate();
        estate1.setPrice(300000L);
        estate1.setOda("3+1");
        estate1.setTitle("Küçükbakkalköyde Kelepir Daire");

        repository.save(estate1);

        Estate estate2 = new Estate();
        estate2.setPrice(300000L);
        estate2.setOda("3+1");
        estate2.setTitle("Acıbademde 3+2 Daire");

        repository.save(estate2);

        List<Estate> estates = repository.findAll();

        for (Estate estate : estates) {
            System.out.println(estate);
        }


        assertEquals("2", String.valueOf(estates.size()));
    }


}
