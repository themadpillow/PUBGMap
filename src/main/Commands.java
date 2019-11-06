package main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.WorldBorder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapView;

import renderers.Renderer;

public class Commands implements CommandExecutor {
	private Main Main;

	Commands(Main instance) {
		this.Main = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("next")) {
			if (args.length < 3) {
				return false;
			}

			Main.setNextBorder(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("givemap")) {
			if (args.length != 0) {
				ItemStack map = Main.createMap();
				for (Entity entity : Bukkit.selectEntities(sender, args[0])) {
					if (entity instanceof Player) {
						((Player) entity).getInventory().addItem(map.clone());
					}
				}
			} else {
				for (Player p : Bukkit.getOnlinePlayers()) {
					ItemStack map = Main.createMap();
					p.getInventory().addItem(map);
				}
			}

			return true;
		}

		if (cmd.getName().equalsIgnoreCase("resetmap")) {
			WorldBorder border = Bukkit.getWorlds().get(0).getWorldBorder();
			border.setCenter(Main.MAP_CENTERX, Main.MAP_CENTERZ);
			border.setSize(500);

			for (MapView mapView : Main.mapList) {
				for (int i = 1; i < mapView.getRenderers().size(); i++) {
					mapView.removeRenderer(mapView.getRenderers().get(i));
				}
				mapView.addRenderer(new Renderer());
			}

			sender.sendMessage(ChatColor.RED + "§lMAPの表示をリセットしました");
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("clearmap")) {
			Main.mapList = new ArrayList<MapView>();
			sender.sendMessage(ChatColor.RED + "§lMAPの保持情報をリセットしました");
			return true;
		}

		return false;
	}
}
