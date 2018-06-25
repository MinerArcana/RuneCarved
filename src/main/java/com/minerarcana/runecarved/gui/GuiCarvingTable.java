package com.minerarcana.runecarved.gui;

import java.io.IOException;
import java.util.*;

import com.minerarcana.runecarved.Runecarved;
import com.minerarcana.runecarved.api.RunecarvedAPI;
import com.minerarcana.runecarved.api.spell.Spell;
import com.minerarcana.runecarved.container.ContainerCarvingTable;
import com.minerarcana.runecarved.item.ItemRunestone;
import com.minerarcana.runecarved.tileentity.TileEntityCarvingTable;
import com.minerarcana.runecarved.tileentity.TileEntityRuneIndex;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

@SideOnly(Side.CLIENT)
public class GuiCarvingTable extends GuiContainer {

    private static final ResourceLocation GUI_TEX = new ResourceLocation(Runecarved.MODID,
            "textures/gui/carving_table.png");
    private TileEntityCarvingTable tileCarvingTable;
    // TODO
    // private Map<Spell, Integer> buttonIDs = Maps.newHashMap();

    public GuiCarvingTable(ContainerCarvingTable inventorySlotsIn, TileEntityCarvingTable tile) {
        super(inventorySlotsIn);
        this.tileCarvingTable = tile;
    }

    @Override
    // TODO Not every tick plz!!1
    public void updateScreen() {
        super.updateScreen();
        if (tileCarvingTable.getIndexPos() != null) {
            TileEntity tile = tileCarvingTable.getWorld().getTileEntity(tileCarvingTable.getIndexPos());
            if (tile instanceof TileEntityRuneIndex) {
                TileEntityRuneIndex indexTile = (TileEntityRuneIndex) tile;
                // TODO We will have to manually sync the contents of the index whenever this is
                // opened, since client won't know about index contents if it hasn't been opened
                // that game
                IItemHandler handler = indexTile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                for (int i = 0; i < handler.getSlots(); i++) {
                    ItemStack stack = handler.getStackInSlot(i);
                    if (stack.getItem() instanceof ItemRunestone) {
                        String runeName = stack.getUnlocalizedName().split("\\.")[1];
                        // FIXME
                        Spell spell = RunecarvedAPI.getInstance().getSpellRegistry()
                                .getSpell(new ResourceLocation(Runecarved.MODID, runeName));
                        this.buttonList.get(i).enabled = true;
                    }
                }
            }
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.clear();

        int buttonID = 0;
        Map<ResourceLocation, Spell> spellMap = RunecarvedAPI.getInstance().getSpellRegistry().getSpells();
        List<Spell> spellList = new ArrayList<Spell>(spellMap.values());
        for (int vertical = 0; vertical < spellList.size(); vertical++) {
            for (int horizontal = 0; horizontal < 3; horizontal++) {
                if (buttonID < spellList.size()) {
                    Spell spell = spellList.get(buttonID);
                    // buttonIDs.put(spell, buttonID);
                    GuiButtonRune button = new GuiButtonRune(buttonID++, this.guiLeft + 8 + (horizontal * 19),
                            this.guiTop + 16 + (vertical * 19), 18, 18, spell);
                    button.enabled = false;
                    this.addButton(button);
                }
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        Runecarved.instance.getPacketHandler().sendToServer(new PacketRuneButton(
                ((GuiButtonRune) button).spell.getRegistryName().getResourcePath(), this.tileCarvingTable.getPos()));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.renderEngine.bindTexture(GUI_TEX);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
    }

    public static class GuiButtonRune extends GuiButton {

        Spell spell;

        public GuiButtonRune(int buttonId, int x, int y, int widthIn, int heightIn, Spell spell) {
            super(buttonId, x, y, widthIn, heightIn, "");
            this.spell = spell;
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
            if (this.visible) {
                mc.getRenderItem().renderItemIntoGUI(
                        new ItemStack(Item
                                .getByNameOrId("runecarved:runestone." + spell.getRegistryName().getResourcePath())),
                        this.x, this.y);
                if (!this.enabled) {
                    mc.getRenderItem().renderItemIntoGUI(new ItemStack(Item.getItemFromBlock(Blocks.BARRIER)), this.x,
                            this.y);
                }
            }
        }
    }
}
