/*******************************************************************************
 * Copyright (c) 2019 Black Rook Software
 * This program and the accompanying materials are made available under 
 * the terms of the MIT License, which accompanies this distribution.
 ******************************************************************************/
package com.blackrook.math.easing;

/**
 * Easing type interface for custom easings.
 * @author Matthew Tropiano
 */
public interface EasingType
{
	/**
	 * Samples this easing to get the final output value for interpolation.
	 * An input time of 0f and less should return 0f. An input time of 1f or greater should return 1f.  
	 * @param inputScalar the input scalar (between 0 and 1, inclusively).
	 * @return the output value  (between 0 and 1, inclusively).
	 */
	public double getScaling(double inputScalar);
	
}
