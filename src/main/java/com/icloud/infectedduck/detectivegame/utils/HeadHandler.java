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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
/**
 * @author BigTeddy98
 * @author dori99xd
 * @author InfectedDuck <infectedduckkr@gmail.com>
 * Refletions by BigTeddy98
 * Modified by InfectedDuck. Big thanks to dori99xd
 */
public class HeadHandler {

    private static final Random random = new Random();
    private static final String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static Method getWorldHandle;
    private static Method getWorldTileEntity;
    private static Method setGameProfile;

    // Example
    //@Override
    /*public static void HeadHandler() {
        if (getWorldHandle == null || getWorldTileEntity == null || setGameProfile == null) {
            try {
                getWorldHandle = getCraftClass("CraftWorld").getMethod("getHandle");
                getWorldTileEntity = getMCClass("WorldServer").getMethod("getTileEntity", int.class, int.class, int.class);
                setGameProfile = getMCClass("TileEntitySkull").getMethod("setGameProfile", GameProfile.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }*/

    // Example
    /*@EventHandler(ignoreCancelled=true)
    public void on(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.SKULL)
            setSkullWithNonPlayerProfile("[url]http://213.136.94.170/downloads/img/skin.png[/url]", true, event.getClickedBlock());
    }*/

    // Method
    /*public static ItemStack createSkullWithNonPlayerProfile(String skinURL, boolean randomName) {
        //if(skull.getType() != Material.SKULL)
        //    throw new IllegalArgumentException("Block must be a skull.");
        Skull s = (Skull) skull.getState();
        try {
            setSkullProfile(s, getNonPlayerProfile(skinURL, randomName));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        return skull;
        //skull.getWorld().refreshChunk(skull.getChunk().getX(), skull.getChunk().getZ());
    }*/

    public static ItemStack CreateHead(boolean randomName, String displayName, String texture) {
        GameProfile profile = getNonPlayerProfile(texture, randomName);
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta headMeta = head.getItemMeta();
        Class headMetaClass = headMeta.getClass();
        if(!setReflection(headMetaClass, headMeta, "profile", profile, "Unable to inject profile")) {
            return null;
        } else {
            head.setItemMeta(headMeta);
            SkullMeta skullMeta = (SkullMeta)head.getItemMeta();
            skullMeta.setDisplayName(displayName);
            //skullMeta.setLore(displayLore);
            head.setItemMeta(skullMeta);
            return head;
        }
    }
    public static boolean setReflection(Class<?> sourceClass, Object instance, String fieldName, Object value, String message) {
        try {
            Field ex = sourceClass.getDeclaredField(fieldName);
            boolean accessible = ex.isAccessible();
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            int modifiers = modifiersField.getModifiers();
            boolean isFinal = (modifiers & 16) == 16;
            if(!accessible) {
                ex.setAccessible(true);
            }

            if(isFinal) {
                modifiersField.setAccessible(true);
                modifiersField.setInt(ex, modifiers & -17);
            }

            try {
                ex.set(instance, value);
            } finally {
                if(isFinal) {
                    modifiersField.setInt(ex, modifiers | 16);
                }

                if(!accessible) {
                    ex.setAccessible(false);
                }

            }

            return true;
        } catch (IllegalArgumentException var17) {
            Bukkit.getLogger().log(Level.INFO, message + ": unsupported version.");
        } catch (IllegalAccessException var18) {
            Bukkit.getLogger().log(Level.INFO, message + ": security exception.");
        } catch (NoSuchFieldException var19) {
            Bukkit.getLogger().log(Level.INFO, message + ": unsupported version, field " + fieldName + " not found.");
        } catch (SecurityException var20) {
            Bukkit.getLogger().log(Level.INFO, message + ": security exception.");
        }

        return false;
    }
    // Method
    /*private static void setSkullProfile(Skull skull, GameProfile someGameprofile) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        //Object world = getWorldHandle.invoke(skull.getWorld());
        //Object tileSkull = getWorldTileEntity.invoke(world, skull.getX(), skull.getY(), skull.getZ());
        //setGameProfile.invoke(tileSkull, someGameprofile);
        setGameProfile
    }*/

    // Method
    public static GameProfile getNonPlayerProfile(String skinURL, boolean randomName) {
        GameProfile newSkinProfile = new GameProfile(UUID.randomUUID(), randomName ? getRandomString(16) : null);
        newSkinProfile.getProperties().put("textures", new Property("textures", skinURL));
        return newSkinProfile;
    }

    // Example
    public static String getRandomString(int length) {
        StringBuilder b = new StringBuilder(length);
        for(int j = 0; j < length; j++)
            b.append(chars.charAt(random.nextInt(chars.length())));
        return b.toString();
    }

    // Reflection
    private static Class<?> getMCClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        String className = "net.minecraft.server." + version + name;
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }

    // Reflection
    private static Class<?> getCraftClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        String className = "org.bukkit.craftbukkit." + version + name;
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }
}
/*
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import com.mojang.authlib.GameProfile;

import java.util.List;
import java.util.UUID;

/**
 * Created by InfectedDuck on 15. 9. 26..
 *//*
public class HeadHandler {
    public HeadHandler() {
    }

    public ItemStack CreateHead(UUID id, String displayName, List<String> displayLore, String texture) {
        GameProfile profile = this.createGameProfile(texture, id);
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        ItemMeta headMeta = head.getItemMeta();
        Class headMetaClass = headMeta.getClass();
        if(!Reflection.set(headMetaClass, headMeta, "profile", profile, "Unable to inject profile")) {
            return null;
        } else {
            head.setItemMeta(headMeta);
            SkullMeta skullMeta = (SkullMeta)head.getItemMeta();
            skullMeta.setDisplayName(displayName);
            skullMeta.setLore(displayLore);
            head.setItemMeta(skullMeta);
            return head;
        }
    }
}*/


