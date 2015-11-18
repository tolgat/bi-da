package com.totu;

import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.BeforeClass;

/**
 * - Tüm Test class'ları test edilecek class ile aynı package icinde olmalı.
 * Bu şekilde private haricindeki method'lar da test edilebilir.
 * <p>
 * - Test class'ları test edilecek class isminin sonuna Unit Test için Test,
 * integration test için IT yazılarak isimlendirilmelidir.
 * Örnek: TaskService class icin
 * - unit test class'ı: TaskServiceTest
 * - integration test class'ı: TaskServiceIT
 * <p>
 * - Test Method'ları, test edilecek method'un sonuna alt cizgi ve camelCase test konusu yazilmali.
 * Ornek: addToTask_oneInsertActionTest -> "addToTask" orjinal method adi.
 * "oneInsertActionTest" ise ne testi yapildigini gosterir
 * -
 * <p>
 */
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles(profiles = "dev")
@WebAppConfiguration
@IntegrationTest("server.port:0")
@TestExecutionListeners(inheritListeners = false, listeners = {
    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class})
public abstract class IntegrationTestAbstractTestNg extends AbstractTestNGSpringContextTests {

    @BeforeClass
    public void setUp() {

    }


}
