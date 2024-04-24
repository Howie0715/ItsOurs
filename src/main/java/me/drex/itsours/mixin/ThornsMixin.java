package me.drex.itsours.mixin;

import me.drex.itsours.claim.AbstractClaim;
import me.drex.itsours.claim.ClaimList;
import me.drex.itsours.claim.permission.PermissionManager;
import me.drex.itsours.claim.permission.node.Node;
import net.minecraft.enchantment.ThornsEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(ThornsEnchantment.class)
public abstract class ThornsMixin {
    @Inject(
        method = "onUserDamaged",
        at = @At("HEAD"),
        cancellable = true
    )
    private void itsours$canInteract(LivingEntity user, Entity attacker, int level, CallbackInfo ci) {
        Optional<AbstractClaim> claim = ClaimList.getClaimAt(user.getWorld(), user.getBlockPos());
        if (claim.isPresent() && !claim.get().hasPermission(user.getUuid(), PermissionManager.DAMAGE_ENTITY, Node.registry(Registries.ENTITY_TYPE, attacker.getType())) && !(attacker instanceof PlayerEntity) &&!(attacker instanceof ShulkerBulletEntity)) {
            ci.cancel();
        }
    }

}
