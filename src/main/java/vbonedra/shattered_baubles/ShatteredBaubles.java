package vbonedra.shattered_baubles;


import fi.dy.masa.malilib.config.ConfigManager;
import net.xiaoyu233.fml.ModResourceManager;
import net.xiaoyu233.fml.reload.event.MITEEvents;
import vbonedra.shattered_baubles.event.FMLEvents;
import vbonedra.shattered_baubles.event.RICEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ShatteredBaubles implements ModInitializer, PreLaunchEntrypoint {
    public static final String MOD_NAME = "ShatteredBaubles";
    public static final String MOD_ID = "shattered_baubles";
    public static final String RESOURCE_ID = MOD_ID +":";

    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);


    @Override
    public void onInitialize() {
        ModResourceManager.addResourcePackDomain(MOD_ID);
        SBConfig.getInstance().load();
        ConfigManager.getInstance().registerConfig(SBConfig.getInstance());
        MITEEvents.MITE_EVENT_BUS.register(new FMLEvents());
        RICEvents.register();
    }


    @Override
    public void onPreLaunch() {
        System.out.println("[" + MOD_NAME + "] \"Early riser registering chat formatting\" or whatever it needs to print... why it needs to print it btw?");
    }

}
