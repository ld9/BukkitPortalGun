package ch.dlyn.portalgun;

import org.bukkit.plugin.java.JavaPlugin;

public class PortalGun extends JavaPlugin {
	
	@Override
	public void onEnable() {
		
		getServer().getLogger().info("Enabling PortalGun plugin");
		
		CraftingAdditions.addCraftingRecipes();
		
		getServer().getPluginManager().registerEvents(new MobKillListener(), this);
		getServer().getPluginManager().registerEvents(new MoveListener(), this);
		getServer().getPluginManager().registerEvents(new InteractListener(), this);
		
		getCommand("portaltest").setExecutor(new TestingCommandListener());
		
		Portal.initializePortals();
	}
	
	@Override
	public void onDisable() {
		getServer().getLogger().info("Disabling PortalGun plugin");
	}
	
}
