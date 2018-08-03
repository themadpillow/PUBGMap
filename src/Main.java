import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapView;
import org.bukkit.plugin.java.JavaPlugin;



public class Main extends JavaPlugin implements Listener{

	public static double MAP_CENTERX = -778.0;
	public static double MAP_CENTERZ = 464.0;

	public static double centerAddX = 0;
	public static double centerAddZ = 0;

	private ItemStack itemMap = new ItemStack(Material.MAP);
	private MapView map;



	public void onEnable(){
		Commands Commands = new Commands(this);
		getCommand("next").setExecutor(Commands);

		map = Bukkit.getMap((short)7);

		Bukkit.getPluginManager().registerEvents(this, this);

		for(int i = 1; i < map.getRenderers().size(); i ++){
			map.removeRenderer(map.getRenderers().get(i));
		}

		map.addRenderer(new Renderer());
		itemMap.setDurability(map.getId());


		WorldBorder border = Bukkit.getWorlds().get(0).getWorldBorder();
		border.setCenter(MAP_CENTERX, MAP_CENTERZ);

		border.setSize(500);

		for(Player p : Bukkit.getOnlinePlayers()){
			p.getInventory().addItem(itemMap);
			p.sendMap(map);
		}

	}

	void setNextBorder(int size, int delay, int time){
			map.addRenderer(new NextRenderer(map, Main.this, size, delay, time));
	}
}
