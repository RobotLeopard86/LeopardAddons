package net.rl86.leopardaddons.content;

import dan200.computercraft.shared.computer.blocks.AbstractComputerBlock;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class KioskLinkerItem extends Item {

	public KioskLinkerItem() {
		super(new Item.Properties().stacksTo(1).setNoRepair());
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		Block selectedBlock = level.getBlockState(context.getClickedPos()).getBlock();
		if(selectedBlock instanceof ComputerKioskBlock) {
			if(!context.getItemInHand().hasTag()) return InteractionResult.PASS;
			CompoundTag nbt = context.getItemInHand().getTag();
			if(!nbt.contains("link")) return InteractionResult.PASS;
			CompoundTag lnk = nbt.getCompound("link");
			if(!lnk.contains("x") || !lnk.contains("y") || !lnk.contains("z")) return InteractionResult.PASS;
			
			KioskBE blockEntity = (KioskBE)level.getBlockEntity(context.getClickedPos());
			blockEntity.linkToComputer(lnk.getInt("x"), lnk.getInt("y"), lnk.getInt("z"));
			
			return InteractionResult.SUCCESS;
		} else if(selectedBlock instanceof AbstractComputerBlock) {
			CompoundTag nbt = context.getItemInHand().getOrCreateTag();
			CompoundTag lnk = new CompoundTag();
			lnk.put("x", IntTag.valueOf(context.getClickedPos().getX()));
			lnk.put("y", IntTag.valueOf(context.getClickedPos().getY()));
			lnk.put("z", IntTag.valueOf(context.getClickedPos().getZ()));
			nbt.put("link", lnk);
			context.getItemInHand().setTag(nbt);
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}

	

}
