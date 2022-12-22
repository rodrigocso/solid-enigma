module enigma.application {
    requires enigma.domain;
    requires com.google.guice;

    exports enigma.application.repository;
    exports enigma.application.exception;
}
