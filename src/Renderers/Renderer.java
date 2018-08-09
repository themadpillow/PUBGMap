package Renderers;
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



}