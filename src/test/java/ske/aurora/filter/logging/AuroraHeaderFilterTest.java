package ske.aurora.filter.logging;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class AuroraHeaderFilterTest {

    @Test
    public void assertKorrelasjonsIdIsSetSetsKorrelasjonsIdWhenNotSet() {

        assertNull(RequestKorrelasjon.getId());

        AuroraHeaderFilter.assertKorrelasjonsIdIsSet();

        assertNotNull(RequestKorrelasjon.getId());
    }
}
