package vbonedra.shattered_baubles;


import fi.dy.masa.malilib.config.ConfigManager;
import net.xiaoyu233.fml.ModResourceManager;
import vbonedra.shattered_baubles.event.EventListeners;
import vbonedra.shattered_baubles.util.ConfigShatteredBaubles;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ShatteredBaubles implements ModInitializer, PreLaunchEntrypoint {
    public static final String MODID  = "ShatteredBaubles";
    public static final String NAMESPACE = "shattered_baubles";

    public static final Logger LOGGER = LogManager.getLogger(MODID);


    @Override
    public void onInitialize() {
        ModResourceManager.addResourcePackDomain(NAMESPACE);
        EventListeners.registerAllEvents();

        ConfigShatteredBaubles.getInstance().load();
        ConfigManager.getInstance().registerConfig(ConfigShatteredBaubles.getInstance());

    }


    @Override
    public void onPreLaunch() {
        System.out.println("[" + MODID + "] \"Early riser registering chat formatting\" or whatever it needs to print");
    }

}
