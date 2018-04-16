package com.minerarcana.runecarved.api.caster;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public interface ICaster {
    Vec3d getCastDirection();

    Vec3d getCastPosition();

    World getWorld();

    @Nullable
    NonNullList<ItemStack> getArmor();
}
