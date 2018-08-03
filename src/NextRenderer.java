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

public class NextRenderer extends MapRenderer{
	MapRenderer thisRenderer = this;
	Main main;
	WorldBorder border;
	Location center;
	Location nextCenter;
	int size;

	public NextRenderer(MapView map, Main main, int size, int delay, int time){

		this.main = main;

		border = Bukkit.getWorlds().get(0).getWorldBorder();
		center = border.getCenter().add(-Main.MAP_CENTERX, 0, -Main.MAP_CENTERZ + 150);

		this.size = size;
		int x = (int) (Math.random() * (border.getSize() - size));
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
		Main.centerAddX += x;
		Main.centerAddZ += z;
		nextCenter = border.getCenter().add(x, 0, z);
		nextCenter = nextCenter.add(-Main.MAP_CENTERX, 0, -Main.MAP_CENTERZ + 150);


		new BukkitRunnable(){public void run(){
			border.setSize(size, time);
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
					this.cancel();

					map.removeRenderer(thisRenderer);
				}
		}}.runTaskTimer(main, delay* 20L, time*5L);
	}

	@Override
	public void render(MapView arg0, MapCanvas mc, Player arg2) {
		MapCursorCollection cursors = new MapCursorCollection();

		cursors.addCursor(new MapCursor(
				(byte) ((nextCenter.getBlockX()/10 + Main.centerAddX*0.1) - size/10*1.2 ),
				(byte) ((nextCenter.getBlockZ()/10 + Main.centerAddZ*0.15) - size/10 ),
				(byte) 14, MapCursor.Type.RED_POINTER.getValue(), true));
		cursors.addCursor(new MapCursor(
				(byte) ((nextCenter.getBlockX()/10 + Main.centerAddX*0.1) + size/10*1.2 ),
				(byte) ((nextCenter.getBlockZ()/10 + Main.centerAddZ*0.15) - size/10 ),
				(byte) 2, MapCursor.Type.RED_POINTER.getValue(), true));
		cursors.addCursor(new MapCursor(
				(byte) ((nextCenter.getBlockX()/10 + Main.centerAddX*0.1) + size/10*1.2 ),
				(byte) ((nextCenter.getBlockZ()/10 + Main.centerAddZ*0.15) + size/10 ),
				(byte) 6, MapCursor.Type.RED_POINTER.getValue(), true));
		cursors.addCursor(new MapCursor(
				(byte) ((nextCenter.getBlockX()/10 + Main.centerAddX*0.1) - size/10*1.2 ),
				(byte) ((nextCenter.getBlockZ()/10 + Main.centerAddZ*0.15) + size/10 ),
				(byte) 10, MapCursor.Type.RED_POINTER.getValue(), true));


		mc.setCursors(cursors);

	}
}
