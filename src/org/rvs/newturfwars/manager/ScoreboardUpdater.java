package org.rvs.newturfwars.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class ScoreboardUpdater {

    @SuppressWarnings("deprecation")
	public void updateScoreboard(final Player player, int times, String type) {
        final ScoreboardManager m = Bukkit.getScoreboardManager();
        final Scoreboard b = m.getNewScoreboard();
        final Objective o = b.registerNewObjective("Gold", "");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        o.setDisplayName(ChatColor.GOLD + "NKTurfwars " + ChatColor.GRAY + " - " + ChatColor.YELLOW + player.getName());
        final Score title = o.getScore("§6Time "+type+":");
        title.setScore(2);
        final Score time = o.getScore("§7"+times+"s");
        time.setScore(1);
        player.setScoreboard(b);
    }
    
    public void resetScoreboard(Player p) {
    	p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }
}
