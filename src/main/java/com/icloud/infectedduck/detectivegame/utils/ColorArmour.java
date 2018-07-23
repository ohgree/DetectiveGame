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

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

/**
 * Created by InfectedDuck on 2015. 10. 5..
 */
public class ColorArmour {
    public static ItemStack[] getColoredArmour(int armourColor) {

        ItemStack[] armour = new ItemStack[4];

        armour[1] = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta lam = (LeatherArmorMeta)armour[1].getItemMeta();
        lam.setColor(Color.fromRGB(armourColor));
        armour[1].setItemMeta(lam);

        armour[2] = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        lam = (LeatherArmorMeta)armour[2].getItemMeta();
        lam.setColor(Color.fromRGB(armourColor));
        armour[2].setItemMeta(lam);

        armour[3] = new ItemStack(Material.LEATHER_BOOTS, 1);
        lam = (LeatherArmorMeta)armour[2].getItemMeta();
        lam.setColor(Color.fromRGB(armourColor));
        armour[3].setItemMeta(lam);

        return armour;
    }
    public static ItemStack getColoredChestplate(int armourColor) {
        ItemStack armour = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta lam = (LeatherArmorMeta)armour.getItemMeta();
        lam.setColor(Color.fromRGB(armourColor));
        armour.setItemMeta(lam);

        return armour;
    }
    public static ItemStack getColoredLeggings(int armourColor) {
        ItemStack armour = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        LeatherArmorMeta lam = (LeatherArmorMeta)armour.getItemMeta();
        lam.setColor(Color.fromRGB(armourColor));
        armour.setItemMeta(lam);

        return armour;
    }
    public static ItemStack getColoredBoots(int armourColor) {
        ItemStack armour = new ItemStack(Material.LEATHER_BOOTS, 1);
        LeatherArmorMeta lam = (LeatherArmorMeta)armour.getItemMeta();
        lam.setColor(Color.fromRGB(armourColor));
        armour.setItemMeta(lam);

        return armour;
    }
}
