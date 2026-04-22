package net.rl86.leopardaddons.content;

import dan200.computercraft.api.peripheral.PeripheralCapability;
import dan200.computercraft.shared.computer.blocks.AbstractComputerBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.rl86.leopardaddons.LeopardAddons;
import net.rl86.leopardaddons.registries.BlockRegistry;

public class RemotePeripheralItem extends BlockItem {
    public RemotePeripheralItem() {
        super(BlockRegistry.remotePeripheral.get(), new Item.Properties());
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if(context.getLevel().getBlockEntity(context.getClickedPos()) instanceof AbstractComputerBlockEntity) {
            InteractionResult result = this.place(new BlockPlaceContext(context));
            if (!result.consumesAction() && context.getItemInHand().has(DataComponents.FOOD)) {
                InteractionResult r2 = use(context.getLevel(), context.getPlayer(), context.getHand()).getResult();
                return r2 == InteractionResult.CONSUME ? InteractionResult.CONSUME_PARTIAL : r2;
            } else {
                return result;
            }
        }
        if(context.getLevel().getCapability(PeripheralCapability.get(), context.getClickedPos(), context.getClickedFace()) != null) {
            CompoundTag nbt = context.getItemInHand().getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
            CompoundTag lnk = new CompoundTag();
            lnk.put("x", IntTag.valueOf(context.getClickedPos().getX()));
            lnk.put("y", IntTag.valueOf(context.getClickedPos().getY()));
            lnk.put("z", IntTag.valueOf(context.getClickedPos().getZ()));
            lnk.put("dir", IntTag.valueOf(switch (context.getClickedFace()) {
                case Direction.DOWN -> 0;
                case Direction.UP -> 1;
                case Direction.NORTH -> 2;
                case Direction.SOUTH -> 3;
                case Direction.EAST -> 4;
                case Direction.WEST -> 5;
            }));

            if (nbt.contains("link") && context.getClickedPos().getX() == ((IntTag) nbt.getCompound("link").get("x")).getAsInt() &&
                    context.getClickedPos().getY() == ((IntTag) nbt.getCompound("link").get("y")).getAsInt() &&
                    context.getClickedPos().getZ() == ((IntTag) nbt.getCompound("link").get("z")).getAsInt()) {
                return InteractionResult.SUCCESS;
            }
            nbt.put("link", lnk);

            context.getItemInHand().set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));
            if(context.getLevel().isClientSide) Minecraft.getInstance().player.sendSystemMessage(Component.literal("Selected peripheral @ " + lnk.get("x").getAsString() + " " + lnk.get("y").getAsString() + " " + lnk.get("z").getAsString()));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public InteractionResult place(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if(!context.getItemInHand().has(DataComponents.CUSTOM_DATA) || (context.getItemInHand().has(DataComponents.CUSTOM_DATA) && !context.getItemInHand().get(DataComponents.CUSTOM_DATA).contains("link"))) return InteractionResult.FAIL;

        InteractionResult result = super.place(context);
        if(result.consumesAction() && !level.isClientSide) {
            BlockEntity be = level.getBlockEntity(pos);
            if(be instanceof RemotePeripheralBE rpbe) {
                CompoundTag nbt = context.getItemInHand().get(DataComponents.CUSTOM_DATA).copyTag();
                if(!nbt.contains("link")) return InteractionResult.FAIL;
                CompoundTag lnk = nbt.getCompound("link");
                if(!lnk.contains("x") || !lnk.contains("y") || !lnk.contains("z") || !lnk.contains("dir")) return InteractionResult.FAIL;

                rpbe.configureOnPlace(new BlockPos(lnk.getInt("x"), lnk.getInt("y"), lnk.getInt("z")), switch(lnk.getInt("dir")) {
                   case 0 -> Direction.DOWN;
                   case 1 -> Direction.UP;
                   case 2 -> Direction.NORTH;
                   case 3 -> Direction.SOUTH;
                   case 4 -> Direction.EAST;
                   case 5 -> Direction.WEST;
                   default -> throw new IllegalStateException("Unexpected value");
                });
            }
        }
        return result;
    }

}
