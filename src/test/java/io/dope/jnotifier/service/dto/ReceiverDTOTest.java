package io.dope.jnotifier.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.dope.jnotifier.web.rest.TestUtil;

public class ReceiverDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReceiverDTO.class);
        ReceiverDTO receiverDTO1 = new ReceiverDTO();
        receiverDTO1.setId("id1");
        ReceiverDTO receiverDTO2 = new ReceiverDTO();
        assertThat(receiverDTO1).isNotEqualTo(receiverDTO2);
        receiverDTO2.setId(receiverDTO1.getId());
        assertThat(receiverDTO1).isEqualTo(receiverDTO2);
        receiverDTO2.setId("id2");
        assertThat(receiverDTO1).isNotEqualTo(receiverDTO2);
        receiverDTO1.setId(null);
        assertThat(receiverDTO1).isNotEqualTo(receiverDTO2);
    }
}
