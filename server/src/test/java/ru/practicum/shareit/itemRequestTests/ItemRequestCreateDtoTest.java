package ru.practicum.shareit.itemRequestTests;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.request.ItemRequestCreateDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest()
@ExtendWith(SpringExtension.class)
public class ItemRequestCreateDtoTest {

    @Disabled
    @Test
    public void testDescriptionNotBlank() {
        ItemRequestCreateDto itemRequestCreateDto = new ItemRequestCreateDto();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<ItemRequestCreateDto>> violations = validator.validate(itemRequestCreateDto);
        assertEquals(1, violations.size());

        String expectedMessage = "Заполните описание запроса";
        String actualMessage = violations.iterator().next().getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
