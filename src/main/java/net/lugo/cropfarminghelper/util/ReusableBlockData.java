package net.lugo.cropfarminghelper.util;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ReusableBlockData {
    private static final Minecraft MC = Minecraft.getInstance();

    private final BlockPos blockPos;
    private BlockState blockState;
    private Block block;

    public ReusableBlockData(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public BlockState blockState() {
        if (blockState == null && MC.level != null) {
            blockState = MC.level.getBlockState(blockPos);
        }
        return blockState;
    }

    public Block block() {
        if (block == null && MC.level != null) {
            block = blockState().getBlock();
        }
        return block;
    }

}
