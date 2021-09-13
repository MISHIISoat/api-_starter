package com.assets_france.api.unit.shared.helper;

import com.assets_france.api.shared.infrastructure.exception.helper.json.JsonHelperImpl;
import com.assets_france.api.shared.domain.exception.JsonHelperException;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class JsonHelperTest {

    private JsonHelperImpl sut;

    @BeforeEach
    void setup() {
        sut = new JsonHelperImpl();
    }

    @Test
    void readByteValue_can_parse_byte_to_instance_class() throws JsonHelperException {
        assertThat(true).isEqualTo(true);
        String value = "{\"test\":\"value to test\"}";
        var expected = new DummiesClass();
        expected.setTest("value to test");

        var result = sut.readInputStreamValue(new ByteArrayInputStream(value.getBytes()), DummiesClass.class);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void readByteValue_should_throw_exception_when() {
        assertThat(true).isEqualTo(true);
        String value = "{\"noTest\":\"value to test\"}";

        assertThatThrownBy(() -> sut.readInputStreamValue(new ByteArrayInputStream(value.getBytes()), DummiesClass.class))
                .isExactlyInstanceOf(JsonHelperException.class);
    }
}

@Data
class DummiesClass {
    private String test;
}