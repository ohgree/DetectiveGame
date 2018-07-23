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

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;

import java.util.*;


/**
 * Created by InfectedDuck on 2015. 10. 30..
 */
public enum Corpse {
    FACEDOWN {
        public HashMap<List<EulerAngle>, List<Float>> getCorpse() {
            HashMap<List<EulerAngle>, List<Float>> corpse = new HashMap<List<EulerAngle>, List<Float>>();
            List<EulerAngle> upperBody = new ArrayList<EulerAngle>();
            upperBody.add(new EulerAngle(Math.toRadians(90f), 0f, 0f));//body
            upperBody.add(new EulerAngle(Math.toRadians(90f), 0f, Math.toRadians(80f)));//head
            upperBody.add(new EulerAngle(Math.toRadians(90f), 0f, 0f));//leftarm
            upperBody.add(new EulerAngle(90f, 0f, 0f));//rightarm
            upperBody.add(new EulerAngle(0f, 0f, 0f));//leftleg
            upperBody.add(new EulerAngle(0f, 0f, 0f));//rightleg

            List<EulerAngle> lowerBody = new ArrayList<EulerAngle>();
            upperBody.add(new EulerAngle(360f, 0f, 360f));//body
            upperBody.add(new EulerAngle(0f, 0f, 80f));//head
            upperBody.add(new EulerAngle(0f, 0f, 0f));//leftarm
            upperBody.add(new EulerAngle(293f, 42f, 195f));//rightarm
            upperBody.add(new EulerAngle(90f, 0f, 0f));//leftleg
            upperBody.add(new EulerAngle(90f, 0f, 0f));//rightleg

            corpse.put(upperBody, Arrays.asList(0f, -1f, 0f));
            corpse.put(lowerBody, Arrays.asList(0f, -0.9f, -0.6f));
/**
 * /summon ArmorStand ~ ~ ~ {ShowArms:1b,Equipment:[{},{id:"diamond_shoes",Count:1b},
 * {id:"diamond_leggings",Count:1b},{id:"diamond_chestplate",Count:1b},
 * {id:"skull",Count:1b,Damage:3b,tag:{SkullOwner:"5Gree"}}],
 * Pose:{Body:[90f,0f,0f],Head:[90f,0f,80f],LeftArm:[90f,0f,0f],RightArm:[90f,0f,0f]}}

 /summon ArmorStand ~ ~0.1 ~0.4 {Invisible:1b,NoGravity:1b,ShowArms:1b,
 Equipment:[{id:"iron_sword",Count:1b},{id:"golden_boots",Count:1b},
 {id:"diamond_leggings",Count:1b},{},{}],
 Pose:{Body:[360f,0f,360f],LeftLeg:[90f,0f,0f],
 RightLeg:[90f,0f,0f],RightArm:[293f,42f,195f]}}
 */
            return corpse;
        }
    }, FACEUP {
        public HashMap<ArmorStand, Location> getCorpse() {
            HashMap<ArmorStand, Location> corpse = new HashMap<ArmorStand, Location>();

            return corpse;
        }
    }, SITTING {
        public HashMap<ArmorStand, Location> getCorpse() {
            HashMap<ArmorStand, Location> corpse = new HashMap<ArmorStand, Location>();

            return corpse;
        }
    };

    public static Corpse getRandomCorpse() {
        Random random = new Random();
        return Corpse.values()[random.nextInt(Corpse.values().length)];
    }
    private Corpse(Integer ... corpsePosesInDegrees) {

    }
}