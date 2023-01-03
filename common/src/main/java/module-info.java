module enigma.common {
    requires com.google.common;
    requires com.google.guice;
    requires com.google.guice.extensions.throwingproviders;
    requires org.reflections;

    exports enigma.common.mediator;
    exports enigma.common.mediator.exception;
    exports enigma.common.mediator.simple;
}