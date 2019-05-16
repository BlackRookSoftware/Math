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
public class Vect1D extends Tuple1D implements VectD
{
	
	/**
	 * Creates a new one-dimensional point, with 0 as its value. 
	 */
	public Vect1D()
	{
		this(0);
	}
	
	/**
	 * Creates a copy of another Vect1D.
	 * @param v the source vector.
	 */
	public Vect1D(Vect1D v)
	{
		this(v.x);
	}
	
	/**
	 * Creates a new one-dimensional vector. 
	 * @param x the initial x-coordinate value of this point.
	 */
	public Vect1D(double x)
	{
		this.x = x;
	}
	
	/**
	 * Creates a new one-dimensional vector from two points
	 * (a vector from one point to the other).
	 * @param source source point. 
	 * @param dest dest point. 
	 */
	public Vect1D(Point1D source, Point1D dest)
	{
		x = dest.x - source.x;
	}
	
	/**
	 * Sets this vector's value using two points
	 * (a vector from one point to the other).
	 * @param source source point. 
	 * @param dest dest point. 
	 */
	public void set(Point1D source, Point1D dest)
	{
		x = dest.x - source.x;
	}
	
}
