package renderers;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapCursor;
import org.bukkit.map.MapCursorCollection;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import main.Main;

public class NextRenderer extends MapRenderer {
	int size;
	double mapX;
	double mapZ;
	
	public NextRenderer(int size, double mapX,double mapZ) {
		this.size = size;
		this.mapX = mapX;
		this.mapZ = mapZ;
	}
	
	@Override
	public void render(MapView arg0, MapCanvas mc, Player arg2) {
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

		if (Main.smalling) {
			leftup.setType(MapCursor.Type.RED_POINTER);
			rightup.setType(MapCursor.Type.RED_POINTER);
			leftbot.setType(MapCursor.Type.RED_POINTER);
			rightbot.setType(MapCursor.Type.RED_POINTER);
		}

		mc.setCursors(cursors);
	}
}
