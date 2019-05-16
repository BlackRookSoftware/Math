/*******************************************************************************
 * Copyright (c) 2019 Black Rook Software
 * This program and the accompanying materials are made available under 
 * the terms of the MIT License, which accompanies this distribution.
 ******************************************************************************/
package com.blackrook.math.wave;

/**
 * Describes all types of sampleable waveforms.
 * @author Matthew Tropiano
 */
public interface WaveFormType
{
	/**
	 * Returns a value on the wave at a particular part of the wave's period.
	 * @param time a value from 0 to 1, describing a position along the period (0 = beginning, 1 = end).
	 * Depending on the WaveForm, this may wrap the input value around the interval [0,1] or clamp it.
	 * @return a value within the wave's amplitude.
	 */
	public double getSample(double time);
	
	/**
	 * @return this wave's amplitude.
	 */
	public double getAmplitude();

}
