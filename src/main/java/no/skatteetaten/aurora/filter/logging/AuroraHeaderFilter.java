package no.skatteetaten.aurora.filter.logging;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class AuroraHeaderFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(AuroraHeaderFilter.class);
    public static final String KORRELASJONS_ID = "Korrelasjonsid";
    private static final List<String> HEADERS = Arrays.asList(KORRELASJONS_ID, "Meldingid", "Klientid");

    public void init(FilterConfig filterConfig) {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        try {
            try {
                copyHeadersFromRequestToMdc((HttpServletRequest) request, HEADERS);
                assertKorrelasjonsIdIsSet();
            } catch (Throwable t) {
                LOG.error("Kunne ikke håndtere Aurora headere", t);
            }

            chain.doFilter(request, response);
        } finally {
            MDC.remove(KORRELASJONS_ID);
            RequestKorrelasjon.cleanup();
        }
    }

    protected static void assertKorrelasjonsIdIsSet() {

        String korrelasjonsId = MDC.get(KORRELASJONS_ID);

        if (korrelasjonsId == null || korrelasjonsId.isEmpty()) {
            korrelasjonsId = UUID.randomUUID().toString();
            LOG.debug("Kunne ikke finne {}. Generert=true {}={}", KORRELASJONS_ID, KORRELASJONS_ID, korrelasjonsId);
            MDC.put(KORRELASJONS_ID, korrelasjonsId);
        }

        RequestKorrelasjon.setId(korrelasjonsId);
    }

    protected static void copyHeadersFromRequestToMdc(HttpServletRequest request, List<String> headers) {

        headers.forEach(headerName -> {
            String headerValue = request.getHeader(headerName);
            if (headerValue != null && !headerValue.trim().isEmpty()) {
                MDC.put(headerName, headerValue);
            }
        });
        LOG.debug("Registrerte headerverdier i MDC");
    }

    public void destroy() {
    }
}
