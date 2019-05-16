/*******************************************************************************
 * Copyright (c) 2019 Black Rook Software
 * This program and the accompanying materials are made available under 
 * the terms of the MIT License, which accompanies this distribution.
 ******************************************************************************/
package com.blackrook.math.geometry;

/**
 * A one dimensional ray that describes a defined direction with no origin.
 * @author Matthew Tropiano
 */
public class Vect1F extends Tuple1F implements VectF
{
	
	/**
	 * Creates a new one-dimensional point, with 0 as its value. 
	 */
	public Vect1F()
	{
		this(0);
	}
	
	/**
	 * Creates a copy of another Vect1D.
	 * @param v the source vector.
	 */
	public Vect1F(Vect1F v)
	{
		this(v.x);
	}
	
	/**
	 * Creates a new one-dimensional vector. 
	 * @param x the initial x-coordinate value of this point.
	 */
	public Vect1F(float x)
	{
		this.x = x;
	}
	
	/**
	 * Creates a new one-dimensional vector from two points
	 * (a vector from one point to the other).
	 * @param source source point. 
	 * @param dest dest point. 
	 */
	public Vect1F(Point1F source, Point1F dest)
	{
		x = dest.x - source.x;
	}
	
	/**
	 * Sets this vector's value using two points
	 * (a vector from one point to the other).
	 * @param source source point. 
	 * @param dest dest point. 
	 */
	public void set(Point1F source, Point1F dest)
	{
		x = dest.x - source.x;
	}
	
}
