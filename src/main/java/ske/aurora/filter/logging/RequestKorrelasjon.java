package ske.aurora.filter.logging;

public class RequestKorrelasjon {
    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    private RequestKorrelasjon() {
    }

    public static String getId() {
        return THREAD_LOCAL.get();
    }

    public static void setId(String correlationId) {
        THREAD_LOCAL.set(correlationId);
    }

    public static void cleanup() {
        THREAD_LOCAL.remove();
    }
}
