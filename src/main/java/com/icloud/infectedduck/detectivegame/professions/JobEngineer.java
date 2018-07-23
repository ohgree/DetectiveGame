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

package com.icloud.infectedduck.detectivegame.professions;

import com.icloud.infectedduck.detectivegame.types.ProfessionBase;
import org.bukkit.ChatColor;

/**
 * Created by InfectedDuck on 15. 9. 21..
 */
public class JobEngineer extends ProfessionBase {
    public JobEngineer() {
        initProfession("엔지니어", Profession.ENGINEER,
                ChatColor.GRAY + "컴퓨터를 고쳐달라 하면 화내면서 고쳐주는 공돌이입니다.",
                ChatColor.GRAY + "카메라에 취미가 있어 " + ChatColor.DARK_GREEN + "감시카메라" + ChatColor.GRAY + "를 설치할 수 있습니다.",
                ChatColor.GRAY + "뛰어난 손재주로 " + ChatColor.DARK_GREEN + "얼음" + ChatColor.GRAY + "을 절묘하게 퓨즈에 끼워넣을 수 있습니다.",
                ChatColor.DARK_GREEN + "플레이어 상태확인" + ChatColor.GRAY + "을 사용할 수 있습니다.");
    }
}
