package net.rl86.leopardaddons.content;

import net.minecraft.core.BlockPos;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.rl86.leopardaddons.registries.BETypeRegistry;
import org.jetbrains.annotations.Nullable;

public class ChargedDustBlock extends ColoredFallingBlock implements EntityBlock {
    public static final IntegerProperty stage = IntegerProperty.create("stage", 0, 3);

    public ChargedDustBlock() {
        super(new ColorRGBA(15085056), Properties.ofFullCopy(Blocks.SAND).mapColor(MapColor.TERRACOTTA_ORANGE));
        this.registerDefaultState(this.getStateDefinition().any().setValue(stage, 0));
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(stage);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ChargedDustBlockBE(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level lvl, BlockState state, BlockEntityType<T> type) {
        return type == BETypeRegistry.chargedDustBE.get() ? ChargedDustBlockBE::tick : null;
    }
}
