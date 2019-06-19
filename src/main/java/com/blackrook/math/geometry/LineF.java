/*******************************************************************************
 * Copyright (c) 2019 Black Rook Software
 * This program and the accompanying materials are made available under 
 * the terms of the MIT License, which accompanies this distribution.
 ******************************************************************************/
package com.blackrook.math.geometry;


/**
 * Describes a line segment.
 * @param <P> the point type.
 * @author Matthew Tropiano
 */
public abstract class LineF<P extends PointF>
{
	/** Starting point. */
	public P pointA;
	/** Ending point. */
	public P pointB;

	/**
	 * Returns the reference to the starting point.
	 * @return a reference to starting point.
	 */
	public P getPointA()
	{
		return pointA;
	}
	
	/**
	 * Returns the reference to the ending point.
	 * @return a reference to ending point.
	 */
	public P getPointB()
	{
		return pointB;
	}
	
	/** @return a copy of this line. */
	public abstract LineF<P> copy();

	@Override
	public String toString()
	{
		return pointA + " to " + pointB; 
	}
	
}
