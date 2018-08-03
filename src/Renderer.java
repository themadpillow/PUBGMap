import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapCursor;
import org.bukkit.map.MapCursorCollection;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class Renderer extends MapRenderer{
	WorldBorder border = Bukkit.getWorlds().get(0).getWorldBorder();
	Location center = border.getCenter().add(-Main.MAP_CENTERX, 0, -Main.MAP_CENTERZ + 150);

	public Renderer(){

	}

	@Override
	public void render(MapView map, MapCanvas mc, Player p) {
		border = Bukkit.getWorlds().get(0).getWorldBorder();
		center = border.getCenter().add(-Main.MAP_CENTERX, 0, -Main.MAP_CENTERZ + 150);

		MapCursorCollection cursors = new MapCursorCollection();

		MapCursor centerCursor = new MapCursor((byte)(center.getBlockX()/10 + Main.centerAddX*0.1), (byte)(center.getBlockZ()/10 + Main.centerAddZ*0.15), (byte) 0, MapCursor.Type.SMALL_WHITE_CIRCLE.getValue(), true);
		cursors.addCursor(centerCursor);

		mc.setCursors(cursors);

		lineRender(mc);
	}

	public void lineRender(MapCanvas mc){
		double centerX = center.getBlockX()/10 + Main.centerAddX*0.1;
		double centerY = center.getBlockZ()/10 + Main.centerAddZ*0.15;
		MapCursorCollection cursors = new MapCursorCollection();

		MapCursor leftup = cursors.addCursor(new MapCursor(
				(byte) (centerX - border.getSize()/10*1.2 ),
				(byte) (centerY - border.getSize()/10*1.2 ), (byte) 14,
				MapCursor.Type.SMALL_WHITE_CIRCLE.getValue(), true));
		cursors.addCursor(new MapCursor((byte)(leftup.getX() + 6),
				(byte) (leftup.getY()),(byte) 14,
				MapCursor.Type.SMALL_WHITE_CIRCLE.getValue(), true));
		cursors.addCursor(new MapCursor((byte)leftup.getX(),
				(byte) (leftup.getY() + 6),(byte) 14,
				MapCursor.Type.SMALL_WHITE_CIRCLE.getValue(), true));


		MapCursor rightup = cursors.addCursor(new MapCursor(
				(byte) (centerX + border.getSize()/10*1.2 ),
				(byte) (centerY - border.getSize()/10*1.2 ), (byte) 14,
				MapCursor.Type.SMALL_WHITE_CIRCLE.getValue(), true));
		cursors.addCursor(new MapCursor((byte)(rightup.getX() - 6),
				(byte) (rightup.getY()),(byte) 14,
				MapCursor.Type.SMALL_WHITE_CIRCLE.getValue(), true));
		cursors.addCursor(new MapCursor((byte)rightup.getX(),
				(byte) (rightup.getY() + 6),(byte) 14,
				MapCursor.Type.SMALL_WHITE_CIRCLE.getValue(), true));

		MapCursor leftbot = cursors.addCursor(new MapCursor(
				(byte) (centerX - border.getSize()/10*1.2 ),
				(byte) (centerY + border.getSize()/10*1.2 ), (byte) 14,
				MapCursor.Type.SMALL_WHITE_CIRCLE.getValue(), true));
		cursors.addCursor(new MapCursor((byte)(leftbot.getX() + 6),
				(byte) (leftbot.getY()),(byte) 14,
				MapCursor.Type.SMALL_WHITE_CIRCLE.getValue(), true));
		cursors.addCursor(new MapCursor((byte)leftbot.getX(),
				(byte) (leftbot.getY() - 6),(byte) 14,
				MapCursor.Type.SMALL_WHITE_CIRCLE.getValue(), true));

		MapCursor rightbot = cursors.addCursor(new MapCursor(
				(byte) (centerX + border.getSize()/10*1.2 ),
				(byte) (centerY + border.getSize()/10*1.2 ), (byte) 14,
				MapCursor.Type.SMALL_WHITE_CIRCLE.getValue(), true));
		cursors.addCursor(new MapCursor((byte)(rightbot.getX() - 6),
				(byte) (rightbot.getY()),(byte) 14,
				MapCursor.Type.SMALL_WHITE_CIRCLE.getValue(), true));
		cursors.addCursor(new MapCursor((byte)rightbot.getX(),
				(byte) (rightbot.getY() - 6),(byte) 14,
				MapCursor.Type.SMALL_WHITE_CIRCLE.getValue(), true));

		mc.setCursors(cursors);
	}

	public static byte getCardinalDirection(Player player) {
		double rotation = (player.getLocation().getYaw() - 90) % 360;
		if (rotation < 0) {
			rotation += 360.0;
		}
		if (0 <= rotation && rotation < 22.5) {
			return 8;
		} else if (22.5 <= rotation && rotation < 67.5) {
			return 10;
		} else if (67.5 <= rotation && rotation < 112.5) {
			return 12;
		} else if (112.5 <= rotation && rotation < 157.5) {
			return 14;
		} else if (157.5 <= rotation && rotation < 202.5) {
			return 0;
		} else if (202.5 <= rotation && rotation < 247.5) {
			return 2;
		} else if (247.5 <= rotation && rotation < 292.5) {
			return 4;
		} else if (292.5 <= rotation && rotation < 337.5) {
			return 6;
		} else if (337.5 <= rotation && rotation < 360.0) {
			return 8;
		} else {
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