package com.infamous.dungeons_mobs.client;

import com.infamous.dungeons_mobs.client.particle.ModParticleTypes;
import com.infamous.dungeons_mobs.client.particle.SnowflakeParticle;
import com.infamous.dungeons_mobs.client.renderer.creeper.CustomCreeperRenderer;
import com.infamous.dungeons_mobs.client.renderer.ender.BlastlingRenderer;
import com.infamous.dungeons_mobs.client.renderer.ender.EndersentRenderer;
import com.infamous.dungeons_mobs.client.renderer.ender.SnarelingRenderer;
import com.infamous.dungeons_mobs.client.renderer.ender.WatchlingRenderer;
import com.infamous.dungeons_mobs.client.renderer.golem.SquallGolemRenderer;
import com.infamous.dungeons_mobs.client.renderer.illager.*;
import com.infamous.dungeons_mobs.client.renderer.jungle.LeapleafRenderer;
import com.infamous.dungeons_mobs.client.renderer.jungle.PoisonQuillVineRenderer;
import com.infamous.dungeons_mobs.client.renderer.jungle.QuickGrowingVineRenderer;
import com.infamous.dungeons_mobs.client.renderer.jungle.WhispererRenderer;
import com.infamous.dungeons_mobs.client.renderer.layer.GeoMobEnchantmentGlintLayer;
import com.infamous.dungeons_mobs.client.renderer.layer.MobEnchantmentGlintLayer;
import com.infamous.dungeons_mobs.client.renderer.layers.SkeletonEyesLayer;
import com.infamous.dungeons_mobs.client.renderer.piglin.CustomPiglinRenderer;
import com.infamous.dungeons_mobs.client.renderer.projectiles.*;
import com.infamous.dungeons_mobs.client.renderer.redstone.RedstoneCubeRenderer;
import com.infamous.dungeons_mobs.client.renderer.redstone.RedstoneGolemRenderer;
import com.infamous.dungeons_mobs.client.renderer.redstone.RedstoneMineRenderer;
import com.infamous.dungeons_mobs.client.renderer.slime.ConjuredSlimeRenderer;
import com.infamous.dungeons_mobs.client.renderer.summonables.*;
import com.infamous.dungeons_mobs.client.renderer.undead.*;
import com.infamous.dungeons_mobs.client.renderer.water.*;
import com.infamous.dungeons_mobs.items.ModSpawnEggItem;
import com.infamous.dungeons_mobs.items.WraithFireChargeItem;
import com.infamous.dungeons_mobs.items.shield.CustomISTER;
import com.infamous.dungeons_mobs.mod.ModBlocks;
import com.infamous.dungeons_mobs.mod.ModEntityTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.DyeColor;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.event.RenderNameplateEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.stream.Collectors;

