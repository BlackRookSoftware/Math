/*******************************************************************************
 * Copyright (c) 2019 Black Rook Software
 * This program and the accompanying materials are made available under 
 * the terms of the MIT License, which accompanies this distribution.
 ******************************************************************************/
package com.blackrook.math.geometry;


/**
 * Describes a line segment.
 * @author Matthew Tropiano
 */
public abstract class LineD<T extends PointD>
{
	/** Starting point. */
	public T pointA;
	/** Ending point. */
	public T pointB;

	/**
	 * Returns the reference to the starting point.
	 * @return a reference to starting point.
	 */
	public T getPointA()
	{
		return pointA;
	}
	
	/**
	 * Returns the reference to the ending point.
	 * @return a reference to ending point.
	 */
	public T getPointB()
	{
		return pointB;
	}
	
	/** @return a copy of this line. */
	public abstract LineD<T> copy();

	@Override
	public String toString()
	{
		return pointA + " to " + pointB; 
	}
	
}
