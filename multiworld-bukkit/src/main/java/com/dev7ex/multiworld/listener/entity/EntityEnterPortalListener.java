package com.dev7ex.multiworld.listener.entity;

import com.dev7ex.multiworld.MultiWorldPlugin;
import com.dev7ex.multiworld.api.bukkit.MultiWorldBukkitApi;
import com.dev7ex.multiworld.api.bukkit.event.MultiWorldListener;
import com.dev7ex.multiworld.api.bukkit.event.user.WorldUserEnterPortalEvent;
import com.dev7ex.multiworld.api.bukkit.world.BukkitWorldHolder;
import com.dev7ex.multiworld.api.user.WorldUser;
import org.bukkit.Bukkit;
import org.bukkit.PortalType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex, SN-Koarashi
 * @since 02.06.2024
 */
public class EntityEnterPortalListener extends MultiWorldListener {

    public EntityEnterPortalListener(@NotNull final MultiWorldBukkitApi multiWorldApi) {
        super(multiWorldApi);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void handleEntityPortal(final EntityPortalEvent event) {
        if (!super.getConfiguration().isWorldLinkEnabled()) {
            return;
        }

        final BukkitWorldHolder fromWorldHolder = super.getWorldProvider().getWorldHolder(event.getFrom().getWorld().getName()).orElseThrow();

        if (event.getTo() == null) {
            return;
        }

        if (event.getTo().getWorld() == null) {
            return;
        }

        switch (event.getTo().getWorld().getEnvironment()) {
            case NETHER:
                if (fromWorldHolder.getNetherWorldName() == null) {
                    event.setCancelled(true);
                    return;
                }

                if (super.getWorldProvider().getWorldHolder(fromWorldHolder.getNetherWorldName()).isEmpty()) {
                    event.setCancelled(true);
                    return;
                }
                final BukkitWorldHolder netherWorldHolder = super.getWorldProvider().getWorldHolder(fromWorldHolder.getNetherWorldName()).get();

                if (!netherWorldHolder.isLoaded()) {
                    event.setCancelled(true);
                    return;
                }
                // No Player
                event.getTo().setWorld(netherWorldHolder.getWorld());
                break;

            case NORMAL:
                if (fromWorldHolder.getNormalWorldName() == null) {
                    event.setCancelled(true);
                    return;
                }

                if (super.getWorldProvider().getWorldHolder(fromWorldHolder.getNormalWorldName()).isEmpty()) {
                    event.setCancelled(true);
                    return;
                }
                final BukkitWorldHolder normalWorldHolder = super.getWorldProvider().getWorldHolder(fromWorldHolder.getNormalWorldName()).get();

                if (!normalWorldHolder.isLoaded()) {
                    event.setCancelled(true);
                    return;
                }
                // No Player
                event.getTo().setWorld(normalWorldHolder.getWorld());
                break;

            case THE_END:
                if (fromWorldHolder.getEndWorldName() == null) {
                    event.setCancelled(true);
                    return;
                }

                if (super.getWorldProvider().getWorldHolder(fromWorldHolder.getEndWorldName()).isEmpty()) {
                    event.setCancelled(true);
                    return;
                }
                final BukkitWorldHolder endWorldHolder = super.getWorldProvider().getWorldHolder(fromWorldHolder.getEndWorldName()).get();

                if (!endWorldHolder.isLoaded()) {
                    event.setCancelled(true);
                    return;
                }
                // No Player
                event.getTo().setWorld(endWorldHolder.getWorld());
                break;
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void handleEntityEnterBlockPortal(final EntityPortalEvent event) {
        final BukkitWorldHolder fromWorldHolder = super.getWorldProvider().getWorldHolder(event.getFrom().getWorld().getName()).orElseThrow();

        if (event.getTo() == null) {
            return;
        }

        if (event.getTo().getWorld() == null) {
            return;
        }

        switch (event.getTo().getWorld().getEnvironment()) {
            case NETHER:
                if (fromWorldHolder.isNetherPortalAccessible()) {
                    return;
                }
                event.setCancelled(true);
                break;

            case THE_END:
                if (fromWorldHolder.isEndPortalAccessible()) {
                    return;
                }
                event.setCancelled(true);
                break;
        }
    }

}
