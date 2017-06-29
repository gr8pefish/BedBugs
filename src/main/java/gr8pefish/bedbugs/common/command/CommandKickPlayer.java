package gr8pefish.bedbugs.common.command;

import gr8pefish.bedbugs.common.BedBugs;
import gr8pefish.bedbugs.common.lib.Logger;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

// Registered server side only (check the bottom of BedBugs.java)
public class CommandKickPlayer extends CommandBase {

    // The strings used in the command in one place
    private final String BEDBUGS = "bedbugs";
    private final String KICK = "kick";
    private final String PLAYER = "[player]";
    private final String KICK_COMMAND = "/"+BEDBUGS+" "+KICK+" "+PLAYER;

    @Override
    public String getName() {
        return BEDBUGS;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return KICK_COMMAND;
    }

    @Override
    public int getRequiredPermissionLevel(){
        return 2; //Best guess, not sure on the perfect level
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] params) throws CommandException {
        if (params.length > 0 && params.length <= 2) {
            if (params[0].equalsIgnoreCase(KICK)) { //kick command
                if (params.length == 2) { //name supplied
                    try {
                        BedBugs.proxy.handleKick(getPlayer(server, sender, params[1]));
                    } catch (Exception e) {
                        Logger.warn("Couldn't process command to kick the player!");
                    }
                } else { //do it for whoever did the command
                    try {
                        BedBugs.proxy.handleKick((EntityPlayerMP)sender.getCommandSenderEntity());
                    } catch (Exception e) {
                        Logger.warn("Couldn't process command to kick the player!");
                    }
                }
            }
        } else {
            throw new CommandException(getUsage(sender));
        }
    }

    @Nonnull
    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        List<String> tabCompletion = new ArrayList<String>();
        if (args.length <= 1) //no name, match string
            tabCompletion.addAll(getListOfStringsMatchingLastWord(args, KICK));
        else //match name
            tabCompletion.addAll(getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()));
        return tabCompletion;
    }

}
