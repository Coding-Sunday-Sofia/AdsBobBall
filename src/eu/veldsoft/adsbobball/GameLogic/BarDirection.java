/*
  Copyright (c) 2012 Richard Martin. All rights reserved.
  Licensed under the terms of the BSD License, see LICENSE.txt
 */

package eu.veldsoft.adsbobball.GameLogic;

import eu.veldsoft.adsbobball.TouchDirection;

public enum BarDirection {
	VERTICAL, HORIZONTAL;

	public static BarDirection fromTouchDirection(
			final TouchDirection touchDirection) {
		if (touchDirection == TouchDirection.VERTICAL) {
			return VERTICAL;
		}

		return HORIZONTAL;
	}

}
