package com.flightwand.mixin;

import com.flightwand.Item.ModItems;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

/**
 * @author Administrator
 */
@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        // 仅在服务端执行逻辑
        if (player.getWorld().isClient()) {
            return;
        }

        // 使用正确的方式获取游戏模式
        if (!player.isInSwimmingPose() && player.getAbilities().creativeMode) {
            return;
        }

        ItemStack mainHand = player.getMainHandStack();
        ItemStack offHand = player.getOffHandStack();

        // 检查 Trinkets 模组的/插槽
        boolean hasWandInTrinkets = TrinketsApi.getTrinketComponent(player).map(component -> {
            Optional<ItemStack> stackOptional = component.getEquipped(ModItems.FLIGHT_WAND).stream()
                    .map(Pair::getRight)
                    .findFirst();
            return stackOptional.isPresent();
        }).orElse(false);

        boolean hasWand = mainHand.isOf(ModItems.FLIGHT_WAND) || offHand.isOf(ModItems.FLIGHT_WAND) || hasWandInTrinkets;

        if (hasWand) {
            player.getAbilities().allowFlying = true;

            // 仅在飞行时消耗
            if (player.getAbilities().flying) {
                // 调整为每tick 0.01f（即疲劳值消耗速度0.01*20tick=0.2/s，4/0.2=每20秒消耗1点饥饿值）
                float exhaustion = 0.01f;

                // 直接判断饥饿值是否足够
                if (player.getHungerManager().getFoodLevel() > 0) {
                    player.addExhaustion(exhaustion);
                } else {
                    player.getAbilities().flying = false;
                    player.getAbilities().allowFlying = false;
                }
            }
        } else {
            if (player.getAbilities().allowFlying) {
                player.getAbilities().allowFlying = false;
                player.getAbilities().flying = false;
            }
        }
        player.sendAbilitiesUpdate();
    }
}