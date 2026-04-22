package net.rl86.leopardaddons.util;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VoxelUtils {
	
	//Tee-hee
	private VoxelUtils() {}
	
	public static VoxelShape rotateVoxel(Direction from, Direction to, VoxelShape shape) {
		VoxelShape[] buffer = new VoxelShape[]{ shape, Shapes.empty() };

		int times = 0;
		Direction dir = from;
		while(dir != to) {
			times++;
			dir = dir.getClockWise();
		}
		
		for (int i = 0; i < times; i++) {
			buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = Shapes.or(buffer[1], Shapes.box(1-maxZ, minY, minX, 1-minZ, maxY, maxX)));
			buffer[0] = buffer[1];
			buffer[1] = Shapes.empty();
		}

		return buffer[0];
	}

	public static VoxelShape flipVoxelY(VoxelShape shape) {
		VoxelShape[] buffer = new VoxelShape[]{ Shapes.empty() };
		shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
			double newMinY = 1.0 - maxY;
			double newMaxY = 1.0 - minY;

			VoxelShape box = Shapes.box(minX, newMinY, minZ, maxX, newMaxY, maxZ);
			buffer[0] = Shapes.or(buffer[0], box);
		});
		return buffer[0];
	}
}
