package org.rvs.newturfwars.manager;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;

public class Team {
	private String name;
	private HashMap<UUID, String> members = new HashMap<>();

	public Team(Player owner, String name) {
	    this.name = name;
	    members.put(owner.getUniqueId(), "owner");
	}

	public boolean isInTeam(Player player) {
	    return members.containsKey(player.getUniqueId());
	}

	public Set<UUID> getMembers() {
	    return members.keySet();
	}

	public UUID getOwner() {
	    Set<UUID> keySet = members.keySet();
	    for (UUID uuid : keySet) {
	        if (members.get(uuid).equals("owner"))
	            return uuid;
	    }
	    return null;
	}

	public void addMember(Player player, String memberType) {
	    members.put(player.getUniqueId(), memberType);
	}

	public void removeMember(Player player) {
	    if (members.containsKey(player.getUniqueId()))
	        members.remove(player.getUniqueId());
	}

}
