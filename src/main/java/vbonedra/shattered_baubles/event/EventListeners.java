package vbonedra.shattered_baubles.event;

import net.xiaoyu233.fml.reload.event.MITEEvents;

public class EventListeners {

    public static void registerAllEvents() {
        MITEEvents.MITE_EVENT_BUS.register(new FMLEvents());
        RICEvents.register();
    }

}