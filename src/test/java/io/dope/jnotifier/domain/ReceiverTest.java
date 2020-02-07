package io.dope.jnotifier.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.dope.jnotifier.web.rest.TestUtil;

public class ReceiverTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Receiver.class);
        Receiver receiver1 = new Receiver();
        receiver1.setId("id1");
        Receiver receiver2 = new Receiver();
        receiver2.setId(receiver1.getId());
        assertThat(receiver1).isEqualTo(receiver2);
        receiver2.setId("id2");
        assertThat(receiver1).isNotEqualTo(receiver2);
        receiver1.setId(null);
        assertThat(receiver1).isNotEqualTo(receiver2);
    }
}