import static com.infamous.dungeons_libraries.capabilities.enchantable.EnchantableHelper.getEnchantableCapabilityLazy;
import static com.infamous.dungeons_mobs.DungeonsMobs.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onClientSetup(final ModelRegistryEvent event){
        ModelLoader.addSpecialModel(CustomISTER.getTridentMRL(DyeColor.YELLOW, false));
        ModelLoader.addSpecialModel(CustomISTER.getTridentMRL(DyeColor.PURPLE, false));
        ModelLoader.addSpecialModel(CustomISTER.getTridentMRL(DyeColor.YELLOW, true));
        ModelLoader.addSpecialModel(CustomISTER.getTridentMRL(DyeColor.PURPLE, true));
    }

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event){
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ARMORED_ZOMBIE.get(), CustomZombieRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.JUNGLE_ZOMBIE.get(), CustomZombieRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.FROZEN_ZOMBIE.get(), CustomZombieRenderer::new);

        // To match Husk proportions found in MCD
        RenderingRegistry.registerEntityRenderingHandler(EntityType.HUSK, CustomZombieRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ARMORED_SKELETON.get(), CustomSkeletonRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.MOSSY_SKELETON.get(), CustomSkeletonRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.SKELETON_HORSEMAN.get(), CustomSkeletonRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.SKELETON_VANGUARD.get(), SkeletonVanguardRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.NECROMANCER.get(), NecromancerRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ARMORED_VINDICATOR.get(), CustomVindicatorRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ARMORED_PILLAGER.get(), CustomPillagerRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ROYAL_GUARD.get(), CustomVindicatorRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.VINDICATOR_CHEF.get(), CustomVindicatorRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.MOUNTAINEER.get(), MountaineerRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ARMORED_MOUNTAINEER.get(), MountaineerRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ICEOLOGER.get(), IceologerRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.GEOMANCER.get(),
	    	    manager -> new GeomancerRenderer(manager));

        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ILLUSIONER.get(), CustomIllusionerRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ILLUSIONER_CLONE.get(), IllusionerCloneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.WINDCALLER.get(), WindcallerRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ENCHANTER.get(),
	    	    manager -> new EnchanterRenderer(manager));

        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ICY_CREEPER.get(), CustomCreeperRenderer::new);

	    RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.WRAITH.get(),
	    	    manager -> new WraithRenderer(manager));

        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.CONJURED_SLIME.get(), ConjuredSlimeRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.REDSTONE_CUBE.get(), RedstoneCubeRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.REDSTONE_GOLEM.get(),
                manager -> new RedstoneGolemRenderer(manager));

        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.WHISPERER.get(), WhispererRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.LEAPLEAF.get(), LeapleafRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.POISON_QUILL_VINE.get(), PoisonQuillVineRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.QUICK_GROWING_VINE.get(), QuickGrowingVineRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.SQUALL_GOLEM.get(), SquallGolemRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ARMORED_PIGLIN.get(), manager -> new CustomPiglinRenderer(manager, false, false));
        RenderingRegistry.registerEntityRenderingHandler(EntityType.PIGLIN, manager -> new CustomPiglinRenderer(manager, false, false));
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.FUNGUS_THROWER.get(), manager -> new CustomPiglinRenderer(manager, false, true));
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ZOMBIFIED_ARMORED_PIGLIN.get(), manager -> new CustomPiglinRenderer(manager, true, false));
        RenderingRegistry.registerEntityRenderingHandler(EntityType.ZOMBIFIED_PIGLIN, manager -> new CustomPiglinRenderer(manager, true, false));
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ZOMBIFIED_FUNGUS_THROWER.get(), manager -> new CustomPiglinRenderer(manager, true, true));

        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.WRAITH_FIREBALL.get(), CustomFireballRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.SLIMEBALL.get(), SlimeballRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.COBWEB_PROJECTILE.get(), CobwebProjectileRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.BLUE_NETHERSHROOM.get(), BlueNethershroomRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.GEOMANCER_WALL.get(),
	    	    manager -> new GeomancerWallRenderer(manager));

        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.GEOMANCER_BOMB.get(),
	    	    manager -> new GeomancerBombRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.REDSTONE_MINE.get(),
                manager -> new RedstoneMineRenderer(manager));
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ICE_CLOUD.get(), IceCloudRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.TORNADO.get(), TornadoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.COBWEB_TRAP.get(), CobwebTrapRenderer::new);

        RenderTypeLookup.setRenderLayer(ModBlocks.WRAITH_FIRE_BLOCK.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.CORRUPTED_PYRE_BLOCK.get(), RenderType.cutout());

        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.WAVEWHISPERER.get(), WavewhispererRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.POISON_ANEMONE.get(), PoisonAnemoneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.QUICK_GROWING_ANEMONE.get(), QuickGrowingAnemoneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ARMORED_DROWNED.get(), CustomDrownedRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.DROWNED_NECROMANCER.get(), DrownedNecromancerRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.SUNKEN_SKELETON.get(), SmartSkeletonRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ARMORED_SUNKEN_SKELETON.get(), SmartSkeletonRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.LASER_ORB.get(), OrbRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.TRIDENT_FUME.get(), OrbRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ENDERSENT.get(), EndersentRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.BLASTLING.get(), BlastlingRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.WATCHLING.get(), WatchlingRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.SNARELING.get(), SnarelingRenderer::new);
    }

    @SubscribeEvent
    public static void onClientSetupComplete(final FMLLoadCompleteEvent event){
        Minecraft.getInstance().getEntityRenderDispatcher().renderers.values().forEach(r -> {
            if (r instanceof LivingRenderer) {
                ((LivingRenderer)r).addLayer(new MobEnchantmentGlintLayer((LivingRenderer)r));
            } else if (r instanceof GeoEntityRenderer) {
                ((GeoEntityRenderer)r).addLayer(new GeoMobEnchantmentGlintLayer((GeoEntityRenderer)r));
            }
            if(r instanceof SkeletonRenderer){
                ((SkeletonRenderer)r).addLayer(new SkeletonEyesLayer<>((SkeletonRenderer)r));
            }
        });

        Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().values().forEach((r) -> {
            if (r instanceof LivingRenderer) {
                r.addLayer(new MobEnchantmentGlintLayer(r));
            }

        });
    }

    @SubscribeEvent
    public static void onRegisterEntities(final RegistryEvent.Register<EntityType<?>> event){
        ModSpawnEggItem.initSpawnEggs();
        WraithFireChargeItem.initSoulFireCharge();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onParticleFactory(ParticleFactoryRegisterEvent event){
        Minecraft.getInstance().particleEngine.register(ModParticleTypes.SNOWFLAKE.get(), SnowflakeParticle.Factory::new);
    }

    /*@SubscribeEvent
    public static void onRenderNamePlateEvent(RenderNameplateEvent event){
        Entity entity = event.getEntity();
        IFormattableTextComponent copy = event.getContent().copy();
        StringBuilder enchantmentString = new StringBuilder();
        getEnchantableCapabilityLazy(entity).ifPresent(cap -> {
            if(cap.hasEnchantment()){
                enchantmentString.append(" (");
                enchantmentString.append(cap.getEnchantments().stream().map(mobEnchantment -> mobEnchantment.getRegistryName().getPath()).collect(Collectors.joining(", ")));
                enchantmentString.append(")");
                event.setResult(Event.Result.ALLOW);
            }
        });
        copy.append(enchantmentString.toString());
        event.setContent(copy);
    }*/
}
