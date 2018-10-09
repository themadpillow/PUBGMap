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

import main.Main;

public class Renderer extends MapRenderer {

	@Override
	public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
		lineRender(mapCanvas);
	}

	public void lineRender(MapCanvas mc) {
		WorldBorder worldBorder = Bukkit.getWorlds().get(0).getWorldBorder();
		Location center = Bukkit.getWorlds().get(0).getWorldBorder().getCenter();
		MapCursorCollection cursors = new MapCursorCollection();

		double mapX = (((center.getX() - (worldBorder.getSize() / 2)) - Main.MAP_CENTERX));
		double mapZ = (((center.getZ() - (worldBorder.getSize() / 2)) - Main.MAP_CENTERZ));
		MapCursor leftup = cursors.addCursor(new MapCursor(
				Byte.parseByte(String.valueOf((int) (mapX / 4))),
				Byte.parseByte(String.valueOf((int) (mapZ / 4 + 15))), (byte) 14,
				MapCursor.Type.SMALL_WHITE_CIRCLE.getValue(), true));
		cursors.addCursor(new MapCursor((byte) (leftup.getX() + 6),
				(byte) (leftup.getY()), (byte) 14,
				MapCursor.Type.SMALL_WHITE_CIRCLE.getValue(), true));
		cursors.addCursor(new MapCursor((byte) leftup.getX(),
				(byte) (leftup.getY() + 6), (byte) 14,
				MapCursor.Type.SMALL_WHITE_CIRCLE.getValue(), true));

		MapCursor leftbot = cursors.addCursor(new MapCursor(
				leftup.getX(),
				Byte.parseByte(String.valueOf((int) (leftup.getY() + worldBorder.getSize() / 4))), (byte) 14,
				MapCursor.Type.SMALL_WHITE_CIRCLE.getValue(), true));
		cursors.addCursor(new MapCursor((byte) (leftbot.getX() + 6),
				leftbot.getY(), (byte) 14,
				MapCursor.Type.SMALL_WHITE_CIRCLE.getValue(), true));
		cursors.addCursor(new MapCursor(leftbot.getX(),
				(byte) (leftbot.getY() - 6), (byte) 14,
				MapCursor.Type.SMALL_WHITE_CIRCLE.getValue(), true));

		MapCursor rightup = cursors.addCursor(new MapCursor(
				Byte.parseByte(String.valueOf((int) (leftup.getX() + worldBorder.getSize() / 4))),
				leftup.getY(), (byte) 14,
				MapCursor.Type.SMALL_WHITE_CIRCLE.getValue(), true));
		cursors.addCursor(new MapCursor((byte) (rightup.getX() - 6),
				(byte) (rightup.getY()), (byte) 14,
				MapCursor.Type.SMALL_WHITE_CIRCLE.getValue(), true));
		cursors.addCursor(new MapCursor((byte) rightup.getX(),
				(byte) (rightup.getY() + 6), (byte) 14,
				MapCursor.Type.SMALL_WHITE_CIRCLE.getValue(), true));

		MapCursor rightbot = cursors.addCursor(new MapCursor(
				rightup.getX(),
				leftbot.getY(), (byte) 14,
				MapCursor.Type.SMALL_WHITE_CIRCLE.getValue(), true));
		cursors.addCursor(new MapCursor((byte) (rightbot.getX() - 6),
				(byte) (rightbot.getY()), (byte) 14,
				MapCursor.Type.SMALL_WHITE_CIRCLE.getValue(), true));
		cursors.addCursor(new MapCursor((byte) rightbot.getX(),
				(byte) (rightbot.getY() - 6), (byte) 14,
				MapCursor.Type.SMALL_WHITE_CIRCLE.getValue(), true));

		mc.setCursors(cursors);
	}
}