package vbonedra.shattered_baubles.event;

import moddedmite.rustedironcore.api.event.Handlers;

public class RICEvents extends Handlers {

    public static void register() {
        Handlers.LootTable.register(new SBLootTable());
    }
}