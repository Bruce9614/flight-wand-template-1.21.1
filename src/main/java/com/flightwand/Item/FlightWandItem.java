package com.flightwand.Item;

import dev.emi.trinkets.api.Trinket;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.item.tooltip.TooltipType;
import org.jetbrains.annotations.Nullable;
import java.util.List;

/**
 * @author Administrator
 */
public class FlightWandItem extends Item implements Trinket {
    public FlightWandItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(
            ItemStack stack,
            @Nullable TooltipContext context,
            List<Text> tooltip,
            TooltipType type
    ) {
        // 逐行添加翻译键
        tooltip.add(Text.translatable("item.flight-wand.flight_wand.tooltip.line1"));
        tooltip.add(Text.translatable("item.flight-wand.flight_wand.tooltip.line2"));
        tooltip.add(Text.translatable("item.flight-wand.flight_wand.tooltip.line3"));
        tooltip.add(Text.translatable("item.flight-wand.flight_wand.tooltip.line4"));
        super.appendTooltip(stack, context, tooltip, type);
    }
}