package com.suyako.lazyQoL.common.command;

import com.suyako.LazyQoL;
import com.suyako.lazyQoL.common.function.FakeBan;
import com.suyako.lazyQoL.core.FunctionManager.FunctionManager;
import com.suyako.lazyQoL.common.function.CustomItemName;
import com.suyako.lazyQoL.core.Pathfinder;
import com.suyako.lazyQoL.core.PathfinderProxy;
import com.suyako.lazyQoL.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CommandManager extends CommandBase {
    Utils utils = new Utils();
    String[] Usage = {"/lazyQoL maincommand",
            "/lazyQoL ss <SCALING> Super Screenshot",
            "/lazyQoL getUUID get item UUID",
            "/lazyQoL goto <X> <Y> <Z> auto find way to go",
            "/lazyQoL fakeban <reason> Make FakeBan"
    };

    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getCommandName() {
        return "lazyQoL";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            for (String UsageOneLine : Usage) {
                utils.print(UsageOneLine);
            }
        }
        if (args.length == 1) {
            if (args[0].equals("help")) {
                for (String UsageOneLine : Usage) {
                    utils.print(UsageOneLine);
                }
            }
            if (args[0].equalsIgnoreCase("getUUID")) {
                if(Minecraft.getMinecraft().thePlayer.getHeldItem() != null) {
                    String uuid = Utils.getUUIDForItem(Minecraft.getMinecraft().thePlayer.getHeldItem());
                    if(uuid != null){
                        utils.print("UUID: " + uuid + " copid");
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        Transferable trans = new StringSelection(uuid);
                        clipboard.setContents(trans, null);
                    } else {
                        utils.print("no UUID");
                    }
                } else {
                    utils.print("no UUID");
                }
            }

        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("ss")) {
                LazyQoL.ImageScaling = Integer.parseInt(args[1]);
                utils.print("setting done");
            }
        }
        if (args.length >= 2) {
            if (args[0].equalsIgnoreCase("fakeban")) {
                String str = "";
                boolean skipped = false;
                for(String string : args) {
                    if(!skipped){
                        skipped = true;
                    } else {
                        str = str + string + " ";
                    }
                }
                FakeBan.active(str);
            }
        }
        if (args.length == 5) {
            if(args[0].equalsIgnoreCase("goto")){
                try {
                    PathfinderProxy.calcPathDistance(new BlockPos(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3])), Integer.parseInt(args[4]));
                    if (!PathfinderProxy.running && Pathfinder.hasPath()) {
                        if (!FunctionManager.getStatus("Pathfinding")) {
                            FunctionManager.setStatus("Pathfinding", true);
                        }
                    }
                } catch (Exception e) {
                    Utils.print("error");
                    e.printStackTrace();
                }

            }
        }
    }
}


