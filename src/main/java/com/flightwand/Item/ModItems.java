package com.flightwand.Item;

import com.flightwand.FlightWand;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * @author Administrator
 */
public class ModItems {
    public static final Item FLIGHT_WAND = registerItems(new FlightWandItem(new Item.Settings()));

    private static Item registerItems(Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(FlightWand.MOD_ID, "flight_wand"), item);
    }

    private static void addItemToGroup(FabricItemGroupEntries fabricItemGroupEntries) {
        fabricItemGroupEntries.add(FLIGHT_WAND);
    }

    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ModItems::addItemToGroup);
        FlightWand.LOGGER.info("Registering Mod Items");
    }
}