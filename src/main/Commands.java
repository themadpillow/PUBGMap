package main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.WorldBorder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.map.MapView;

import renderers.Renderer;

public class Commands implements CommandExecutor {
	public Main Main;

	Commands(Main instance) {
		this.Main = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("next")) {
			if (args.length < 3)
				return false;

			Main.setNextBorder(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));

			return true;
		}

		if (cmd.getName().equalsIgnoreCase("givemap")) {
			if (args.length != 0) {
				if (Bukkit.getPlayerExact(args[0]) != null) {
					Main.giveMap(Bukkit.getPlayerExact(args[0]));
				}
			} else {
				for (Player p : Bukkit.getOnlinePlayers()) {
					Main.giveMap(p);
				}
			}

			return true;
		}

		if (cmd.getName().equalsIgnoreCase("resetmap")) {
			WorldBorder border = Bukkit.getWorlds().get(0).getWorldBorder();
			border.setCenter(Main.MAP_CENTERX, Main.MAP_CENTERZ);
			border.setSize(500);

			for (int i = 0; i < Main.mapList.size(); i++) {
				for (int j = 1; j < Main.mapList.get(i).getRenderers().size(); j++) {
					Main.mapList.get(i).removeRenderer(Main.mapList.get(i).getRenderers().get(j));
				}
				Main.mapList.get(i).addRenderer(new Renderer());
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
