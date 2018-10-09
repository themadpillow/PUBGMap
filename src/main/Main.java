package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapView;
import org.bukkit.plugin.java.JavaPlugin;

import renderers.NextRenderer;
import renderers.Renderer;

public class Main extends JavaPlugin implements Listener {

	private String map0path = null;
	public static double MAP_CENTERX = -778.0;
	public static double MAP_CENTERZ = 464.0;


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

		for (Player player : Bukkit.getOnlinePlayers()) {
			Inventory inventory = player.getInventory();
			if (inventory.contains(Material.MAP)) {
				inventory.clear(inventory.first(Material.MAP));
				giveMap(player);
			}
		}
	}

	public void giveMap(Player p) {
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
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		MapView map = Bukkit.getMap((short) mapList.size());
		ItemStack itemMap = new ItemStack(Material.MAP);

		for (int i = 1; i < map.getRenderers().size(); i++) {
			map.removeRenderer(map.getRenderers().get(i));
		}
		map.addRenderer(new Renderer());

		itemMap.setDurability(map.getId());
		p.getInventory().addItem(itemMap);
		mapList.add(map);
	}

	void setNextBorder(int size, int delay, int time) {
		for (MapView mapView : mapList) {
			mapView.addRenderer(new NextRenderer(mapView, Main.this, size, delay, time));
		}
	}
}
