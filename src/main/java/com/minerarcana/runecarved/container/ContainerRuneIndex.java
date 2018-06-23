package com.minerarcana.runecarved.container;

import javax.annotation.Nonnull;

import com.minerarcana.runecarved.item.ItemRunestone;
import com.minerarcana.runecarved.tileentity.TileEntityRuneIndex;
import com.teamacronymcoders.base.containers.ContainerBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.*;

public class ContainerRuneIndex extends ContainerBase {
    TileEntityRuneIndex tile;

    public ContainerRuneIndex(EntityPlayer player, World world, TileEntityRuneIndex tile) {
        this.addSlotToContainer(
                new SlotRune(tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null), 0, 25, 33));
        for (int i = 0; i < 9; i++) {
            this.addSlotToContainer(new SlotRune(
                    tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null), i, 134 + (i * 10), 33));
        }
        this.createPlayerSlots(player.inventory);
        this.tile = tile;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        // TODO Auto-generated method stub
        return true;
    }

    public static class SlotRune extends SlotItemHandler {
        public SlotRune(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(@Nonnull ItemStack stack) {
            if (stack.getItem() instanceof ItemRunestone) {
                return super.isItemValid(stack);
            }
            return false;
        }
    }

}
