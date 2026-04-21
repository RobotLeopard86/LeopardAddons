package net.rl86.leopardaddons.content;

import dan200.computercraft.shared.computer.blocks.AbstractComputerBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.CustomData;
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
			if(!context.getItemInHand().has(DataComponents.CUSTOM_DATA) || (context.getItemInHand().has(DataComponents.CUSTOM_DATA) && !context.getItemInHand().get(DataComponents.CUSTOM_DATA).contains("link"))) return InteractionResult.PASS;
			CompoundTag nbt = context.getItemInHand().get(DataComponents.CUSTOM_DATA).copyTag();
			if(!nbt.contains("link")) return InteractionResult.PASS;
			CompoundTag lnk = nbt.getCompound("link");
			if(!lnk.contains("x") || !lnk.contains("y") || !lnk.contains("z")) return InteractionResult.PASS;
			
			KioskBE blockEntity = (KioskBE)level.getBlockEntity(context.getClickedPos());
			blockEntity.linkToComputer(lnk.getInt("x"), lnk.getInt("y"), lnk.getInt("z"));
			nbt.remove("link");
			context.getItemInHand().set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));

			Minecraft.getInstance().player.sendSystemMessage(Component.literal("Linking successful!"));
			return InteractionResult.SUCCESS;
		} else if(selectedBlock instanceof AbstractComputerBlock) {
			CompoundTag nbt = context.getItemInHand().getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
			CompoundTag lnk = new CompoundTag();
			lnk.put("x", IntTag.valueOf(context.getClickedPos().getX()));
			lnk.put("y", IntTag.valueOf(context.getClickedPos().getY()));
			lnk.put("z", IntTag.valueOf(context.getClickedPos().getZ()));
			if(nbt.contains("link") && context.getClickedPos().getX() == ((IntTag)nbt.getCompound("link").get("x")).getAsInt() &&
					context.getClickedPos().getY() == ((IntTag)nbt.getCompound("link").get("y")).getAsInt() &&
					context.getClickedPos().getZ() == ((IntTag)nbt.getCompound("link").get("z")).getAsInt()) {
				return InteractionResult.SUCCESS;
			}
			nbt.put("link", lnk);
			context.getItemInHand().set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));
			Minecraft.getInstance().player.sendSystemMessage(Component.literal("Selected computer @ " + lnk.get("x").getAsString() + " " + lnk.get("y").getAsString() + " " + lnk.get("z").getAsString()));
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}

}
