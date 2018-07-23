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

package com.icloud.infectedduck.detectivegame;

/**
 * Created by InfectedDuck on 15. 9. 12..
 */

import com.icloud.infectedduck.detectivegame.listeners.BasicEventListener;
import com.icloud.infectedduck.detectivegame.listeners.InteractEventListener;
import com.icloud.infectedduck.detectivegame.listeners.SetupListener;
import com.icloud.infectedduck.detectivegame.lists.ItemList;
import com.icloud.infectedduck.detectivegame.lists.NpcList;
import com.icloud.infectedduck.detectivegame.lists.ProfessionList;
import com.icloud.infectedduck.detectivegame.types.ItemBase;
import com.icloud.infectedduck.detectivegame.types.PlayerBase;
import com.icloud.infectedduck.detectivegame.types.ProfessionBase;
import com.icloud.infectedduck.detectivegame.utils.CommandUtil;
import com.icloud.infectedduck.detectivegame.utils.NpcTalkTrait;
import com.icloud.infectedduck.detectivegame.utils.TitleAPI;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DetectiveGame extends JavaPlugin {
    public static DetectiveGame instance;
    public final Logger logger = Logger.getLogger("Minecraft");
    PluginDescriptionFile pdfFile = this.getDescription();

    public final SetupListener setupListener = new SetupListener(this);
    public final BasicEventListener basicListener = new BasicEventListener(this);
    public final InteractEventListener interactListener = new InteractEventListener(this);

    public boolean isEnabled = false;
    public List<Player> acceptedPlayers = new ArrayList<Player>();
    public List<PlayerBase> acceptedPlayerBase = new ArrayList<PlayerBase>();

    public HashMap<Player, ProfessionBase> playerJobs = new HashMap<Player, ProfessionBase>();
    public List<ItemBase> spawnedItem = new ArrayList<ItemBase>();
    public List<ItemBase> spawnedKillerItem = new ArrayList<ItemBase>();

    public ItemList itemList;
    public ProfessionList profList;
    public NpcList npcList;

    public List<Integer> spawnedNpcNum = new ArrayList<Integer>();
    public List<Location> bloodLocation = new ArrayList<Location>();

    public List<Block> fuse = new ArrayList<Block>();
    public List<Block> tapWater = new ArrayList<Block>();
    public Block ballotPlace = null;
    public List<Chest> chests = new ArrayList<Chest>();
    public Player killer = null;

    public ScoreboardManager manager;
    public Scoreboard board;
    public Team teamAll;
    public Team killerTeam;
    public Team npcTeam;
    //public Team invisibleTeam;

    private int killedNum = 0;
    private boolean killCool = false;
    private BukkitRunnable killCooltimeTask;

    @Override
    public void onDisable() {
        this.logger.info("[DetectiveGame] Disabled DetectiveGame[420] v" + pdfFile.getVersion());

        CitizensAPI.getNPCRegistry().deregisterAll();
        /*for(int n: spawnedNpcNum) {
            NpcList.NpcList.get(n-1).getNpc().destroy();
        }*/

        for(World w: Bukkit.getWorlds()) {
            for(Entity e: w.getEntities()) {
                if(e.hasMetadata("DGcorpse")) e.remove();
                if(e.hasMetadata("DGtap")) e.remove();
                if(e.hasMetadata("DGballot")) e.remove();
                if(e.hasMetadata("DGfuse")) e.remove();
                if(e.hasMetadata("DGnpc")) e.remove();
                if(e.hasMetadata("DGmarker")) e.remove();
                if(e.hasMetadata("DGc4")) e.remove();
                if(e.hasMetadata("DGcamera")) e.remove();
            }

            for (Chunk ch : w.getLoadedChunks()) {
                BlockState[] tileEntities = ch.getTileEntities();
                for (BlockState te : tileEntities) {
                    if (te instanceof Sign || te instanceof Skull) {
                        if(te.hasMetadata("DGcorpse")) {
                            te.getBlock().setType(Material.AIR);
                        }
                    }
                }
            }
        }

    }

    @Override
    public void onEnable() {

        if(getServer().getPluginManager().getPlugin("Citizens") == null || !getServer().getPluginManager().getPlugin("Citizens").isEnabled()) {
            getLogger().log(Level.SEVERE, "Citizens 2.0 not found or not enabled");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        instance = this;

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(setupListener, this);

        this.getCommand("dg").setExecutor(new CommandUtil(this));
        itemList = new ItemList();
        profList = new ProfessionList();
        npcList = new NpcList();
        manager = Bukkit.getScoreboardManager();

        net.citizensnpcs.api.CitizensAPI.getTraitFactory()
                .registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(NpcTalkTrait.class).withName("npctrait"));

        this.logger.info("[DetectiveGame] Enabled DetectiveGame[420] v" + pdfFile.getVersion());
    }

    public void addKill() {
        if(++this.killedNum >= 4) {
            for(Player pl: this.acceptedPlayers) {
                TitleAPI.sendTitle(pl, 20, 60, 20, ChatColor.RED + "" + ChatColor.BOLD + "[ 살인마 승리 ]",
                        ChatColor.GRAY + "" + ChatColor.BOLD + "살인마가 4번의 살인을 완료했습니다");
                pl.playSound(pl.getLocation(), "mob.horse.zombie.death", 10000000, 0.8f);
            }
        }
    }
    public int getKillCount() {
        return this.killedNum;
    }
    public void setKillCooltime(int sec) {
        if(this.killCool) {
            this.killCooltimeTask.cancel();
        }
        this.killCool = true;
        this.killCooltimeTask = new BukkitRunnable() {
            public void run() {
                enableKill();
            }
        };
        killCooltimeTask.runTaskLater(this, 20 * sec);
    }
    public boolean isKillCooltime() {
        return this.killCool;
    }
    private void enableKill() {
        this.killCool = false;
    }
}
