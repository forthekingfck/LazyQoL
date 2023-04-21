package com.suyako.lazyQoL.common.mixins;

import com.suyako.LazyQoL;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class MixinBlock {
    @Shadow
    protected double minX,minY,minZ,maxX,maxY,maxZ;
    @Shadow public abstract Vec3 modifyAcceleration(World p_modifyAcceleration_1_, BlockPos p_modifyAcceleration_2_, Entity p_modifyAcceleration_3_, Vec3 p_modifyAcceleration_4_);

    @Inject(method = "isCollidable", at = @At("HEAD"), cancellable = true)
    private void isCollidable(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if(LazyQoL.NoSaplingBound && (Block.getIdFromBlock((Block) (Object) this) == Block.getIdFromBlock(Blocks.sapling))){
            callbackInfoReturnable.setReturnValue(false);
        }
        if(LazyQoL.NoTreeBound && ((Block.getIdFromBlock((Block) (Object) this) == Block.getIdFromBlock(Blocks.leaves)) || (Block.getIdFromBlock((Block) (Object) this) == Block.getIdFromBlock(Blocks.leaves2)) || (Block.getIdFromBlock((Block) (Object) this) == Block.getIdFromBlock(Blocks.log)) || (Block.getIdFromBlock((Block) (Object) this) == Block.getIdFromBlock(Blocks.log2)))){
            callbackInfoReturnable.setReturnValue(false);
        }
    }
}
