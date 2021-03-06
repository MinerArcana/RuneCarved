package com.minerarcana.runecarved.client;

import com.minerarcana.runecarved.CommonProxy;
import com.minerarcana.runecarved.api.entity.EntityProjectileSpell;
import com.minerarcana.runecarved.entity.EntityBoundZombie;
import com.minerarcana.runecarved.entity.EntityFlame;
import com.minerarcana.runecarved.entity.RenderBoundZombie;
import com.minerarcana.runecarved.tileentity.TileEntityRunestone;
import com.minerarcana.runecarved.tileentity.TileEntitySimpleEnchanter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@SuppressWarnings("rawtypes")
public class ClientProxy extends CommonProxy {
    @Override
    public void bindTESRs() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRunestone.class, new TileEntityRunestoneRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySimpleEnchanter.class,
                new TileEntitySimpleEnchanterRenderer());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void bindEntityRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntityBoundZombie.class, new RenderFactoryBoundZombie());
        RenderingRegistry.registerEntityRenderingHandler(EntityProjectileSpell.class,
                new RenderFactoryProjectileSpell());
        RenderingRegistry.registerEntityRenderingHandler(EntityFlame.class, new RenderFactoryFlame());
    }

    public static class RenderFactoryFlame implements IRenderFactory {
        @Override
        public Render createRenderFor(RenderManager manager) {
            return new RenderSnowball(manager, Item.getItemFromBlock(Blocks.FIRE),
                    Minecraft.getMinecraft().getRenderItem());
        }

    }

    public static class RenderFactoryProjectileSpell implements IRenderFactory {

        @Override
        public Render createRenderFor(RenderManager manager) {
            return new RenderSnowball(manager, Items.SNOWBALL, Minecraft.getMinecraft().getRenderItem());
        }

    }

    public static class RenderFactoryBoundZombie implements IRenderFactory {

        @Override
        public Render createRenderFor(RenderManager manager) {
            return new RenderBoundZombie(manager);
        }

    }
}
