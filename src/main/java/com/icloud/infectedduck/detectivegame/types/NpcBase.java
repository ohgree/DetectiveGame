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

package com.icloud.infectedduck.detectivegame.types;

import com.icloud.infectedduck.detectivegame.DetectiveGame;
import com.icloud.infectedduck.detectivegame.lists.NpcList;
import com.icloud.infectedduck.detectivegame.utils.ColorArmour;
import com.icloud.infectedduck.detectivegame.utils.NpcTalkTrait;
import com.sun.tools.javac.jvm.Items;
import net.citizensnpcs.Citizens;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitFactory;
import net.citizensnpcs.npc.CitizensTraitFactory;
import net.citizensnpcs.trait.LookClose;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.EulerAngle;

import java.util.*;

/**
 * Created by InfectedDuck on 15. 9. 20..
 */
abstract public class NpcBase {
    //private Vector projectileTrace;
    private boolean isDead;
    private ItemBase killItem;
    private String npcName;
    private String[] npcQuotes;
    private NPCRegistry npcReg;
    private NPC npc = null;
    private List<ItemStack> npcArmour = new ArrayList<ItemStack>();
    private String npcSkin;
    private int npcSerial;
    private Location npcLocation;


    private void spawnCorpse(Location loc) {
        /**
         * Use CorpseGen Class -> 귀찮아
         *
         * FACE_UP
         * summon ArmorStand ~ ~-1.7 ~ {Rotation:[268.47f],
         * Pose:{Body:[275.43f,359.7f,0.0f],
         * Head:[282.74f,7.16f,37.39f],
         * LeftLeg:[-1.0f,0.0f,-1.0f],
         * RightLeg:[1.0f,0.0f,1.0f],
         * LeftArm:[264.46f,0.0f,-10.0f],
         * RightArm:[290.27f,358.46f,10.0f]}}
         *
         * summon ArmorStand ~0.62 ~-1.17 ~ {Rotation:[-89.79f],
         *
         * Pose:{Body:[360.0f,360.0f,360.0f],
         * LeftLeg:[275.83f,347.18f,271.65f],
         * RightLeg:[269.05f,0.0f,360.0f],
         * LeftArm:[-10.0f,0.0f,-10.0f],
         * RightArm:[334.34f,137.07f,264.47f]}}
         */

        Location armorLoc = loc.clone();
        armorLoc.setYaw(0f);
        armorLoc.setPitch(0f);

        ArmorStand upperArmor;
        ArmorStand lowerArmor;

        int temp = (new Random()).nextInt(3);

        if(temp == 0) {
            upperArmor = (ArmorStand) loc.getWorld().spawnEntity(armorLoc.clone().add(0, -1.15, 0), EntityType.ARMOR_STAND);
            lowerArmor = (ArmorStand) loc.getWorld().spawnEntity(armorLoc.clone().add(0, -0.57, 0.62), EntityType.ARMOR_STAND);

            upperArmor.setBodyPose(new EulerAngle(Math.toRadians(275.43f), Math.toRadians(359.7f), 0.0f));
            upperArmor.setHeadPose(new EulerAngle(Math.toRadians(282.74f), Math.toRadians(7.16f), Math.toRadians(37.39f)));
            upperArmor.setLeftArmPose(new EulerAngle(Math.toRadians(264.46f), Math.toRadians(0.0f), Math.toRadians(-10.0f)));
            upperArmor.setRightArmPose(new EulerAngle(Math.toRadians(290.27f), Math.toRadians(358.46f), Math.toRadians(10.0f)));
            upperArmor.setLeftLegPose(new EulerAngle(Math.toRadians(-1.0f), Math.toRadians(0.0f), Math.toRadians(-1.0f)));
            upperArmor.setRightLegPose(new EulerAngle(Math.toRadians(1.0f), Math.toRadians(0.0f), Math.toRadians(1.0f)));

            lowerArmor.setBodyPose(new EulerAngle(Math.toRadians(360.0f), Math.toRadians(360.0f), Math.toRadians(360.0f)));
            lowerArmor.setHeadPose(new EulerAngle(0f, 0f, 0f));
            lowerArmor.setLeftArmPose(new EulerAngle(Math.toRadians(-10.0f), Math.toRadians(0.0f), Math.toRadians(-10.0f)));
            lowerArmor.setRightArmPose(new EulerAngle(Math.toRadians(334.34f), Math.toRadians(137.07f), Math.toRadians(264.47f)));
            lowerArmor.setLeftLegPose(new EulerAngle(Math.toRadians(275.83f), Math.toRadians(347.18f), Math.toRadians(271.65f)));
            lowerArmor.setRightLegPose(new EulerAngle(Math.toRadians(269.05f), 0.0f, 0.0f));
        } else if (temp == 1){
            /**
             * upperarmor
             * summon ArmorStand ~2 ~-1.1 ~0.2
             * Pose:{Body:[22.85f,0.0f,0.0f],
             * Head:[77.62f,0.0f,20.49f],
             * LeftLeg:[-1.0f,0.0f,-1.0f],
             * RightLeg:[1.0f,0.0f,1.0f],
             * LeftArm:[331.07f,0.0f,360.0f],
             * RightArm:[351.32f,0.0f,21.47f]}}
             *
             * lowerarmor
             * summon ArmorStand ~2 ~-1.1 ~
             * Pose:{Body:[4.29f,0.0f,0.0f],
             * LeftLeg:[272.71f,354.85f,6.18f],
             * RightLeg:[268.83f,28.12f,4.76f],
             * LeftArm:[-10.0f,0.0f,-10.0f],
             * RightArm:[-15.0f,0.0f,10.0f]}}
             */
            upperArmor = (ArmorStand) loc.getWorld().spawnEntity(armorLoc.clone().add(0, -0.6, 0.135), EntityType.ARMOR_STAND);
            lowerArmor = (ArmorStand) loc.getWorld().spawnEntity(armorLoc.clone().add(0, -0.6, 0), EntityType.ARMOR_STAND);

            upperArmor.setBodyPose(new EulerAngle(Math.toRadians(22.85f), 0.0f, 0.0f));
            upperArmor.setHeadPose(new EulerAngle(Math.toRadians(77.62f), 0.0f, Math.toRadians(20.49f)));
            upperArmor.setLeftArmPose(new EulerAngle(Math.toRadians(331.07f), Math.toRadians(0.0f), Math.toRadians(0.0f)));
            upperArmor.setRightArmPose(new EulerAngle(Math.toRadians(351.32f), Math.toRadians(0.0f), Math.toRadians(21.47f)));
            upperArmor.setLeftLegPose(new EulerAngle(Math.toRadians(259.71f), Math.toRadians(343.3f), Math.toRadians(0.18f)));
            upperArmor.setRightLegPose(new EulerAngle(Math.toRadians(252.83f), Math.toRadians(28.12f), Math.toRadians(4.76f)));

            lowerArmor.setBodyPose(new EulerAngle(Math.toRadians(4.29f), Math.toRadians(0), Math.toRadians(0)));
            lowerArmor.setHeadPose(new EulerAngle(0f, 0f, 0f));
            lowerArmor.setLeftArmPose(new EulerAngle(Math.toRadians(-10.0f), Math.toRadians(0.0f), Math.toRadians(-10.0f)));
            lowerArmor.setRightArmPose(new EulerAngle(Math.toRadians(-15.0f), Math.toRadians(0.0f), Math.toRadians(-10.0f)));
            lowerArmor.setLeftLegPose(new EulerAngle(Math.toRadians(272.71f), Math.toRadians(354.85f), Math.toRadians(6.18f)));
            lowerArmor.setRightLegPose(new EulerAngle(Math.toRadians(268.83f), Math.toRadians(28.12f), Math.toRadians(4.76f)));

        } else {
            /**
             * upperarmor
             * summon ArmorStand ~ ~-1.6 ~2 {Rotation:[89.7f],
             * Pose:{Body:[273.62f,360.0f,0.0f],
             * Head:[266.83f,0.0f,18.88f],
             * LeftLeg:[-1.0f,0.0f,-1.0f],
             * RightLeg:[1.0f,0.0f,1.0f],
             * LeftArm:[265.61f,344.86f,0.4f],
             * RightArm:[278.18f,17.26f,10.0f]}}
             *
             * lowerarmor
             * summon ArmorStand ~-0.65 ~-1.05 ~2 {Rotation:[90.3f],
             * Pose:{LeftLeg:[272.84f,355.11f,-1.0f],
             * RightLeg:[268.95f,6.15f,1.0f],
             * LeftArm:[-10.0f,0.0f,-10.0f],
             * RightArm:[-15.0f,0.0f,10.0f]}}
             */

            Location upperLoc = armorLoc.clone().add(0, -1.1, 0);
            Location lowerLoc = armorLoc.clone().add(-0.65, -0.55, 0);
            upperLoc.setYaw(90f);
            lowerLoc.setYaw(90f);
            upperArmor = (ArmorStand) loc.getWorld().spawnEntity(upperLoc, EntityType.ARMOR_STAND);
            lowerArmor = (ArmorStand) loc.getWorld().spawnEntity(lowerLoc, EntityType.ARMOR_STAND);

            upperArmor.setBodyPose(new EulerAngle(Math.toRadians(273.62f), 0.0f, 0.0f));
            upperArmor.setHeadPose(new EulerAngle(Math.toRadians(266.83f), 0.0f, Math.toRadians(18.88f)));
            upperArmor.setLeftArmPose(new EulerAngle(Math.toRadians(265.61f), Math.toRadians(344.86f), Math.toRadians(0.4f)));
            upperArmor.setRightArmPose(new EulerAngle(Math.toRadians(278.18f), Math.toRadians(17.26f), Math.toRadians(10.0f)));
            upperArmor.setLeftLegPose(new EulerAngle(Math.toRadians(0.0f), Math.toRadians(0.0f), Math.toRadians(0.0f)));
            upperArmor.setRightLegPose(new EulerAngle(Math.toRadians(0.0f), Math.toRadians(0.0f), Math.toRadians(0.0f)));

            lowerArmor.setBodyPose(new EulerAngle(Math.toRadians(0f), Math.toRadians(0), Math.toRadians(0)));
            lowerArmor.setHeadPose(new EulerAngle(0f, 0f, 0f));
            lowerArmor.setLeftArmPose(new EulerAngle(Math.toRadians(-10.0f), Math.toRadians(0.0f), Math.toRadians(-10.0f)));
            lowerArmor.setRightArmPose(new EulerAngle(Math.toRadians(-15.0f), Math.toRadians(0.0f), Math.toRadians(-10.0f)));
            lowerArmor.setLeftLegPose(new EulerAngle(Math.toRadians(272.84f), Math.toRadians(355.11f), Math.toRadians(-1.0f)));
            lowerArmor.setRightLegPose(new EulerAngle(Math.toRadians(268.95f), Math.toRadians(6.15f), Math.toRadians(1.0f)));
        }

        upperArmor.setGravity(false);
        upperArmor.setArms(true);
        upperArmor.setBasePlate(false);
        upperArmor.setVisible(true);
        upperArmor.setMetadata("DGcorpse", new FixedMetadataValue(DetectiveGame.instance, this.getNpcSerial()));

        lowerArmor.setVisible(false);
        lowerArmor.setGravity(false);
        lowerArmor.setArms(true);
        lowerArmor.setBasePlate(false);
        lowerArmor.setMetadata("DGcorpse", new FixedMetadataValue(DetectiveGame.instance, this.getNpcSerial()));

        upperArmor.setHelmet(npcArmour.get(0));
        upperArmor.setChestplate(npcArmour.get(1));
        lowerArmor.setLeggings(npcArmour.get(2));
        lowerArmor.setBoots(npcArmour.get(3));
    }
    public boolean isDead() {
        return this.isDead;
    }
    public ItemBase getKillItem() { return killItem; }
    public void killNpc(ItemBase killItem) {
        setKillItem(killItem);
        isDead = true;
        spawnCorpse(getNpc().getEntity().getLocation());
        ((Player)getNpc().getEntity()).damage(1);
        ((Player)getNpc().getEntity()).setHealth(0);
    }
    public void setKillItem(ItemBase killItem) {
        this.killItem = killItem;
    }

