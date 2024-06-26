package com.dev7ex.multiworld.api.world;

import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Dev7ex
 * @since 18.06.2023
 */
@Getter(AccessLevel.PUBLIC)
public enum WorldDefaultProperty {

    RECEIVE_ACHIEVEMENTS("receive-achievements", "settings.defaults.receive-achievements"),
    LOAD_AUTO("load-auto", "settings.defaults.load-auto"),
    DIFFICULTY("difficulty", "settings.defaults.difficulty"),
    GAME_MODE("game-mode", "settings.defaults.game-mode"),
    PVP_ENABLED("pvp-enabled", "settings.defaults.pvp-enabled"),
    SPAWN_ANIMALS("spawn-animals", "settings.defaults.spawn-animals"),
    SPAWN_MONSTERS("spawn-monsters", "settings.defaults.spawn-monsters"),
    SPAWN_ENTITIES("spawn-entities", "settings.defaults.spawn-entities"),
    END_PORTAL_ACCESSIBLE("end-portal-accessible", "settings.defaults.end-portal-accessible"),
    NETHER_PORTAL_ACCESSIBLE("nether-portal-accessible", "settings.defaults.nether-portal-accessible"),
    WORLD("world", "settings.defaults.normal-world"),
    END_WORLD("end-world", "settings.defaults.end-world"),
    NETHER_WORLD("nether-world", "settings.defaults.nether-world"),
    NORMAL_WORLD("world", "settings.defaults.normal-world"),
    WHITELIST_ENABLED("whitelist-enabled", "settings.defaults.whitelist-enabled");

    private final String name;
    private final String storagePath;

    WorldDefaultProperty(@NotNull final String name, @NotNull final String storagePath) {
        this.name = name;
        this.storagePath = storagePath;
    }

    public static Optional<WorldDefaultProperty> fromString(final String name) {
        return Arrays.stream(WorldDefaultProperty.values()).filter(worldType -> worldType.name().equalsIgnoreCase(name)).findFirst();
    }

}
