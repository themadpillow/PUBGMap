package Renderers;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapCursor;
import org.bukkit.map.MapCursorCollection;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import main.Main;

public class PlayerRenderer extends MapRenderer{

	@Override
	public void render(MapView mv, MapCanvas mc, Player p) {
		Location loc = p.getLocation().add(-Main.MAP_CENTERX, 0, -Main.MAP_CENTERZ + 70);
		MapCursorCollection cursors = new MapCursorCollection();
		cursors.addCursor(
				new MapCursor(
						(byte)(loc.getX() * 0.23),
						(byte)(loc.getZ() * 0.23),
						getCardinalDirection(p),
						MapCursor.Type.WHITE_POINTER.getValue(), true));

		mc.setCursors(cursors);
	}

	public static byte getCardinalDirection(Player player) {
		double rotation = player.getLocation().getYaw();
		if (0 <= rotation && rotation < 22.5) {
			return 0;
		}
		else if (22.5 <= rotation && rotation < 45) {
			return 1;
		}
		else if (45 <= rotation && rotation < 67.5) {
			return 2;
		}
		else if (67.5 <= rotation && rotation < 90) {
			return 3;
		}
		else if (90 <= rotation && rotation < 112.5) {
			return 4;
		}
		else if (112.5 <= rotation && rotation < 135) {
			return 5;
		}
		else if (135 <= rotation && rotation < 157.5) {
			return 6;
		}
		else if (157.5 <= rotation && rotation < 180) {
			return 7;
		}
		else if (180 <= rotation && rotation < 202.5) {
			return 8;
		}
		else if (202.5 <= rotation && rotation < 225) {
			return 9;
		}
		else if (225 <= rotation && rotation < 247.5) {
			return 10;
		}
		else if (247.5 <= rotation && rotation < 270) {
			return 11;
		}
		else if (270 <= rotation && rotation < 292.5) {
			return 12;
		}
		else if (292.5 <= rotation && rotation < 315) {
			return 13;
		}
		else if (315 <= rotation && rotation < 337.5) {
			return 14;
		}
		else if (337.5 <= rotation && rotation < 360.0) {
			return 15;
		}
		else {
			return 0;
		}
		/*
		 * 0 = South
		 * 1 = South-West-South
		 * 2 = South-West
		 * 3 = South-West-West
		 * 4 = West
		 * 5 = North-West-West
		 * 6 = North-West
		 * 7 = North-West-North
		 * 8 = North
		 * 9 = North-East-North
		 * 10 = North-East
		 * 11 = North-East-East
		 * 12 = East
		 * 13 = South-East-East
		 * 14 = South-East
		 * 15 = South-East-South
		 */
	}
}
