package ske.aurora.filter.logging;

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

    private static final String KORRELASJONS_ID = "Korrelasjonsid";
    private static final List<String> HEADERS = Arrays.asList(KORRELASJONS_ID, "Meldingid", "Klientid");
    private static final Logger LOG = LoggerFactory.getLogger(AuroraHeaderFilter.class);

    public void init(FilterConfig filterConfig) {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        try {
            copyHeadersFromRequestToMdc((HttpServletRequest) request, HEADERS);
            assertKorrelasjonsIdIsSet();

            chain.doFilter(request, response);
        } catch (Throwable t) {
            LOG.error("Kunne ikke håndtere Aurora headere", t);
        } finally {
            MDC.remove(KORRELASJONS_ID);
            RequestKorrelasjon.cleanup();
        }
    }

    private void assertKorrelasjonsIdIsSet() {

        String korrelasjonsId = MDC.get(KORRELASJONS_ID);

        if (korrelasjonsId == null || korrelasjonsId.isEmpty()) {
            korrelasjonsId = UUID.randomUUID().toString();
            MDC.put(KORRELASJONS_ID, korrelasjonsId);
        }

        RequestKorrelasjon.setId(korrelasjonsId);
    }

    private void copyHeadersFromRequestToMdc(HttpServletRequest request, List<String> headers) {

        headers.forEach(headerName -> {
            String headerValue = request.getHeader(headerName);
            if (headerValue != null && headerValue.trim().isEmpty()) {
                MDC.put(headerName, headerValue);
            }
        });
    }

    public void destroy() {
    }
}
