package com.minerarcana.runecarved.api.caster;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class CasterEntityPlayer implements ICaster {

    private EntityPlayer player;

    public CasterEntityPlayer(EntityPlayer player) {
        this.setPlayer(player);
    }

    @Override
    public Vec3d getCastDirection() {
        return getPlayer().getLookVec();
    }

    @Override
    public Vec3d getCastPosition() {
        return getPlayer().getPositionVector().add(new Vec3d(0, getPlayer().getEyeHeight(), 0));
    }

    @Override
    public World getWorld() {
        // TODO Auto-generated method stub
        return getPlayer().getEntityWorld();
    }

    @Override
    @Nullable
    public NonNullList<ItemStack> getArmor() {
        return player.inventory.armorInventory;
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public void setPlayer(EntityPlayer player) {
        this.player = player;
    }
}
