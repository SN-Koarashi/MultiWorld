package com.dev7ex.multiworld.listener.player;

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
 * @author Dev7ex
 * @since 26.06.2023
 */
public class PlayerEnterPortalListener extends MultiWorldListener {

    public PlayerEnterPortalListener(@NotNull final MultiWorldBukkitApi multiWorldApi) {
        super(multiWorldApi);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void handlePlayerPortal(final PlayerPortalEvent event) {
        if (!super.getConfiguration().isWorldLinkEnabled()) {
            return;
        }

        final BukkitWorldHolder fromWorldHolder = super.getWorldProvider().getWorldHolder(event.getFrom().getWorld().getName()).orElseThrow();
        final Player player = event.getPlayer();
        final WorldUser user = super.getUserProvider().getUser(player.getUniqueId()).orElseThrow();

        if (event.getTo() == null) {
            return;
        }

        if (event.getTo().getWorld() == null) {
            return;
        }

        switch (event.getTo().getWorld().getEnvironment()) {
            case NETHER:
                if (fromWorldHolder.getNetherWorldName() == null) {
                    MultiWorldPlugin.getInstance().getLogger().warning("Player: " + player.getName() + " try to enter the nether-world of " + fromWorldHolder.getName() + " but is null!");
                    event.setCancelled(true);
                    return;
                }

                if (super.getWorldProvider().getWorldHolder(fromWorldHolder.getNetherWorldName()).isEmpty()) {
                    MultiWorldPlugin.getInstance().getLogger().warning("Player: " + player.getName() + " try to enter the nether-world of " + fromWorldHolder.getName() + " but the world not exists!");
                    event.setCancelled(true);
                    return;
                }
                final BukkitWorldHolder netherWorldHolder = super.getWorldProvider().getWorldHolder(fromWorldHolder.getNetherWorldName()).get();

                if (!netherWorldHolder.isLoaded()) {
                    MultiWorldPlugin.getInstance().getLogger().warning("Player: " + player.getName() + " try to enter the nether-world of " + fromWorldHolder.getName() + " but the world is not loaded!");
                    event.setCancelled(true);
                    return;
                }
                Bukkit.getPluginManager().callEvent(new WorldUserEnterPortalEvent(user, fromWorldHolder, netherWorldHolder, PortalType.NETHER));
                event.getTo().setWorld(netherWorldHolder.getWorld());
                break;

            case NORMAL:
                if (fromWorldHolder.getNormalWorldName() == null) {
                    MultiWorldPlugin.getInstance().getLogger().warning("Player: " + player.getName() + " try to enter the normal-world of " + fromWorldHolder.getName() + " but is null!");
                    event.setCancelled(true);
                    return;
                }

                if (super.getWorldProvider().getWorldHolder(fromWorldHolder.getNormalWorldName()).isEmpty()) {
                    MultiWorldPlugin.getInstance().getLogger().warning("Player: " + player.getName() + " try to enter the normal-world of " + fromWorldHolder.getName() + " but the world not exists!");
                    event.setCancelled(true);
                    return;
                }
                final BukkitWorldHolder normalWorldHolder = super.getWorldProvider().getWorldHolder(fromWorldHolder.getNormalWorldName()).get();

                if (!normalWorldHolder.isLoaded()) {
                    MultiWorldPlugin.getInstance().getLogger().warning("Player: " + player.getName() + " try to enter the normal-world of " + fromWorldHolder.getName() + " but the world is not loaded!");
                    event.setCancelled(true);
                    return;
                }
                Bukkit.getPluginManager().callEvent(new WorldUserEnterPortalEvent(user, fromWorldHolder, normalWorldHolder, PortalType.CUSTOM));
                event.getTo().setWorld(normalWorldHolder.getWorld());
                break;

            case THE_END:
                if (fromWorldHolder.getEndWorldName() == null) {
                    MultiWorldPlugin.getInstance().getLogger().warning("Player: " + player.getName() + " try to enter the end-world of " + fromWorldHolder.getName() + " but is null!");
                    event.setCancelled(true);
                    return;
                }

                if (super.getWorldProvider().getWorldHolder(fromWorldHolder.getEndWorldName()).isEmpty()) {
                    MultiWorldPlugin.getInstance().getLogger().warning("Player: " + player.getName() + " try to enter the end-world of " + fromWorldHolder.getName() + " but the world not exists!");
                    event.setCancelled(true);
                    return;
                }
                final BukkitWorldHolder endWorldHolder = super.getWorldProvider().getWorldHolder(fromWorldHolder.getEndWorldName()).get();

                if (!endWorldHolder.isLoaded()) {
                    MultiWorldPlugin.getInstance().getLogger().warning("Player: " + player.getName() + " try to enter the end-world of " + fromWorldHolder.getName() + " but the world is not loaded!");
                    event.setCancelled(true);
                    return;
                }
                Bukkit.getPluginManager().callEvent(new WorldUserEnterPortalEvent(user, fromWorldHolder, endWorldHolder, PortalType.ENDER));
                event.getTo().setWorld(endWorldHolder.getWorld());
                break;
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void handlePlayerEnterBlockPortal(final PlayerPortalEvent event) {
        final BukkitWorldHolder fromWorldHolder = super.getWorldProvider().getWorldHolder(event.getFrom().getWorld().getName()).orElseThrow();
        final Player player = event.getPlayer();

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
                // Bukkit.getPluginManager().callEvent(new WorldUserEnterPortalEvent(user, fromWorldHolder, netherWorldHolder, PortalType.NETHER));
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
                // Bukkit.getPluginManager().callEvent(new WorldUserEnterPortalEvent(user, fromWorldHolder, normalWorldHolder, PortalType.CUSTOM));
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
                // Bukkit.getPluginManager().callEvent(new WorldUserEnterPortalEvent(user, fromWorldHolder, endWorldHolder, PortalType.ENDER));
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
