package vbonedra.shattered_baubles.util;


import net.minecraft.EntityPlayer;


public class SBSoundMaster {
    public static void playRandomizedSoundAtPlayer(EntityPlayer player, String sound, float volume, float volume_randomized, float pitch, float pitch_randomized) {
        player.worldObj.playSoundAtEntity(
                player,
                sound,
                volume + player.worldObj.rand.nextFloat() * volume_randomized,
                pitch + player.worldObj.rand.nextFloat() * pitch_randomized
        );
    }
    public static void playRandomizedSoundAtPlayer(Object player, Object sound, Object volume, Object pitch) {
        playRandomizedSoundAtPlayer(
                (EntityPlayer) player,
                (String) String.valueOf(sound),
                ((Number) volume).floatValue(),
                0.0f,
                ((Number) pitch).floatValue(),
                0.1f
        );
    }
    public static void playRandomizedSoundAtPlayer(Object player, Object sound, Object volume, Object volume_randomized, Object pitch, Object pitch_randomized) {
        playRandomizedSoundAtPlayer(
                (EntityPlayer) player,
                (String) String.valueOf(sound),
                ((Number) volume).floatValue(),
                ((Number) volume_randomized).floatValue(),
                ((Number) pitch).floatValue(),
                ((Number) pitch_randomized).floatValue()
        );
    }
}
