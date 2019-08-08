package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldBorder;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapView;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import renderers.NextRenderer;
import renderers.Renderer;

public class Main extends JavaPlugin implements Listener {

	private String map0path = null;
	public static double MAP_CENTERX = -778.0;
	public static double MAP_CENTERZ = 464.0;
	public static boolean smalling = false;

	List<MapView> mapList = new ArrayList<MapView>();

	public void onEnable() {
		map0path = Bukkit.getWorlds().get(0).getWorldFolder().getPath() + "\\data\\map_0.dat";
		Commands Commands = new Commands(this);
		getCommand("next").setExecutor(Commands);
		getCommand("givemap").setExecutor(Commands);
		getCommand("resetmap").setExecutor(Commands);
		getCommand("clearmap").setExecutor(Commands);

		WorldBorder border = Bukkit.getWorlds().get(0).getWorldBorder();
		border.setCenter(MAP_CENTERX, MAP_CENTERZ);
		border.setSize(500);

		/*
		for (Player player : Bukkit.getOnlinePlayers()) {
			Inventory inventory = player.getInventory();
			if (inventory.contains(Material.MAP)) {
				inventory.clear(inventory.first(Material.MAP));
				giveMap(player);
			}
		}
		*/
	}

	public ItemStack createMap() {
		if (Bukkit.getMap((short) mapList.size()) == null) {
			File fileIn = new File(map0path);
			File fileOut = new File(new File(map0path).getParentFile().getPath() + "\\map_" + mapList.size() + ".dat");

			// FileChannelクラスのオブジェクトを生成する
			try {
				FileChannel inCh = new FileInputStream(fileIn).getChannel();
				FileChannel outCh = new FileOutputStream(fileOut).getChannel();

				//transferToメソッドを使用してファイルをコピーする
				inCh.transferTo(0, inCh.size(), outCh);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		MapView map = Bukkit.getMap((short) mapList.size());
		ItemStack itemMap = new ItemStack(Material.MAP);

		for (int i = 1; i < map.getRenderers().size(); i++) {
			map.removeRenderer(map.getRenderers().get(i));
		}
		map.addRenderer(new Renderer());

		itemMap.setDurability((short) map.getId());
		mapList.add(map);

		return itemMap;
	}

	void setNextBorder(int size, int delay, int time) {
		WorldBorder worldBorder = Bukkit.getWorlds().get(0).getWorldBorder();
		double x = (Math.random() * (worldBorder.getSize() - size) / 2);
		double z = x;

		switch ((int) (Math.random() * 4)) {
		case 0:
			x = -x;
		case 1:
			z = -z;
			break;
		case 2:
			x = -x;
			break;
		case 3:
			break;
		}
		Location nextCenter = worldBorder.getCenter().add(x, 0, z);

		new BukkitRunnable() {
			public void run() {
				worldBorder.setSize(size, time);
				smalling = true;
			}
		}.runTaskLater(this, delay * 20L);

		double addX = x / time;
		double addZ = z / time;

		new BukkitRunnable() {
			int count = time;

			public void run() {
				worldBorder.setCenter(worldBorder.getCenter().add(addX, 0, addZ));

				if (--count == 0) {
					smalling = false;

					for (MapView mapView : mapList) {
						for (int i = 2; i < mapView.getRenderers().size(); i++) {
							mapView.removeRenderer(mapView.getRenderers().get(i));
						}
					}

					worldBorder.setCenter(nextCenter);
					this.cancel();
				}
			}
		}.runTaskTimer(this, delay * 20L, 20L);
		double mapX = (nextCenter.getX() - (size / 2)) - Main.MAP_CENTERX;
		double mapZ = (nextCenter.getZ() - (size / 2)) - Main.MAP_CENTERZ;

		for (MapView mapView : mapList) {
			for (int i = 2; i < mapView.getRenderers().size(); i++) {
				mapView.removeRenderer(mapView.getRenderers().get(i));
			}
			mapView.addRenderer(new NextRenderer(size, mapX, mapZ));
		}
	}
}
