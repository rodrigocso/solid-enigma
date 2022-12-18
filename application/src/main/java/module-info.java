module enigma.application {
    exports enigma.application.command;
    exports enigma.application.query;
    exports enigma.application.repository;

    requires enigma.domain;

    requires com.google.guice;
}
