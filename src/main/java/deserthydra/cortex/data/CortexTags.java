package deserthydra.cortex.data;

import deserthydra.cortex.util.CortexUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class CortexTags {
	public static class Blocks {
		public static final TagKey<Block> GRINDSTONE_GRINDING_TARGETS = TagKey.create(Registries.BLOCK, CortexUtils.id("grindstone_grinding_targets"));
	}
}
