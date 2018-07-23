/*
 *  DetectiveGame420 - A bukkit plugin for Detective Games - A bukkit plugin for Detective Games
 *  Copyright (C) 2015  InfectedDuck
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  Contact: minjooon123@naver.com | Skype: minjooon123
 */

package com.icloud.infectedduck.detectivegame.utils;

import com.icloud.infectedduck.detectivegame.DetectiveGame;
import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.trait.Trait;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;

/**
 * Created by InfectedDuck on 2015. 10. 9..
 */
public class NpcTalkTrait extends Trait {

    private int tickCount = 0;

    private DetectiveGame plugin = null;
    private String[] quotes;
    private BukkitTask talkTask;

    public NpcTalkTrait() {
        super("NpcTalkTrait");
        plugin = DetectiveGame.instance;
    }

    public void setQuotes(String[] quotes) {
        this.quotes = quotes;
    }

    //boolean SomeSetting = false;

    // see the 'Persistence API' section
    //@Persist("mysettingname") boolean automaticallyPersistedSetting = false;

    // Here you should load up any values you have previously saved (optional).
    // This does NOT get called when applying the trait for the first time, only loading onto an existing npc at server start.
    // This is called AFTER onAttach so you can load defaults in onAttach and they will be overridden here.
    // This is called BEFORE onSpawn, npc.getBukkitEntity() will return null.
    //public void load(DataKey key) {
    //    SomeSetting = key.getBoolean("SomeSetting", false);
    //}

    // Save settings for this NPC (optional). These values will be persisted to the Citizens saves file
    //public void save(DataKey key) {
     //   key.setBoolean("SomeSetting",SomeSetting);
    //}

    // An example event handler. All traits will be registered automatically as Bukkit Listeners.

    // Called every tick
    @Override
    public void run() {
        if(tickCount<500) {
            tickCount++;
        } else {
            Random random = new Random();
            int rand = random.nextInt(quotes.length);
            for(Player pl: Bukkit.getOnlinePlayers()) {
                if(pl.getLocation().distance(getNPC().getEntity().getLocation()) < 10) {
                    pl.sendMessage(getNPC().getName() + ": " + ChatColor.GRAY + quotes[rand]);
                }
            }
            tickCount = 0;
        }
    }

    //Run code when your trait is attached to a NPC.
    //This is called BEFORE onSpawn, so npc.getBukkitEntity() will return null
    //This would be a good place to load configurable defaults for new NPCs.
    @Override
    public void onAttach() {/*
        plugin.getServer().getLogger().info(npc.getName() + "has been assigned NpcTalkTrait!");
        talkTask = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                int rand = random.nextInt(quotes.length);
                for(Player pl: Bukkit.getOnlinePlayers()) {
                    if(pl.getLocation().distance(getNPC().getEntity().getLocation()) < 15) {
                        pl.sendMessage(getNPC().getName() + ": " + quotes[rand]);
                    }
                }
            }
        }, 20*15, 20*15);*/
    }

    // Run code when the NPC is despawned. This is called before the entity actually despawns so npc.getBukkitEntity() is still valid.
    @Override
    public void onDespawn() {
    }

    //Run code when the NPC is spawned. Note that npc.getBukkitEntity() will be null until this method is called.
    //This is called AFTER onAttach and AFTER Load when the server is started.
    @Override
    public void onSpawn() {

    }

    //run code when the NPC is removed. Use this to tear down any repeating tasks.
    @Override
    public void onRemove() {
        //talkTask.cancel();
    }

}
