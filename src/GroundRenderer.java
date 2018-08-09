import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

/*
 * 一度使うだけで解決したが一応保存しておく
 */

public class GroundRenderer extends MapRenderer {
	boolean painted = false;

	@Override
	public void render(MapView map, MapCanvas mc, Player arg2) {
		if(painted){
			return;
		}
		else{
			List<Byte> pixelList = new ArrayList<Byte>();

			for(int i = 0; i < 128; i ++){
				for(int j = 0; j < 128; j ++){
					pixelList.add(mc.getPixel(i, j));
				}
			}

			for(int i = 0; i < map.getRenderers().size(); i ++){
				if(map.getRenderers().get(i) != this)
					map.removeRenderer(map.getRenderers().get(i));
			}
			for(int i = 0; i < 128; i ++){
				for(int j = 0; j < 128; j ++){

					Bukkit.broadcastMessage(""+pixelList.get(i*128 + j));
					mc.setPixel(i, j, pixelList.get(i*128 + j));
				}
			}
		}

		painted = true;
	}
}
