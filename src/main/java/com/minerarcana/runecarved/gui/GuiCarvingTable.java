package com.minerarcana.runecarved.gui;

import com.minerarcana.runecarved.Runecarved;
import com.minerarcana.runecarved.api.RunecarvedAPI;
import com.minerarcana.runecarved.api.spell.Spell;
import com.minerarcana.runecarved.container.ContainerCarvingTable;
import com.minerarcana.runecarved.container.PacketRuneButton;
import com.minerarcana.runecarved.tileentity.TileEntityCarvingTable;
import com.minerarcana.runecarved.tileentity.TileEntityRuneIndex;
import com.minerarcana.runecarved.tileentity.TileEntityRuneIndex.ItemHandlerRunic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();

        int i = 8453920;
        boolean flag = true;
        String s = I18n.format("container.repair.cost", 3);

        if (flag) {
            int j = -16777216 | (i & 16579836) >> 2 | i & -16777216;
            int k = this.xSize - 8 - this.fontRenderer.getStringWidth(s);
            if (this.fontRenderer.getUnicodeFlag()) {
                drawRect(k - 3, 65, this.xSize - 7, 77, -16777216);
                drawRect(k - 2, 66, this.xSize - 8, 76, -12895429);
            } else {
                this.fontRenderer.drawString(s, k, 68, j);
                this.fontRenderer.drawString(s, k + 1, 67, j);
                this.fontRenderer.drawString(s, k + 1, 68, j);
            }

            this.fontRenderer.drawString(s, k, 67, i);
        }

        GlStateManager.enableLighting();
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
                ItemHandlerRunic handler = (ItemHandlerRunic) indexTile
                        .getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                this.buttonList.stream().map(button -> (GuiButtonRune) button)
                        .filter(runeButton -> handler.getContainedSpells().containsKey(runeButton.spell))
                        .forEach(button -> button.enabled = true);
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
                ((GuiButtonRune) button).spell.getRegistryName().getPath(), this.tileCarvingTable.getPos()));
        super.actionPerformed(button);
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
                GlStateManager.pushMatrix();
                mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                mc.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
                GlStateManager.enableRescaleNormal();
                GlStateManager.enableAlpha();
                GlStateManager.alphaFunc(516, 0.1F);
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA,
                        GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                GlStateManager.translate((float) this.x, (float) this.y, 100.0F + this.zLevel);
                GlStateManager.translate(8.0F, 8.0F, 0.0F);
                GlStateManager.scale(1.0F, -1.0F, 1.0F);
                GlStateManager.scale(16.0F, 16.0F, 16.0F);
                if (!this.enabled) {
                    GlStateManager.color(0.8F, 0.8F, 0.8F, 0.5F);
                    // TODO This is test...
                    GlStateManager.scale(0.5F, 0.5F, 0.5F);
                } else {
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                }
                ItemStack stack = new ItemStack(
                        Item.getByNameOrId("runecarved:runestone." + spell.getRegistryName().getPath()));
                mc.getRenderItem().renderItem(stack, mc.getRenderItem().getItemModelWithOverrides(stack, null, null));
                GlStateManager.disableAlpha();
                GlStateManager.disableRescaleNormal();
                GlStateManager.disableLighting();
                GlStateManager.popMatrix();
                mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                mc.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
            }
        }
    }

}
