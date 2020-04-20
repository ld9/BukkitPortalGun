package ch.dlyn.portalgun;

import java.util.ArrayList;
import java.util.Collection;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Portal {
	private Location source, destination;
	private boolean safe;
	private int remainingTicks;
	private BukkitTask animationTask;
	
	private static ArrayList<Portal> portals;
	
	public Portal(Location source, Location destination, boolean safe) {
		this.source = source;
		this.destination = destination;
		this.safe = safe;
		this.remainingTicks = 160;
		
		portals.add(this);
		
		runAnimations();
	}
	
	public Location getSource() {
		return source;
	}
	
	public Location getDestination() {
		return destination;
	}
	
	public boolean isSafe() {
		return safe;
	}
	
	public static ArrayList<Portal> getPortals() {
		return portals;
	}
	
	public static void initializePortals() {
		portals = new ArrayList<>();
	}
	
	private void runAnimations() {
		Portal thisPortal = this;
		animationTask = new BukkitRunnable() {
			
			@Override
			public void run() {
				runParticles(getSource());
				runParticles(getDestination());
				
				Collection<Entity> ens = getSource().getWorld().getNearbyEntities(getSource(), 64, 64, 64);
				for(Entity en : ens) {
					if (en.getLocation().distance(getSource()) < 2.25F) {
						if (!en.getType().equals(EntityType.PLAYER)) {
							if (isSafe()) {
								en.teleport(getDestination());
							} else {
								if (Math.random() < 0.2) {
									((LivingEntity) en).setHealth(0);
								} else {
									en.teleport(getDestination());
								}
							}
						}
					}
				}
				
				remainingTicks--;
				if (remainingTicks < 0) {
					Portal.portals.remove(thisPortal);
					animationTask.cancel();
				}
			}
			
		}.runTaskTimer(PortalGun.getPlugin(PortalGun.class), 0, 1);
	}
	
	private void runParticles(Location location) {
		Collection<? extends Player> onlinePlayers = PortalGun.getPlugin(PortalGun.class).getServer().getOnlinePlayers();
		
		for(Player player : onlinePlayers) {
			//player.spawnParticle(Particle.SLIME, location, 25, 0, 0, 0, 1);
			player.spawnParticle(Particle.SNEEZE, location, 25, Math.random()/10, Math.random()/2 + .5, Math.random()/10, .01);
		}
	}
	
	public boolean isColliding(Location location) {
		final float ACCEPTABLE_DISTANCE = 2.25F;
		
		return (getSource().distance(location) < ACCEPTABLE_DISTANCE);
	}
	
}
