package com.rempler.fcrenewed.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.ConfigValue<Integer> folderSizeLimit;
    private static final ForgeConfigSpec.ConfigValue<Boolean> seasonalCabinets;
    private static final ForgeConfigSpec.ConfigValue<Boolean> magnifyingGlassGui;
    private static final ForgeConfigSpec.ConfigValue<Boolean> randomVillager;
    private static final ForgeConfigSpec.ConfigValue<Boolean> waterNether;
    private static final ForgeConfigSpec.ConfigValue<Boolean> pickupStuff;
    private static final ForgeConfigSpec.ConfigValue<Boolean> infiniteWaterSource;
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> mobFolderBlacklist;
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> mysteryItems;
    private static final ForgeConfigSpec.ConfigValue<Integer> maxLootChance;
    private static final ForgeConfigSpec.ConfigValue<Integer> guiWidth;
    private static final ForgeConfigSpec.ConfigValue<Integer> guiHeight;
    private static final ForgeConfigSpec.ConfigValue<Boolean> invertShift;
    private static final ForgeConfigSpec.ConfigValue<Boolean> folderHud;

    public static int getFolderSizeLimit() {
        return folderSizeLimit.get();
    }
    public static boolean getSeasonalCabinets() {
        return seasonalCabinets.get();
    }
    public static boolean getMagnifyingGlassGui() {
        return magnifyingGlassGui.get();
    }
    public static boolean getRandomVillager() {
        return randomVillager.get();
    }
    public static boolean getWaterNether() {
        return waterNether.get();
    }
    public static boolean getPickupStuff() {
        return pickupStuff.get();
    }
    public static boolean getInfiniteWaterSource() {
        return infiniteWaterSource.get();
    }
    public static List<? extends String> getMobFolderBlacklist() {
        return mobFolderBlacklist.get();
    }
    public static List<? extends String> getMysteryItems() {
        return mysteryItems.get();
    }
    public static int getMaxLootChance() {
        return maxLootChance.get();
    }
    public static int getGuiWidth() {
        return guiWidth.get();
    }
    public static int getGuiHeight() {
        return guiHeight.get();
    }
    public static boolean getInvertShift() {
        return invertShift.get();
    }
    public static boolean getFolderHud() {
        return folderHud.get();
    }

    static {
        BUILDER.push("general");
        folderSizeLimit = BUILDER.comment("Configure the folder storage limit for dyed folders.")
                .define("folderSizeLimit", 1000);
        seasonalCabinets = BUILDER.comment("If enabled, will let filing cabinets use a different texture depending on the season.")
                .define("seasonalCabinets", true);
        magnifyingGlassGui = BUILDER.comment("Disable this if you want WAILA / TheOneProbe to handle the overlay insted.")
                .define("magnifyingGlassGui", true);
        randomVillager = BUILDER.comment("If enabled, will let Mob Folders with a villager in it spawn villagers with their professions randomized.")
                .define("randomVillager", false);
        waterNether = BUILDER.comment("If enabled, will let Fluid Folders place water in the nether.")
                .define("waterNether", false);
        pickupStuff = BUILDER.comment("If disabled, will not let folders pick up dropped items.")
                .define("pickupStuff", true);
        infiniteWaterSource = BUILDER.comment("If enabled, A fluid cabinet containing at least 3000mb of water will never run out of water.")
                .define("infiniteWaterSource", true);
        mobFolderBlacklist = BUILDER.comment("Use this to blacklist certain mobs from being captured in the Mob Folder. Put the class names of the entities here.")
                .define("mobFolderBlacklist", List.of("fcrenewed:cabinet_entity"),
                        Config::validateEntityName);
        mysteryItems = BUILDER.comment("List of items that will be randomly picked by the mystery folder.")
                .define("mysteryItems", List.of("minecraft:diamond", "minecraft:cobblestone", "minecraft:blaze_rod", "minecraft:slime_ball", "minecraft:clay", "minecraft:prismarine", "minecraft:rabbit_foot", "minecraft:torch")
                , Config::validateItemName);
        maxLootChance = BUILDER.comment("Sets the upper bound limit of the loot item's stack size that the Mystery Folder will randomly return. Set to 0 to always return a single item every time.").define("maxLootChance", 7);
        guiWidth = BUILDER.comment("Adjust placement of magnifying glass GUI on the x axis.").define("guiWidth", 0);
        guiHeight = BUILDER.comment("Adjust placement of magnifying glass GUI on the y axis.").define("guiHeight", 5);
        invertShift = BUILDER.comment("If enabled, reverses the interaction of pulling items from filing cabinets, so that shiftclick pulls 1 instead of 64 and vice-versa for without shiftclick.").define("invertShift", false);
        folderHud = BUILDER.comment("Displays the current Item/Block being contained in the currently held folder/suitcase").define("folderHud", true);
        BUILDER.pop();
    }

    public static final ForgeConfigSpec SPEC = BUILDER.build();
    public static Set<Item> items;

    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }

    private static boolean validateEntityName(final Object obj)
    {
        return obj instanceof final String itemName && ForgeRegistries.ENTITY_TYPES.containsKey(new ResourceLocation(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {

        // convert the list of strings into a set of items
        items = mysteryItems.get().stream()
                .map(itemName -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)))
                .collect(Collectors.toSet());
    }


}