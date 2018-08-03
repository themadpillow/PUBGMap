

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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
		return false;
	}
}
