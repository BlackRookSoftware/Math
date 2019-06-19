/*******************************************************************************
 * Copyright (c) 2019 Black Rook Software
 * This program and the accompanying materials are made available under 
 * the terms of the MIT License, which accompanies this distribution.
 ******************************************************************************/
package com.blackrook.math.geometry;


/**
 * Data that represents a plane, consisting of a normal vector
 * and distance from the origin. 
 * @param <V> the vector type.
 * @author Matthew Tropiano
 */
public abstract class PlaneD<V extends VectD>
{
	/** Normal vector. */
	public V normal;
	/** Distance from the origin. */
	public double distance;
	
	/**
	 * @return this plane's normal vector.
	 */
	public V getNormal()
	{
		return normal;
	}
	
	/**
	 * @return this plane's distance from the origin.
	 */
	public double getDistance()
	{
		return distance;
	}
	
	/**
	 * Sets this plane's distance from the origin.
	 * @param d the new distance.
	 */
	public void setDistance(double d)
	{
		distance = d;
	}
	
	/**
	 * Rotates this plane around the X axis.
	 * Basically just rotates the normal (shhh! don't tell!).
	 * @param radians	 the angle in radians to rotate.
	 */
	public void rotateX(double radians)
	{
		normal.rotateX(radians);
	}

	/**
	 * Rotates this plane around the Y axis.
	 * Basically just rotates the normal (shhh! don't tell!).
	 * @param radians	 the angle in radians to rotate.
	 */
	public void rotateY(double radians)
	{
		normal.rotateY(radians);
	}

	/**
	 * Rotates this plane around the Z axis.
	 * Basically just rotates the normal (shhh! don't tell!).
	 * @param radians	 the angle in radians to rotate.
	 */
	public void rotateZ(double radians)
	{
		normal.rotateZ(radians);
	}

	/**
	 * Scales this plane's distance from the origin.
	 * @param s		scalar factor. 
	 */
	public void scale(double s)
	{
		distance *= s;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(normal.toString());
		sb.append(' ');
		sb.append(distance);
		return sb.toString();
	}
	
	/** @return an exact copy of this Plane. */
	public abstract PlaneD<V> copy();
	
}
