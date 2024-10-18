package me.drex.itsours.mixin;

import me.drex.itsours.claim.AbstractClaim;
import me.drex.itsours.claim.list.ClaimList;
import me.drex.itsours.claim.flags.Flags;
import net.minecraft.block.BlockState;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(NetherPortalBlock.class)
public abstract class PiglinSpawnMixin {

    @Inject(
        method = "randomTick",
        at = @At("HEAD"),
        cancellable = true
    )
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        Optional<AbstractClaim> claim = ClaimList.getClaimAt(world.toServerWorld(), pos);
        if (claim.isPresent() && !claim.get().checkAction(null, Flags.MOB_SPAWN)) {
            ci.cancel();
        }
    }
}