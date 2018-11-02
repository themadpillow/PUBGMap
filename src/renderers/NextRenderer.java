package renderers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapCursor;
import org.bukkit.map.MapCursorCollection;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.scheduler.BukkitRunnable;

import main.Main;

public class NextRenderer extends MapRenderer {
	Main main;
	NextRenderer thisRenderer = this;
	boolean smalling = false;
	WorldBorder worldBorder;
	Location nextCenter;
	int size;

	public NextRenderer(MapView map, Main main, int size, int delay, int time) {
		for (int i = 2; i < map.getRenderers().size(); i++) {
			map.removeRenderer(map.getRenderers().get(i));
		}
		this.main = main;
		this.size = size;

		worldBorder = Bukkit.getWorlds().get(0).getWorldBorder();
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
		nextCenter = worldBorder.getCenter().add(x, 0, z);
		double addX = x / time;
		double addZ = z / time;

		new BukkitRunnable() {
			public void run() {
				worldBorder.setSize(size, time);
				smalling = true;
			}
		}.runTaskLater(main, delay * 20L);
		new BukkitRunnable() {
			int timeTmp = 0;

			public void run() {
				worldBorder.setCenter(
						worldBorder.getCenter().add(addX, 0, addZ));

				if (++timeTmp == time) {
					smalling = false;
					map.removeRenderer(thisRenderer);

					this.cancel();
				}
			}
		}.runTaskTimer(main, delay * 20L, 20L);
	}

	@Override
	public void render(MapView arg0, MapCanvas mc, Player arg2) {
		double mapX = (nextCenter.getX() - (size / 2)) - Main.MAP_CENTERX;
		double mapZ = (nextCenter.getZ() - (size / 2)) - Main.MAP_CENTERZ;
		MapCursorCollection cursors = new MapCursorCollection();

		MapCursor leftup = cursors.addCursor(new MapCursor(
				Byte.parseByte(String.valueOf((int) (mapX / 4 + 2))),
				Byte.parseByte(String.valueOf((int) (mapZ / 4 + 15))),
				(byte) 14, MapCursor.Type.BLUE_POINTER.getValue(), true));
		MapCursor leftbot = cursors.addCursor(new MapCursor(
				leftup.getX(),
				Byte.parseByte(String.valueOf((int) (leftup.getY() + size / 4))),
				(byte) 10, MapCursor.Type.BLUE_POINTER.getValue(), true));
		MapCursor rightup = cursors.addCursor(new MapCursor(
				Byte.parseByte(String.valueOf((int) (leftup.getX() + size / 4))),
				leftup.getY(),
				(byte) 2, MapCursor.Type.BLUE_POINTER.getValue(), true));
		MapCursor rightbot = cursors.addCursor(new MapCursor(
				rightup.getX(),
				leftbot.getY(),
				(byte) 6, MapCursor.Type.BLUE_POINTER.getValue(), true));

		if (smalling) {
			leftup.setType(MapCursor.Type.RED_POINTER);
			rightup.setType(MapCursor.Type.RED_POINTER);
			leftbot.setType(MapCursor.Type.RED_POINTER);
			rightbot.setType(MapCursor.Type.RED_POINTER);
		}

		mc.setCursors(cursors);
	}
}
