module enigma.application {
    requires com.google.common;
    requires com.google.guice;
    requires enigma.domain;
    requires org.reflections;

    exports enigma.application.exception;
    exports enigma.application.repository;
}
