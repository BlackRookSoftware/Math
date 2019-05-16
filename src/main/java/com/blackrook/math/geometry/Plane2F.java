/*******************************************************************************
 * Copyright (c) 2019 Black Rook Software
 * This program and the accompanying materials are made available under 
 * the terms of the MIT License, which accompanies this distribution.
 ******************************************************************************/
package com.blackrook.math.geometry;


/**
 * A two-dimensional hyperplane.
 * @author Matthew Tropiano
 */
public class Plane2F extends PlaneF<Vect2F>
{
	/**
	 * Creates a plane with a specific normal and distance from the origin. 
	 * @param normal	the surface normal (if the vector is not unit length, it will be normalized).
	 * @param distance	the distance of the plane from the origin.
	 */
	public Plane2F(Vect2F normal, float distance)
	{
		this.normal = new Vect2F(normal);
		this.normal.normalize();
		this.distance = distance;
	}

	/**
	 * Creates a plane using two colinear points.
	 * It will compute the surface normal and distance based on the line segment created by these points.  
	 * @param p1	the first point on the plane.
	 * @param p2	the second point on the plane.
	 */
	public Plane2F(Point2F p1, Point2F p2)
	{
		normal = new Vect2F(-(p2.y - p1.y), p2.x - p1.x);
		normal.normalize();
		distance = normal.dot(p1);
	}
	
	/**
	 * Creates a plane using another plane (copy).
	 * @param plane	the plane to use.
	 */
	public Plane2F(Plane2F plane)
	{
		this(plane.normal, plane.distance);
	}

	@Override
	public Plane2F copy()
	{
		return new Plane2F(this);
	}
}
