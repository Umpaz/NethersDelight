package umpaz.nethersdelight.data;

import com.google.common.collect.Sets;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import umpaz.nethersdelight.NethersDelight;
import umpaz.nethersdelight.common.registry.NDItems;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NDItemModels extends ItemModelProvider
{
    public static final String GENERATED = "item/generated";
    public static final String HANDHELD = "item/handheld";

    public NDItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, NethersDelight.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        Set<Item> items = ForgeRegistries.ITEMS.getValues().stream().filter(i -> NethersDelight.MODID.equals(ForgeRegistries.ITEMS.getKey(i).getNamespace()))
                .collect(Collectors.toSet());

        // Specific cases
        itemGeneratedModel(NDItems.CRIMSON_FUNGUS_COLONY.get(), resourceBlock(itemName(NDItems.CRIMSON_FUNGUS_COLONY.get()) + "_stage3"));
        items.remove(NDItems.CRIMSON_FUNGUS_COLONY.get());

        itemGeneratedModel(NDItems.WARPED_FUNGUS_COLONY.get(), resourceBlock(itemName(NDItems.WARPED_FUNGUS_COLONY.get()) + "_stage3"));
        items.remove(NDItems.WARPED_FUNGUS_COLONY.get());

        //blockBasedModel(ModItems.ORGANIC_COMPOST.get(), "_0");
        //items.remove(ModItems.ORGANIC_COMPOST.get());

        // Items that should be held like a mug
        /*Set<Item> mugItems = Sets.newHashSet(
                ModItems.HOT_COCOA.get(),
                ModItems.APPLE_CIDER.get(),
                ModItems.MELON_JUICE.get());
        takeAll(items, mugItems.toArray(new Item[0])).forEach(item -> itemMugModel(item, resourceItem(itemName(item))));

        // Blocks with special item sprites
        Set<Item> spriteBlockItems = Sets.newHashSet(
                ModItems.FULL_TATAMI_MAT.get(),
                ModItems.HALF_TATAMI_MAT.get(),
                ModItems.ROPE.get(),
                ModItems.CANVAS_SIGN.get(),
                ModItems.WHITE_CANVAS_SIGN.get(),
                ModItems.ORANGE_CANVAS_SIGN.get(),
                ModItems.MAGENTA_CANVAS_SIGN.get(),
                ModItems.LIGHT_BLUE_CANVAS_SIGN.get(),
                ModItems.YELLOW_CANVAS_SIGN.get(),
                ModItems.LIME_CANVAS_SIGN.get(),
                ModItems.PINK_CANVAS_SIGN.get(),
                ModItems.GRAY_CANVAS_SIGN.get(),
                ModItems.LIGHT_GRAY_CANVAS_SIGN.get(),
                ModItems.CYAN_CANVAS_SIGN.get(),
                ModItems.PURPLE_CANVAS_SIGN.get(),
                ModItems.BLUE_CANVAS_SIGN.get(),
                ModItems.BROWN_CANVAS_SIGN.get(),
                ModItems.GREEN_CANVAS_SIGN.get(),
                ModItems.RED_CANVAS_SIGN.get(),
                ModItems.BLACK_CANVAS_SIGN.get(),
                ModItems.APPLE_PIE.get(),
                ModItems.SWEET_BERRY_CHEESECAKE.get(),
                ModItems.CHOCOLATE_PIE.get(),
                ModItems.CABBAGE_SEEDS.get(),
                ModItems.TOMATO_SEEDS.get(),
                ModItems.ONION.get(),
                ModItems.RICE.get(),
                ModItems.ROAST_CHICKEN_BLOCK.get(),
                ModItems.STUFFED_PUMPKIN_BLOCK.get(),
                ModItems.HONEY_GLAZED_HAM_BLOCK.get(),
                ModItems.SHEPHERDS_PIE_BLOCK.get()
        );
        takeAll(items, spriteBlockItems.toArray(new Item[0])).forEach(item -> withExistingParent(itemName(item), GENERATED).texture("layer0", resourceItem(itemName(item))));
        */

        // Blocks whose items look alike
        takeAll(items, i -> i instanceof BlockItem).forEach(item -> blockBasedModel(item, ""));

        // Handheld items
        Set<Item> handheldItems = Sets.newHashSet(
                //ModItems.NETHERITE_KNIFE.get()
        );
        takeAll(items, handheldItems.toArray(new Item[0])).forEach(item -> itemHandheldModel(item, resourceItem(itemName(item))));

        // Generated items
        items.forEach(item -> itemGeneratedModel(item, resourceItem(itemName(item))));
    }

    public void blockBasedModel(Item item, String suffix) {
        withExistingParent(itemName(item), resourceBlock(itemName(item) + suffix));
    }

    public void itemHandheldModel(Item item, ResourceLocation texture) {
        withExistingParent(itemName(item), HANDHELD).texture("layer0", texture);
    }

    public void itemGeneratedModel(Item item, ResourceLocation texture) {
        withExistingParent(itemName(item), GENERATED).texture("layer0", texture);
    }

    private String itemName(Item item) {
        return ForgeRegistries.ITEMS.getKey(item).getPath();
    }

    public ResourceLocation resourceBlock(String path) {
        return new ResourceLocation(NethersDelight.MODID, "block/" + path);
    }

    public ResourceLocation resourceItem(String path) {
        return new ResourceLocation(NethersDelight.MODID, "item/" + path);
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> Collection<T> takeAll(Set<? extends T> src, T... items) {
        List<T> ret = Arrays.asList(items);
        for (T item : items) {
            if (!src.contains(item)) {
                NethersDelight.LOGGER.warn("Item {} not found in set", item);
            }
        }
        if (!src.removeAll(ret)) {
            NethersDelight.LOGGER.warn("takeAll array didn't yield anything ({})", Arrays.toString(items));
        }
        return ret;
    }

    public static <T> Collection<T> takeAll(Set<T> src, Predicate<T> pred) {
        List<T> ret = new ArrayList<>();

        Iterator<T> iter = src.iterator();
        while (iter.hasNext()) {
            T item = iter.next();
            if (pred.test(item)) {
                iter.remove();
                ret.add(item);
            }
        }

        if (ret.isEmpty()) {
            NethersDelight.LOGGER.warn("takeAll predicate yielded nothing", new Throwable());
        }
        return ret;
    }
}