package vbonedra.shattered_baubles.mixin;


import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import vbonedra.shattered_baubles.SBItems;
import vbonedra.shattered_baubles.SBConfig;


@Mixin(EntityGhoul.class)
public class EntityGhoulMixin extends EntityMob {
    @Unique private static final float bottle_of_ghoul_blood_PROBABILITY_GhoulDeathDrop = (float) SBConfig.bottle_of_ghoul_blood_PROBABILITY_GhoulDeathDrop.getDoubleValue();

    public EntityGhoulMixin(World par1World) {
        super(par1World);
    }

    protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
        if (
                recently_hit_by_player
                && !this.has_taken_massive_fall_damage
                && this.rand.nextInt(this.getBaseChanceOfRareDrop()) <
                        (int) (EntityGhoulMixin.bottle_of_ghoul_blood_PROBABILITY_GhoulDeathDrop * this.getBaseChanceOfRareDrop()) + damage_source.getLootingModifier() * 2
        ) {
            this.dropItem(SBItems.bottle_of_ghoul_blood, 1);
        }
        super.dropFewItems(recently_hit_by_player, damage_source);
    }
}
