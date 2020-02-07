package io.dope.jnotifier.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ReceiverMapperTest {

    private ReceiverMapper receiverMapper;

    @BeforeEach
    public void setUp() {
        receiverMapper = new ReceiverMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(receiverMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(receiverMapper.fromId(null)).isNull();
    }
}
