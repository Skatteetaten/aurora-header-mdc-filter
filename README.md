# Aurora Header MDC Filter

Dette er et filter som automatisk registrerer Korrelasjonsid, Meldingid og Klientid på MDC (Mapped Diagnostic Context).

Det vil også gjøre Korrelasjonsid tilgjengelig i en ThreadLocal tilgjengelig på

    String ske.aurora.filter.logging.RequestKorrelasjon.getId()


## Hvordan bruke fra Spring Boot

Filteret registreres enkelt i en spring boot applikasjon via springs `FilterRegistrationBean`:

    import org.springframework.boot.web.servlet.FilterRegistrationBean;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    
    import ske.aurora.filter.logging.AuroraHeaderFilter;

    @Configuration
    public class AuroraFilterRegistrationBean {
    
        @Bean
        public FilterRegistrationBean auroraHeaderFilter() {
            FilterRegistrationBean registration = new FilterRegistrationBean();
            registration.addUrlPatterns("/*");
            registration.setFilter(new AuroraHeaderFilter());
            return registration;
        }
    }