package umpaz.nethersdelight.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.BlastFurnaceBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.SmokerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import umpaz.nethersdelight.NethersDelight;
import umpaz.nethersdelight.common.block.BlackstoneStoveBlock;
import umpaz.nethersdelight.common.block.FungusColonyBlock;
import umpaz.nethersdelight.common.block.SoulCompostBlock;
import umpaz.nethersdelight.common.registry.NDBlocks;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import java.util.function.Function;

public class NDBlockStates extends BlockStateProvider {
    private static final int DEFAULT_ANGLE_OFFSET = 180;

    public NDBlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, NethersDelight.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.richSoulSoilBlock(NDBlocks.RICH_SOUL_SOIL.get());

        this.horizontalBlock(NDBlocks.BLACKSTONE_STOVE.get(), state -> {
            String name = blockName(NDBlocks.BLACKSTONE_STOVE.get());
            String suffix = "";
            if (state.getValue(BlackstoneStoveBlock.SOUL)) {
                suffix = "_soul";
            }
            if (!state.getValue(BlackstoneStoveBlock.SOUL) && state.getValue(BlackstoneStoveBlock.LIT)) {
                suffix = "_on";
            }
            return models().orientableWithBottom(name + suffix,
                    resourceBlock(name + "_side"),
                    resourceBlock(name + "_front" + suffix),
                    resourceBlock(name + "_bottom"),
                    resourceBlock(name + "_top" + suffix));
        });
        this.horizontalBlock(NDBlocks.BLACKSTONE_FURNACE.get(), state -> {
            String name = blockName(NDBlocks.BLACKSTONE_FURNACE.get());
            String suffix = state.getValue(FurnaceBlock.LIT) ? "_on" : "";

            return models().orientable(name + suffix,
                    resourceBlock(name + "_side"),
                    resourceBlock(name + "_front" + suffix),
                    resourceBlock(name + "_top"));
        });
        this.horizontalBlock(NDBlocks.NETHER_BRICK_SMOKER.get(), state -> {
            String name = blockName(NDBlocks.NETHER_BRICK_SMOKER.get());
            String suffix = state.getValue(SmokerBlock.LIT) ? "_on" : "";

            return models().orientableWithBottom(name + suffix,
                    resourceBlock(name + "_side"),
                    resourceBlock(name + "_front" + suffix),
                    resourceBlock(name + "_bottom"),
                    resourceBlock(name + "_top"));
        });
        this.horizontalBlock(NDBlocks.BLACKSTONE_BLAST_FURNACE.get(), state -> {
            String name = blockName(NDBlocks.BLACKSTONE_BLAST_FURNACE.get());
            String suffix = state.getValue(BlastFurnaceBlock.LIT) ? "_on" : "";

            return models().orientable(name + suffix,
                    resourceBlock(name + "_side"),
                    resourceBlock(name + "_front" + suffix),
                    resourceBlock(name + "_top"));
        });

        this.stageBlock(NDBlocks.CRIMSON_FUNGUS_COLONY.get(), FungusColonyBlock.COLONY_AGE);
        this.stageBlock(NDBlocks.WARPED_FUNGUS_COLONY.get(), FungusColonyBlock.COLONY_AGE);
    }

    private String blockName(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
    }

    public ResourceLocation resourceBlock(String path) {
        return new ResourceLocation(NethersDelight.MODID, "block/" + path);
    }

    public void stageBlock(Block block, IntegerProperty ageProperty, Property<?>... ignored) {
        this.getVariantBuilder(block).forAllStatesExcept((state) -> {
            int ageSuffix = (Integer)state.getValue(ageProperty);
            String var10000 = this.blockName(block);
            String stageName = var10000 + "_stage" + ageSuffix;
            return ConfiguredModel.builder().modelFile(((BlockModelBuilder)this.models().cross(stageName, this.resourceBlock(stageName))).renderType("cutout")).build();
        }, ignored);
    }

    public void richSoulSoilBlock(Block block) {
        this.simpleBlock(block,
                models().cubeTop(blockName(block), resourceBlock("soul_compost_0"), resourceBlock("rich_soul_soil")));
    }
}