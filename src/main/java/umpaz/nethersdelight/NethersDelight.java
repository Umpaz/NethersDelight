package umpaz.nethersdelight;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import umpaz.nethersdelight.client.event.NDClientSetupEvents;
import umpaz.nethersdelight.common.NDCommonSetup;
import umpaz.nethersdelight.common.registry.NDBiomeFeatures;
import umpaz.nethersdelight.common.registry.NDBlockEntityTypes;
import umpaz.nethersdelight.common.registry.NDBlocks;
import umpaz.nethersdelight.common.registry.NDItems;
import vectorwing.farmersdelight.common.registry.ModBiomeFeatures;

import javax.annotation.Nonnull;

@Mod(NethersDelight.MODID)
public class NethersDelight
{
	public static final String MODID = "nethersdelight";

	public static final Logger LOGGER = LogManager.getLogger();
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

	public static final CreativeModeTab CREATIVE_TAB = new CreativeModeTab(NethersDelight.MODID)
	{
		@Nonnull
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(NDBlocks.BLACKSTONE_STOVE.get());
		}
	};

	public NethersDelight() {
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		modEventBus.addListener(NDCommonSetup::init);
		//modEventBus.addListener(NDClientSetupEvents::init);

		NDItems.ITEMS.register(modEventBus);
		NDBlocks.BLOCKS.register(modEventBus);
		NDBiomeFeatures.FEATURES.register(modEventBus);
		NDBlockEntityTypes.TILES.register(modEventBus);

		MinecraftForge.EVENT_BUS.register(this);
	}
}
