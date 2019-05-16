/*******************************************************************************
 * Copyright (c) 2019 Black Rook Software
 * This program and the accompanying materials are made available under 
 * the terms of the MIT License, which accompanies this distribution.
 ******************************************************************************/
package com.blackrook.math.wave;

import java.util.Random;

import com.blackrook.math.util.Utils;
import com.blackrook.math.wave.CustomWaveForm.InterpolationType;

/**
 * Utility methods for manipulating or displaying waves.
 * @author Matthew Tropiano
 */
public final class WaveUtils
{
	private WaveUtils() {}
	
	/**
	 * Creates a Perlin Noise Wave.
	 * @param random the random seeder to use for generating the random data.
	 * @param startingSamples the starting number of discrete samples. 
	 * @param persistance the change in amplitude in each iteration.
	 * @param steps how many iterations to use to create the noise function. 
	 * @param interpolationType the type of interpolation to use for sampling values between the discrete samples.
	 * @return the resulting noise wave. the total amplitude will not exceed the starting one.
	 */
	public static CustomWaveForm createNoiseWave(Random random, int startingSamples, 
			double persistance, int steps, InterpolationType interpolationType)
	{
		double amp = 1.0;
		int samples = startingSamples;
		CustomWaveForm out = null;
		for (int i = 0; i < steps; i++)
		{
			CustomWaveForm next = new CustomWaveForm(random, amp, samples, interpolationType);
			if (out != null) for (int x = 0; x < samples; x++)
			{
				next.amplitude = 1.0;
				next.setSampleValue(x, Utils.clampValue(next.getSampleValue(x) + out.getSample(x * (1.0 / samples)), -1.0, 1.0));
			}
			out = next;
			samples *= 2;
			amp = Math.pow(persistance, i+1);
		}
		return out;
	}
	
}
