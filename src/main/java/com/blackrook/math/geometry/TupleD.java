/*******************************************************************************
 * Copyright (c) 2019 Black Rook Software
 * This program and the accompanying materials are made available under 
 * the terms of the MIT License, which accompanies this distribution.
 ******************************************************************************/
package com.blackrook.math.geometry;

/**
 * Common interface for tuples, or associated sets 
 * of numeric double-precision coordinates.
 * @author Matthew Tropiano
 */
public interface TupleD
{

	/**
	 * Returns the distance of this tuple from the origin.
	 * @return the distance of this tuple from the origin.
	 */
	public double length();

	/**
	 * Returns true if this tuple is zero, false otherwise.
	 * @return true if this tuple is zero, false otherwise.
	 */
	public boolean isZero();

	/**
	 * Turns this tuple into a unit tuple of length 1, while keeping direction intact.
	 */
	public void normalize();

	/**
	 * Negates this vector.
	 */
	public void negate();

	/**
	 * Scales this tuple.
	 * @param s scalar factor. 
	 */
	public void scale(double s);

	/**
	 * Rotates this tuple around the zero origin's X axis.
	 * @param radians the angle in radians to rotate.
	 */
	public void rotateX(double radians);

	/**
	 * Rotates this tuple around the zero origin's Y axis.
	 * @param radians the angle in radians to rotate.
	 */
	public void rotateY(double radians);

	/**
	 * Rotates this tuple around the zero origin's Z axis.
	 * @param radians the angle in radians to rotate.
	 */
	public void rotateZ(double radians);

	/**
	 * Sets the distance of this tuple from the origin.
	 * @param d	the new distance.
	 */
	public void setLength(double d);

}
