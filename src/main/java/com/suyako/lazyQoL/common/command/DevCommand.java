package com.suyako.lazyQoL.common.command;

import com.suyako.lazyQoL.core.EtherwarpTeleport;
import com.suyako.lazyQoL.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

import java.util.List;

public class DevCommand extends CommandBase {
    public static List<BlockPos> posList;
    public static boolean renderNode = false;
    Utils utils = new Utils();
    String[] Usage = {"/lazyQoLdev 主命令",
    "SelfName","aotvadd","aotvnext","aotvclear"
    };
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getCommandName() {
        return "lazyQoLdev";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 0){
            for (String UsageOneLine : Usage) {
                utils.devLog(UsageOneLine);
            }
        }
        if(args.length == 1){
            if (args[0].equalsIgnoreCase("selfname")) {
                utils.devLog("player name(with no dot):" + Minecraft.getMinecraft().thePlayer.getDisplayNameString());
            }
            if (args[0].equalsIgnoreCase("aotvadd")) {
                EtherwarpTeleport.add(new BlockPos(Minecraft.getMinecraft().thePlayer.getPositionVector()).down());
            }
            if (args[0].equalsIgnoreCase("aotvnext")) {
                EtherwarpTeleport.next();
            }
            if (args[0].equalsIgnoreCase("aotvclear")) {
                EtherwarpTeleport.clear();
            }
        }
    }
}
