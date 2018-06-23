package com.minerarcana.runecarved.tileentity;

import com.minerarcana.runecarved.RunecarvedContent;
import com.minerarcana.runecarved.container.ContainerCarvingTable;
import com.minerarcana.runecarved.gui.GuiCarvingTable;
import com.teamacronymcoders.base.guisystem.IHasGui;
import com.teamacronymcoders.base.tileentities.TileEntityBase;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityCarvingTable extends TileEntityBase implements IHasGui {

    private int searchRadius;
    private TileEntityRuneIndex index;

    public TileEntityCarvingTable() {
        super();
    }

    @Override
    public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
        // TODO Auto-generated method stub
        return new GuiCarvingTable(new ContainerCarvingTable(entityPlayer, world, this), this);
    }

    @Override
    public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
        // TODO Auto-generated method stub
        return new ContainerCarvingTable(entityPlayer, world, this);
    }

    @Override
    protected void readFromDisk(NBTTagCompound data) {
    }

    @Override
    protected NBTTagCompound writeToDisk(NBTTagCompound data) {
        return data;
    }

    public void searchForIndex() {
        for (int x = 0; x < searchRadius; x++) {
            for (int z = 0; z < searchRadius; z++) {
                if (this.getWorld().getBlockState(this.getPos().add(x, 0, z))
                        .getBlock() == RunecarvedContent.runeIndex) {
                    this.index = (TileEntityRuneIndex) this.getWorld().getTileEntity(getPos().add(x, 0, z));
                }
            }
        }
    }

    public TileEntityRuneIndex getIndex() {
        return index;
    }
}