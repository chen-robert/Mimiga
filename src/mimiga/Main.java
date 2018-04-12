package mimiga;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
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
		if (event.getAction() != Action.RIGHT_CLICK_AIR)
			return;

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

			player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 1.0f, 3.0f);
			for (double i = 0; i < 10 * 2 * Math.PI; i += Math.PI / 10) {
				player.spawnParticle(Particle.BLOCK_CRACK, player.getLocation(), 1, Math.cos(i), 1.0, Math.sin(i),
						new MaterialData(Material.RED_ROSE));
			}
		}
	}

}
