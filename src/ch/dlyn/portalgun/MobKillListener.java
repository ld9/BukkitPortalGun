package ch.dlyn.portalgun;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

// Yes, this is the same code as in the hoverpack plugin. I think it's relatively balanced stops AFK/mindless farms.
public class MobKillListener implements Listener {
	
	public static HashMap<Location, Long> recentEnderKillLocations;
	
	public MobKillListener() {
		recentEnderKillLocations = new HashMap<>();
	}
	
	@EventHandler
	public void mobKill(EntityDeathEvent e) {
		if (e.getEntityType().equals(EntityType.ENDERMAN)) {
			if (Math.random() < calculateDropChance(e.getEntity())) {
				e.getDrops().add(CraftingAdditions.getEnderComponent());
			}
		}
	}
	
	private float calculateDropChance(Entity entity) {
		float dropChance = 3;
		
		for (Entry<Location, Long> e : recentEnderKillLocations.entrySet()) {
			if (System.currentTimeMillis() - (1000 * 300) > e.getValue()) {
				recentEnderKillLocations.remove(e.getKey(), e.getValue());
			} else if (e.getKey().distance(entity.getLocation()) < 24) {
				dropChance++;
			}
		}
			
		recentEnderKillLocations.put(entity.getLocation(), System.currentTimeMillis());
		
		dropChance = 2F / dropChance;
		return dropChance;
	}
	
}
