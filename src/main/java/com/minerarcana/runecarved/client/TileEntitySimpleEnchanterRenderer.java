package com.minerarcana.runecarved.client;

import com.google.common.collect.Maps;
import com.minerarcana.runecarved.RunecarvedContent;
import com.minerarcana.runecarved.tileentity.TileEntitySimpleEnchanter;
import com.teamacronymcoders.base.util.TextureUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.util.Map;

public class TileEntitySimpleEnchanterRenderer extends TileEntitySpecialRenderer<TileEntitySimpleEnchanter> {

    protected static Minecraft mc = Minecraft.getMinecraft();
    ItemStack stack;

    public TileEntitySimpleEnchanterRenderer() {
        super();
        stack = new ItemStack(RunecarvedContent.scroll);
        Map<Enchantment, Integer> map = Maps.newLinkedHashMap();
        map.put(Enchantments.AQUA_AFFINITY, Enchantment.getEnchantmentID(Enchantments.AQUA_AFFINITY));
        EnchantmentHelper.setEnchantments(map, stack);
    }

    // TODO Scaling
    @Override
    public void render(TileEntitySimpleEnchanter tile, double x, double y, double z, float partialTicks,
                       int destroyStage, float alpha) {
        // Stolen from TiCon :)
        if (!stack.isEmpty()) {
            TextureUtils.pre(x, y, z);
            int brightness = tile.getWorld().getCombinedLight(tile.getPos(), 0);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightness % 0x10000 / 1f,
                    brightness / 0x10000 / 1f);

            GlStateManager.translate(0.5f, 0.8f, 0.5f);
            GlStateManager.scale(1f, 1f, 1f);
            GlStateManager.rotate(-90, 1, 0, 0);

            GL11.glDepthMask(false);
            IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack,
                    tile.getWorld(), null);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);
            GL11.glDepthMask(true);
            TextureUtils.post();
        }
    }
}
