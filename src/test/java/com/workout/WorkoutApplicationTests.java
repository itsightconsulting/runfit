package com.workout;

import com.itsight.configuration.WebMvcConfiguration;
import com.itsight.domain.dto.TrainerFichaDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@ContextConfiguration(classes = {WebMvcConfiguration.class})
@SpringBootTest
public class WorkoutApplicationTests {

    @DisplayName("Test Validator JSR 380")
    @Test
    public void contextLoads() {
        Assertions.assertEquals("Hello JUnit 5", "Hello JUnit 5");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        TrainerFichaDTO trainer = new TrainerFichaDTO();
        trainer.setCorreo("peterpaul.0194@gmail.com");
        Set<ConstraintViolation<TrainerFichaDTO>> violations = validator.validate(trainer);
        violations.forEach((e)-> System.out.println(e.toString()));
        Assertions.assertTrue(violations.isEmpty());
    }
}
