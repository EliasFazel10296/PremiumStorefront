/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 7/7/20 3:42 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.blurry.effect.view;

import android.content.Context;
import android.graphics.Bitmap;

public class EmptyBlurImpl implements BlurImpl {
	@Override
	public boolean prepare(Context context, Bitmap buffer, float radius) {
		return false;
	}

	@Override
	public void release() {

	}

	@Override
	public void blur(Bitmap input, Bitmap output) {

	}
}
