package enigma.application.mediator;

import enigma.application.dto.CreateUserRequestHandler;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class MediatorTests {
    @Test
    public void testScanner() {
        var reflections = new Reflections(new ConfigurationBuilder()
                .forPackage("enigma"));

        Set<Class<?>> classSet = reflections.getSubTypesOf(RequestHandler.class)
                .stream()
                .filter(c -> !Modifier.isAbstract(c.getModifiers()))
                .collect(Collectors.toSet());

        assertThat(classSet.size()).isEqualTo(1);
        assertThat(classSet).allMatch(c -> c.getName().equals(CreateUserRequestHandler.class.getName()));
    }
}
