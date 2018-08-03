

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
	public Main Main;
	Commands(Main instance){
		this.Main = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(cmd.getName().equalsIgnoreCase("next")){
			if(args.length < 3)
				return false;

			Main.setNextBorder(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));

			return true;
		}

		if(cmd.getName().equalsIgnoreCase("givemap")){
			if(args.length != 0){
				if(Bukkit.getPlayerExact(args[0]) != null){
					Main.giveMap(Bukkit.getPlayerExact(args[0]));
				}
			}
			else{
				for(Player p : Bukkit.getOnlinePlayers()){
					Main.giveMap(p);
				}
			}
		}
		return false;
	}
}
