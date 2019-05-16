/*******************************************************************************
 * Copyright (c) 2019 Black Rook Software
 * This program and the accompanying materials are made available under 
 * the terms of the MIT License, which accompanies this distribution.
 ******************************************************************************/
package com.blackrook.math.wave;

import java.util.Arrays;

/**
 * Polynomial structure for defining polynomials.
 * @author Matthew Tropiano
 */
public class Polynomial implements WaveFormType
{
	/** Starting exponent for polynomial. */
	protected int startingExponent;
	/** Polynomial coefficients. */
	protected double[] coefficients;
	
	/**
	 * Creates a new polynomial.
	 * @param startingExponent the starting exponent.
	 * @param coefficients the polynomial coefficients.
	 */
	public Polynomial(int startingExponent, double ... coefficients)
	{
		this.startingExponent = startingExponent;
		this.coefficients = Arrays.copyOf(coefficients, coefficients.length);
	}

	/**
	 * Always returns 1.
	 */
	@Override
	public double getAmplitude()
	{
		return 1;
	}

	@Override
	public double getSample(double time)
	{
		double out = 0;
		for (int i = 0; i < coefficients.length; i++)
			out += coefficients[i] * Math.pow(time, startingExponent + i);
		return out;
	}

	/**
	 * @return the starting exponent value. 
	 */
	public int getStartingExponent()
	{
		return startingExponent;
	}

	/**
	 * Gets the coefficients.
	 * @return a reference to this polynomial's coefficients.
	 */
	public double[] getCoefficients()
	{
		return coefficients;
	}

}
