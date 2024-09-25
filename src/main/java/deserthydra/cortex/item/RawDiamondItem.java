package deserthydra.cortex.item;

import net.minecraft.block.Blocks;
import net.minecraft.block.dispenser.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.world.event.GameEvent;

// This could be a mixin if not for the wacky way CortexItems and CortexBlocks work
// That probably should be fixed
public class RawDiamondItem extends Item {
	public RawDiamondItem(Settings settings) {
		super(settings);
		DispenserBlock.registerBehavior(this, new FallibleItemDispenserBehavior() {
			@Override
			protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
				var world = pointer.world();
				var blockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
				if (world.getBlockState(blockPos).getBlock() == Blocks.GRINDSTONE) {
					this.setSuccess(true);
					world.emitGameEvent(null, GameEvent.ITEM_INTERACT_FINISH, pointer.pos());
					return this.consumeWithRemainder(pointer, stack, new ItemStack(Items.DIAMOND));
				} else {
					return super.dispenseSilently(pointer, stack);
				}
			}
		});
	}
}
