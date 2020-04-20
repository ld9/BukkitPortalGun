package ch.dlyn.portalgun;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;

public class InteractListener implements Listener {
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		
		ItemStack heldItem = e.getPlayer().getInventory().getItemInMainHand();
		if (heldItem != null &&
				heldItem.getType().equals(CraftingAdditions.getPortalGun().getType()) &&
				heldItem.getEnchantments().containsKey(Enchantment.ARROW_INFINITE)) {
			// Player is using a portal gun
			
			if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
				// Assign destination to gun
				
				Location target = getTargetLocation(e.getPlayer());
				String targetString = String.format("%s%f,%f,%f,%s", ChatColor.GREEN, target.getX(), target.getY(), target.getZ(), target.getWorld().getName());
				
				ItemMeta meta = heldItem.getItemMeta();
				List<String> lore = meta.getLore();
				lore.remove(lore.size() - 1);
				lore.add(targetString);
				meta.setLore(lore);
				meta.setCustomModelData(4211);
				heldItem.setItemMeta(meta);
			}
			
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				// Complete Portal
				
				ItemMeta meta = heldItem.getItemMeta();
				List<String> lore = meta.getLore();
				
				if (!lore.get(1).toLowerCase().contains("unbound")) {					
					String[] points = lore.get(1).substring(2).split(",");
					Location destination = new Location(PortalGun.getPlugin(PortalGun.class).getServer().getWorld(points[3]),
							Double.valueOf(points[0]),
							Double.valueOf(points[1]),
							Double.valueOf(points[2]));
					
					int cost = 1 + ((int) destination.distance(e.getPlayer().getLocation()) / 128);
					if (cost > 64) {
						e.getPlayer().sendMessage(ChatColor.RED + "Longest distance travelable in a single jump is 2^13 blocks.");
						e.getPlayer().sendMessage(ChatColor.RED + "Your portal gun remains bound to the previously set location.");
						return;
					}
					
					int payment = getFluidPayment(e.getPlayer(), cost);
					if (payment < 2) {
						new Portal(getTargetLocation(e.getPlayer()), destination, (payment == 0));
						
						lore.remove(lore.size() - 1);
						lore.add(ChatColor.DARK_AQUA + "Currently unbound.");
						meta.setLore(lore);
						meta.setCustomModelData(4211);
						heldItem.setItemMeta(meta);
					} else {
						e.getPlayer().sendMessage(ChatColor.RED + "You don't have the " + ChatColor.GREEN + cost + ChatColor.RED + " portal fluid required to make it that far.");
						e.getPlayer().sendMessage(ChatColor.RED + "Your portal gun remains bound to the previously set location.");
					}					
				}
			}
		} else if (heldItem != null &&
				heldItem.getType().equals(CraftingAdditions.getEnderAnchor().getType()) &&
				heldItem.getEnchantments().containsKey(Enchantment.ARROW_INFINITE) &&
				(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
			
			ItemMeta meta = heldItem.getItemMeta();
			List<String> lore = meta.getLore();
			
			if (!lore.get(1).toLowerCase().contains("unbound")) {					
				if (e.getPlayer().getInventory().contains(CraftingAdditions.getPortalGun())) {
					
					ItemStack portalGun = e.getPlayer().getInventory().getItem(e.getPlayer().getInventory().first(CraftingAdditions.getPortalGun()));
					
					ItemMeta portalGunMeta = portalGun.getItemMeta();
					List<String> portalGunLore = portalGunMeta.getLore();
					portalGunLore.remove(portalGunLore.size() - 1);
					portalGunLore.add(lore.get(1));
					portalGunMeta.setLore(portalGunLore);
					portalGun.setItemMeta(portalGunMeta);
				}
				
			} else {
				Location target = e.getPlayer().getLocation();
				String targetString = String.format("%s%f,%f,%f,%s", ChatColor.GREEN, target.getX(), target.getY(), target.getZ(), target.getWorld().getName());

				
				lore.remove(lore.size() - 1);
				lore.add(targetString);
				meta.setLore(lore);
				heldItem.setItemMeta(meta);
			}
			
			e.setCancelled(true);
		}
	}
	
	private int getFluidPayment(Player p, int amount) {
		if (p.getInventory().containsAtLeast(CraftingAdditions.getStableFluid(), amount)) {
			for (ItemStack s : p.getInventory().all(CraftingAdditions.getStableFluid().getType()).values()) {
				if (s.getItemMeta().getDisplayName().equals(CraftingAdditions.getStableFluid().getItemMeta().getDisplayName())) {
					if (s.getAmount() < amount) {
						int temp = s.getAmount();
						s.setAmount(s.getAmount() - 1);
						amount = amount - temp;
					} else {
						s.setAmount(s.getAmount() - 1);
						return 0;
					}
				}
			}
			return 2;
		} else if (p.getInventory().containsAtLeast(CraftingAdditions.getUnstableFluid(), amount)) {
			for (ItemStack s : p.getInventory().all(CraftingAdditions.getUnstableFluid().getType()).values()) {
				if (s.getItemMeta().getDisplayName().equals(CraftingAdditions.getUnstableFluid().getItemMeta().getDisplayName())) {
					if (s.getAmount() < amount) {
						int temp = s.getAmount();
						s.setAmount(s.getAmount() - 1);
						amount = amount - temp;
					} else {
						s.setAmount(s.getAmount() - 1);
						return 1;
					}
				}
			}
			return 2;
		} else {
			return 2;
		}
	}
	
	private Location getTargetLocation(Player p) {	
		if (p.isSneaking()) {
			return p.getEyeLocation().add(p.getLocation().getDirection().multiply(6).add(new Vector(0, 1, 0)));
		} else {
			Set<Material> liquids = new HashSet<Material>();
			liquids.add(Material.WATER);
			liquids.add(Material.LAVA);
			liquids.add(Material.AIR);
			
			Block targetBlock = p.getTargetBlock(liquids, 100);
			Location playerLocation = p.getEyeLocation();
			double blockDistance = targetBlock.getLocation().distance(playerLocation);
			return playerLocation.add(playerLocation.getDirection().multiply(blockDistance))
					.subtract(playerLocation.getDirection());
		}
	}
	
}
