package com.minerarcana.runecarved.spell;

import com.minerarcana.runecarved.api.caster.ICaster;
import com.minerarcana.runecarved.api.entity.EntityProjectileSpell;
import com.minerarcana.runecarved.api.spell.ProjectileSpell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

import static com.minerarcana.runecarved.Runecarved.MODID;

public class IceBurst extends ProjectileSpell {
    public IceBurst() {
        super(new ResourceLocation(MODID, "ice_burst"));
    }

    @Override
    public void cast(ICaster caster) {
        for (int i = 0; i < 3; i++) {
            super.cast(caster);
        }
    }

    @Override
    public EntityProjectileSpell getEntityProjectileSpell(World world) {
        return new EntityProjectileSpell(world, this);
    }

    @Override
    public void onImpact(EntityProjectileSpell entitySpell, @Nonnull Entity entity) {
        entity.attackEntityFrom(DamageSource.causeThrownDamage(entitySpell, entitySpell.getThrower()),
                // TODO Spell Resistances
                entity instanceof EntityBlaze ? 6f : 3f);
    }

    @Override
    public void onImpact(EntityProjectileSpell entitySpell, BlockPos pos, EnumFacing impactedSize) {

    }
}
