package enigma.startup;

import io.vertx.core.Vertx;

public class Startup {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new HelloVerticle());
    }
}
