package mimiga;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener {

	@Override
	public void onDisable() {

	}

	@Override
	public void onEnable() {
		System.out.println(getConfig().getString("name-format"));
		getConfig().options().copyDefaults(true);
		saveConfig();

		Bukkit.getPluginManager().registerEvents(this, this);
		ConsoleCommandSender log = getServer().getConsoleSender();
		log.sendMessage(ChatColor.RED + "Mimiga by gamesterrex.");
		log.sendMessage(ChatColor.RED + "Contact me at <robertchen@live.com>");

		BukkitRunnable run = new BukkitRunnable() {

			@Override
			public void run() {
				for (Player player : Bukkit.getServer().getOnlinePlayers()) {
					if (player.hasPermission("mimiga.status")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 150,
								getConfig().getInt("speed-effect-value") - 1), true);
					}
				}
			}

		};
		run.runTaskTimer(this, 0, 100);
	}

	@EventHandler
	public void onRightClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if (!player.hasPermission("mimiga.status"))
			return;

		ItemStack item = player.getInventory().getItemInMainHand();

		if (item != null && item.getType() == Material.RED_ROSE) {
			item.setAmount(item.getAmount() - 1);

			player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, getConfig().getInt("mimiga-time"),
					getConfig().getInt("strength-effect-value") - 1), true);
			player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, getConfig().getInt("mimiga-time"),
					getConfig().getInt("jump-effect-value") - 1), true);
		}
	}

}
