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

public class NextRenderer extends MapRenderer{
	MapRenderer thisRenderer = this;
	Main main;
	WorldBorder border;
	Location center;
	Location nextCenter;
	int size;
	double addx = Main.centerAddX;
	double addz = Main.centerAddZ;
	boolean smalling = false;

	public NextRenderer(MapView map, Main main, int size, int delay, int time){


		for(int i = 2; i < map.getRenderers().size(); i ++){
			map.removeRenderer(map.getRenderers().get(i));
		}
		this.main = main;

		border = Bukkit.getWorlds().get(0).getWorldBorder();
		center = border.getCenter().add(-Main.MAP_CENTERX, 0, -Main.MAP_CENTERZ + 150);

		this.size = size;
		int x = (int) (Math.random() * (border.getSize() - size)/2);
		int z = x;

		switch((int)(Math.random()*4)){
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
		nextCenter = border.getCenter().add(x, 0, z);
		nextCenter = nextCenter.add(-Main.MAP_CENTERX, 0, -Main.MAP_CENTERZ + 150);

		addx = Main.centerAddX + x;
		addz = Main.centerAddZ + z;

		new BukkitRunnable(){public void run(){
			Main.centerAddX = addx;
			Main.centerAddZ = addz;
			border.setSize(size, time);
			smalling = true;
		}}.runTaskLater(main, delay * 20L);
		new BukkitRunnable(){
			double addX = (nextCenter.getX() - center.getX()) / time;
			double addZ = (nextCenter.getZ() - center.getZ()) / time;
			int timeTmp = time;

			public void run(){
				border.setCenter(
						border.getCenter().add(
								addX,
								0,
								addZ
								)
						);

				timeTmp --;
				if(timeTmp == 0){
					smalling = false;
					this.cancel();

					map.removeRenderer(thisRenderer);
				}
		}}.runTaskTimer(main, delay* 20L, 20L);
	}

	@Override
	public void render(MapView arg0, MapCanvas mc, Player arg2) {
		double x = (nextCenter.getBlockX()/10 + addx*0.1);
		double z = (nextCenter.getBlockZ()/10 + addz*0.15);
		MapCursorCollection cursors = new MapCursorCollection();

		MapCursor leftup = cursors.addCursor(new MapCursor(
				(byte) ((x - size/10)*1.2 ),
				(byte) ((z - size/10) * 1.2 ),
				(byte) 14, MapCursor.Type.BLUE_POINTER.getValue(), true));
		MapCursor rightup = cursors.addCursor(new MapCursor(
				(byte) ((x + size/10)*1.2 ),
				(byte) ((z - size/10) * 1.2 ),
				(byte) 2, MapCursor.Type.BLUE_POINTER.getValue(), true));
		MapCursor leftbot = cursors.addCursor(new MapCursor(
				(byte) ((x + size/10)*1.2 ),
				(byte) ((z + size/10) * 1.2 ),
				(byte) 6, MapCursor.Type.BLUE_POINTER.getValue(), true));
		MapCursor rightbot = cursors.addCursor(new MapCursor(
				(byte) ((x - size/10)*1.2 ),
				(byte) ((z + size/10) * 1.2 ),
				(byte) 10, MapCursor.Type.BLUE_POINTER.getValue(), true));

		if(smalling){
			leftup.setType(MapCursor.Type.RED_POINTER);
			rightup.setType(MapCursor.Type.RED_POINTER);
			leftbot.setType(MapCursor.Type.RED_POINTER);
			rightbot.setType(MapCursor.Type.RED_POINTER);
		}

		mc.setCursors(cursors);

	}
}
