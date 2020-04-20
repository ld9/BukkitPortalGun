package ch.dlyn.portalgun;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestingCommandListener implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		if (arg1.getName().equalsIgnoreCase("portaltest")) {
			Player p = (Player) arg0;
			p.getInventory().addItem(CraftingAdditions.getEnderAnchor());
			p.getInventory().addItem(CraftingAdditions.getPortalGun());
			p.getInventory().addItem(CraftingAdditions.getEnderComponent());
			p.getInventory().addItem(CraftingAdditions.getStableFluid());
			p.getInventory().addItem(CraftingAdditions.getUnstableFluid());

			return true;
		}
		
		return false;
	}
	
}
