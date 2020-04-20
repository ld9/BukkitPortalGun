package ch.dlyn.portalgun;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class CraftingAdditions {
	
	public static void addCraftingRecipes() {
		
		// Stable Fluid Craft
		ItemStack stableCraftResult = getStableFluid();
		stableCraftResult.setAmount(4);
		ShapedRecipe stableFluidRecipe = new ShapedRecipe(getStableFluidNamespacedKey(), stableCraftResult);
		stableFluidRecipe.shape("XAX", "ABA", "XAX");
		stableFluidRecipe.setIngredient('X', Material.AIR);
		stableFluidRecipe.setIngredient('A', getEnderComponent().getData());
		stableFluidRecipe.setIngredient('B', Material.GOLD_BLOCK);
		
		PortalGun.getPlugin(PortalGun.class).getServer().addRecipe(stableFluidRecipe);
		
		// Unstable Fluid Craft
		ItemStack unstableCraftResult = getUnstableFluid();
		unstableCraftResult.setAmount(8);
		ShapedRecipe unstableFluidRecipe = new ShapedRecipe(getUnstableFluidNamespacedKey(), unstableCraftResult);
		unstableFluidRecipe.shape("XAX", "ABA", "XAX");
		unstableFluidRecipe.setIngredient('X', Material.AIR);
		unstableFluidRecipe.setIngredient('A', Material.REDSTONE);
		unstableFluidRecipe.setIngredient('B', getEnderComponent().getData());
		
		PortalGun.getPlugin(PortalGun.class).getServer().addRecipe(unstableFluidRecipe);
		
		// Portal Anchor Craft
		ShapedRecipe portalAnchorRecipe = new ShapedRecipe(getPortalAnchorNamespacedKey(), getEnderAnchor());
		portalAnchorRecipe.shape("XXX", "BAB", "XXX");
		portalAnchorRecipe.setIngredient('X', Material.AIR);
		portalAnchorRecipe.setIngredient('A', getEnderComponent().getData());
		portalAnchorRecipe.setIngredient('B', Material.ENDER_PEARL);
		
		PortalGun.getPlugin(PortalGun.class).getServer().addRecipe(portalAnchorRecipe);
		
		// Portal Gun Craft
		ShapedRecipe portalGunRecipe = new ShapedRecipe(getPortalGunNamespacedKey(), getPortalGun());
		portalGunRecipe.shape("XAX", "BCD", "BXX");
		portalGunRecipe.setIngredient('X', Material.AIR);
		portalGunRecipe.setIngredient('A', getEnderComponent().getData());
		portalGunRecipe.setIngredient('B', Material.IRON_INGOT);
		portalGunRecipe.setIngredient('C', Material.NETHER_STAR);
		portalGunRecipe.setIngredient('D', Material.DIAMOND_BLOCK);
		
		PortalGun.getPlugin(PortalGun.class).getServer().addRecipe(portalGunRecipe);
	}
	
	private static NamespacedKey getPortalAnchorNamespacedKey() {
		return new NamespacedKey(PortalGun.getPlugin(PortalGun.class), "portalAnchor");
	}
	
	private static NamespacedKey getPortalGunNamespacedKey() {
		return new NamespacedKey(PortalGun.getPlugin(PortalGun.class), "portalGun");
	}
	
	private static NamespacedKey getStableFluidNamespacedKey() {
		return new NamespacedKey(PortalGun.getPlugin(PortalGun.class), "stableFluid");
	}
	
	private static NamespacedKey getUnstableFluidNamespacedKey() {
		return new NamespacedKey(PortalGun.getPlugin(PortalGun.class), "unstableFluid");
	}
	
	public static ItemStack getPortalGun() {
		ItemStack portalGun = new ItemStack(Material.GOLDEN_HOE);
		
		ItemMeta portalGunMeta = portalGun.getItemMeta();
		portalGunMeta.setDisplayName(ChatColor.GREEN + "Portal Gun");
		ArrayList<String> portalGunLore = new ArrayList<>();
		portalGunLore.add(ChatColor.DARK_AQUA + "Use this to get around quickly.");
		portalGunLore.add(ChatColor.DARK_AQUA + "Currently unbound.");
		portalGunMeta.setLore(portalGunLore);
		portalGunMeta.setCustomModelData(4211);
		portalGun.setItemMeta(portalGunMeta);
		
		portalGun.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
		return portalGun;
	}
	
	public static ItemStack getStableFluid() {
		ItemStack stableFluid = new ItemStack(Material.LIME_DYE);
		
		ItemMeta stableFluidMeta = stableFluid.getItemMeta();
		stableFluidMeta.setDisplayName(ChatColor.GREEN + "Portal Fluid");
		ArrayList<String> stableFluidLore = new ArrayList<>();
		stableFluidLore.add(ChatColor.DARK_AQUA + "Use this to charge a portal gun.");
		stableFluidLore.add(ChatColor.DARK_AQUA + "This fluid does not carry a risk of death when used.");
		stableFluidMeta.setLore(stableFluidLore);
		stableFluidMeta.setCustomModelData(4208);
		stableFluid.setItemMeta(stableFluidMeta);
		
		stableFluid.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
		return stableFluid;
	}
	
	public static ItemStack getUnstableFluid() {
		ItemStack unstableFluid = new ItemStack(Material.PINK_DYE);
		
		ItemMeta unstableFluidMeta = unstableFluid.getItemMeta();
		unstableFluidMeta.setDisplayName(ChatColor.RED + "Bootleg Portal Fluid");
		ArrayList<String> unstableFluidLore = new ArrayList<>();
		unstableFluidLore.add(ChatColor.DARK_AQUA + "Use this to charge a portal gun.");
		unstableFluidLore.add(ChatColor.DARK_AQUA + "This fluid " + ChatColor.RED + "does" + ChatColor.DARK_AQUA + " carry a risk of death when used.");
		unstableFluidMeta.setLore(unstableFluidLore);
		unstableFluidMeta.setCustomModelData(4209);
		unstableFluid.setItemMeta(unstableFluidMeta);
		
		unstableFluid.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
		return unstableFluid;
	}
	
	public static ItemStack getEnderComponent() {
		ItemStack enderComponent = new ItemStack(Material.ENDER_PEARL);
		
		ItemMeta enderComponentMeta = enderComponent.getItemMeta();
		enderComponentMeta.setDisplayName(ChatColor.DARK_GREEN + "Strange Gem");
		ArrayList<String> enderComponentLore = new ArrayList<>();
		enderComponentLore.add(ChatColor.DARK_AQUA + "Use this to craft portal fluid.");
		enderComponentMeta.setLore(enderComponentLore);
		enderComponent.setItemMeta(enderComponentMeta);
		
		enderComponent.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
		return enderComponent;
	}
	
	public static ItemStack getEnderAnchor() {
		ItemStack enderAnchor = new ItemStack(Material.ENDER_EYE);
		
		ItemMeta enderAnchorMeta = enderAnchor.getItemMeta();
		enderAnchorMeta.setDisplayName(ChatColor.DARK_GREEN + "Portal Anchor");
		ArrayList<String> enderAnchorLore = new ArrayList<>();
		enderAnchorLore.add(ChatColor.DARK_AQUA + "Saves a location for later use.");
		enderAnchorLore.add(ChatColor.DARK_AQUA + "Currntly unbound.");
		enderAnchorMeta.setLore(enderAnchorLore);
		enderAnchor.setItemMeta(enderAnchorMeta);
		
		enderAnchor.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
		return enderAnchor;
	}
	
}
