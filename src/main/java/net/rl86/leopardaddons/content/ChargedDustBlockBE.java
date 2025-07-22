package net.rl86.leopardaddons.content;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.rl86.leopardaddons.registries.BETypeRegistry;
import net.rl86.leopardaddons.registries.BlockRegistry;

public class ChargedDustBlockBE extends BlockEntity {
    private int counter;

    public ChargedDustBlockBE(BlockPos pos, BlockState state) {
        super(BETypeRegistry.chargedDustBE.get(), pos, state);
    }

    public static void tick(Level lvl, BlockPos pos, BlockState state, BlockEntity blockEnt) {
        if(!(blockEnt instanceof ChargedDustBlockBE)) return;

        ChargedDustBlockBE be = (ChargedDustBlockBE)blockEnt;
        be.counter++;
        if(be.counter >= 6000) {
            be.counter = 0;
            int stage = state.getValue(ChargedDustBlock.stage);
            stage++;
            if(stage < 4) {
                lvl.setBlockAndUpdate(pos, state.trySetValue(ChargedDustBlock.stage, stage));
            } else {
                lvl.setBlockAndUpdate(pos, BlockRegistry.hardenedDustBlock.get().defaultBlockState());
            }
        }
    }



    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider p) {
        super.saveAdditional(tag, p);
        tag.putInt("counter", counter);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider p) {
        super.loadAdditional(tag, p);
        counter = tag.getInt("counter");
    }
}
