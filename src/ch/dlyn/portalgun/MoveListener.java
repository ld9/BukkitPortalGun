package ch.dlyn.portalgun;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		for (Portal p : Portal.getPortals()) {
			if (p.isColliding(e.getPlayer().getLocation())) {
				if (p.isSafe()) {
					e.getPlayer().teleport(p.getDestination());
				} else {
					if (Math.random() < 0.2) {
						e.getPlayer().setHealth(0);
					} else {
						e.getPlayer().teleport(p.getDestination());
					}
				}
			}
		}
	}
	
}