    protected void initNpc(int npcSerial, String npcName, String npcSkin, List<ItemStack> npcArmour, String ... npcQuotes) {
        this.npcSerial = npcSerial;
        this.npcName = npcName;
        this.npcSkin = npcSkin;
        this.npcArmour = npcArmour;

        this.npcQuotes = new String[npcQuotes.length];
        for(int i=0 ; i<npcQuotes.length ; i++) {
            this.npcQuotes[i] = npcQuotes[i];
        }

        NpcList.NpcList.add(this);
    }
    public int getNpcSerial() { return npcSerial; }
    public Location getNpcLocation() { return npcLocation; }
    public NPC getNpc() { //returns Unique Entity ID
        return npc;
    }
    public void spawnNpc(Location loc) {
        this.npcReg = CitizensAPI.getNPCRegistry();
        npc = npcReg.createNPC(EntityType.PLAYER, ChatColor.DARK_GREEN + npcName);
        npc.setProtected(true);
        npc.addTrait(CitizensAPI.getTraitFactory().getTrait(LookClose.class));
        npc.getTrait(LookClose.class).lookClose(true);
        npc.spawn(loc);

        npc.addTrait(CitizensAPI.getTraitFactory().getTrait(NpcTalkTrait.class));
        npc.getTrait(NpcTalkTrait.class).setQuotes(npcQuotes);

        if(npcArmour != null) {
            ((Player) npc.getEntity()).getInventory().setHelmet(npcArmour.get(0));
            ((Player) npc.getEntity()).getInventory().setChestplate(npcArmour.get(1));
            ((Player) npc.getEntity()).getInventory().setLeggings(npcArmour.get(2));
            ((Player) npc.getEntity()).getInventory().setBoots(npcArmour.get(3));
        }
        this.npcLocation = npc.getEntity().getLocation();
        npc.getEntity().setMetadata("DGnpc", new FixedMetadataValue(DetectiveGame.instance, "1"));
    }
}
