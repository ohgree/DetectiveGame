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

package com.icloud.infectedduck.detectivegame.lists;

import com.icloud.infectedduck.detectivegame.professions.*;
import com.icloud.infectedduck.detectivegame.types.ProfessionBase;

import java.util.ArrayList;

/**
 * Created by InfectedDuck on 15. 9. 29..
 */
public class ProfessionList {
    public final static ArrayList<ProfessionBase> ProfessionList = new ArrayList<ProfessionBase>();

    public final static JobDoctor doctor = new JobDoctor();
    public final static JobStudent student = new JobStudent();

    public final static JobPolice police = new JobPolice();
    public final static JobDetective detective = new JobDetective();

    public final static JobSoldier soldier = new JobSoldier();
    public final static JobDeltaForce deltaForce = new JobDeltaForce();
    public final static JobEngineer engineer = new JobEngineer();
    public final static JobSalary salary = new JobSalary();
    public final static JobUnemployed unemployed = new JobUnemployed();
}
