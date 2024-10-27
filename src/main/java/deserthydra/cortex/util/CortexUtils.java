/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.util;

import net.minecraft.util.Identifier;

public class CortexUtils {
	public static final String MOD_ID = "cortex";

	private static final Identifier MOD_NAMESPACE_ID = Identifier.of(MOD_ID, "");

	public static Identifier id(String path) {
		return MOD_NAMESPACE_ID.withPath(path);
	}
}
