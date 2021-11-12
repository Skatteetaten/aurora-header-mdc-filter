package no.skatteetaten.aurora.filter.logging;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AuroraHeaderFilterTest {

    @Test
    public void assertKorrelasjonsIdIsSetSetsKorrelasjonsIdWhenNotSet() {

        assertNull(RequestKorrelasjon.getId());

        AuroraHeaderFilter.assertKorrelasjonsIdIsSet();

        assertNotNull(RequestKorrelasjon.getId());
    }
}
