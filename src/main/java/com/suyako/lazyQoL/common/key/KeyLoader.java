package com.suyako.lazyQoL.common.key;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class KeyLoader {
    public static KeyBinding SelectGuiUP;
    public static KeyBinding SelectGuiDown;
    public static KeyBinding SelectGuiEnter;
    public static KeyBinding SelectGuiBack;

    public static KeyBinding OpenClickGUI;
    public static KeyBinding OpenBindGUI;

    public static KeyBinding OpenConsole;

    public static KeyBinding AddAOTV;
    public static KeyBinding ClearAOTV;
    public static KeyBinding UndoAOTV;
    public static KeyBinding LoadAOTV;

    public static KeyBinding GhostBlock;
    public static KeyBinding InstantSwitch;

    public static KeyBinding HugeScreenshot;

    public KeyLoader()
    {
        KeyLoader.SelectGuiUP = new KeyBinding("LazyQoL SelectGui up", Keyboard.KEY_UP, "LazyQoL-SkyBlock");
        KeyLoader.SelectGuiDown = new KeyBinding("LazyQoL SelectGui down", Keyboard.KEY_DOWN, "LazyQoL-SkyBlock");
        KeyLoader.SelectGuiEnter = new KeyBinding("LazyQoL SelectGui chosen", Keyboard.KEY_RIGHT, "LazyQoL-SkyBlock");
        KeyLoader.SelectGuiBack = new KeyBinding("LazyQoL SelectGui return upper menu", Keyboard.KEY_LEFT, "LazyQoL-SkyBlock");

        KeyLoader.OpenClickGUI= new KeyBinding("LazyQoL open ClickGUI", Keyboard.KEY_RSHIFT, "LazyQoL-SkyBlock");
        KeyLoader.OpenBindGUI= new KeyBinding("LazyQoL open KeyBindGUI", Keyboard.KEY_B, "LazyQoL-SkyBlock");
        KeyLoader.OpenConsole= new KeyBinding("Open Console", Keyboard.KEY_GRAVE, "LazyQoL-SkyBlock");

        KeyLoader.GhostBlock = new KeyBinding("create Air Ghost Block", Keyboard.KEY_G, "LazyQoL-SkyBlock");
        KeyLoader.InstantSwitch = new KeyBinding("use InstantSwitch", Keyboard.KEY_R, "LazyQoL-SkyBlock");

        KeyLoader.AddAOTV = new KeyBinding("add EtherWarp cord", Keyboard.KEY_V, "LazyQoL-SkyBlock");
        KeyLoader.ClearAOTV = new KeyBinding("clear EtherWarp cord", Keyboard.KEY_C, "LazyQoL-SkyBlock");
        KeyLoader.UndoAOTV = new KeyBinding("undo EtherWarp cord", Keyboard.KEY_Z, "LazyQoL-SkyBlock");
        KeyLoader.LoadAOTV = new KeyBinding("load EtherWarp cord", Keyboard.KEY_L, "LazyQoL-SkyBlock");

        KeyLoader.HugeScreenshot = new KeyBinding("大型截图", Keyboard.KEY_NUMPAD2, "LazyQoL-SkyBlock");

        ClientRegistry.registerKeyBinding(KeyLoader.SelectGuiUP);
        ClientRegistry.registerKeyBinding(KeyLoader.SelectGuiDown);
        ClientRegistry.registerKeyBinding(KeyLoader.SelectGuiEnter);
        ClientRegistry.registerKeyBinding(KeyLoader.SelectGuiBack);

        ClientRegistry.registerKeyBinding(KeyLoader.GhostBlock);
        ClientRegistry.registerKeyBinding(KeyLoader.InstantSwitch);

        ClientRegistry.registerKeyBinding(KeyLoader.AddAOTV);
        ClientRegistry.registerKeyBinding(KeyLoader.ClearAOTV);
        ClientRegistry.registerKeyBinding(KeyLoader.UndoAOTV);
        ClientRegistry.registerKeyBinding(KeyLoader.LoadAOTV);

        ClientRegistry.registerKeyBinding(KeyLoader.OpenClickGUI);
        ClientRegistry.registerKeyBinding(KeyLoader.OpenBindGUI);

        ClientRegistry.registerKeyBinding(KeyLoader.OpenConsole);


        ClientRegistry.registerKeyBinding(KeyLoader.HugeScreenshot);
    }
}
