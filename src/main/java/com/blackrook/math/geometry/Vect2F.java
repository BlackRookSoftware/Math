/*******************************************************************************
 * Copyright (c) 2019 Black Rook Software
 * This program and the accompanying materials are made available under 
 * the terms of the MIT License, which accompanies this distribution.
 ******************************************************************************/
package com.blackrook.math.geometry;

/**
 * A two dimensional ray that describes a defined direction with no origin. 
 * @author Matthew Tropiano
 */
public class Vect2F extends Tuple2F implements VectF
{
	/**
	 * Creates a new two-dimensional point, with (0,0) as its value. 
	 */
	public Vect2F()
	{
		this(0, 0);
	}

	/**
	 * Creates a copy of another Vect2D.
	 * @param v the source vector.
	 */
	public Vect2F(Vect2F v)
	{
		this(v.x, v.y);
	}

	/**
	 * Creates a new two-dimensional point, from a Vect1D.
	 * The missing dimensions are filled with zeroes.
	 * @param v the source vector.
	 */
	public Vect2F(Vect1F v)
	{
		this(v.x, 0);
	}

	/**
	 * Creates a new two-dimensional vector. 
	 * @param x		the initial x-coordinate value of this vector.
	 * @param y		the initial y-coordinate value of this vector.
	 */
	public Vect2F(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Creates a new two-dimensional vector from two points
	 * (a vector from one point to the other).
	 * @param source	source point. 
	 * @param dest		destination point. 
	 */
	public Vect2F(Point2F source, Point2F dest)
	{
		set(source, dest);
	}
	
	/**
	 * Creates a new two-dimensional vector from the components of two points
	 * (a vector from one point to the other).
	 * @param sourceX	source x-coordinate. 
	 * @param sourceY	source y-coordinate. 
	 * @param destX		destination x-coordinate. 
	 * @param destY		destination y-coordinate.
	 */
	public Vect2F(float sourceX, float sourceY, float destX, float destY)
	{
		set(sourceX, sourceY, destX, destY);
	}
	
	/**
	 * Sets this vector's value using two points
	 * (a vector from one point to the other).
	 * @param source	source point. 
	 * @param dest		destination point. 
	 */
	public void set(Point2F source, Point2F dest)
	{
		x = dest.x - source.x;
		y = dest.y - source.y;
	}

	/**
	 * Sets this vector's value using the components of two points
	 * (a vector from one point to the other).
	 * @param sourceX	source x-coordinate. 
	 * @param sourceY	source y-coordinate. 
	 * @param destX		destination x-coordinate. 
	 * @param destY		destination y-coordinate.
	 */
	public void set(float sourceX, float sourceY, float destX, float destY)
	{
		x = destX - sourceX;
		y = destY - sourceY;
	}

}
