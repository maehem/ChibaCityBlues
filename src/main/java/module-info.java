module com.maehem.chibacityblues {
    requires java.logging;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.media;
    requires java.base;

    requires com.maehem.abyss;

    exports com.maehem.chibacityblues;
    exports com.maehem.chibacityblues.content.goal;
    exports com.maehem.chibacityblues.content.matrix;
    exports com.maehem.chibacityblues.content.sites;
    exports com.maehem.chibacityblues.content.things.deck;
    exports com.maehem.chibacityblues.content.things.ram;
    exports com.maehem.chibacityblues.content.things.skillchip;
    exports com.maehem.chibacityblues.content.things.software;
    exports com.maehem.chibacityblues.content.vignette;
    exports com.maehem.chibacityblues.content.matrix.sitenode;
    exports com.maehem.chibacityblues.content.matrix.shieldnode;
    exports com.maehem.chibacityblues.content.matrix.attacknode;
}
