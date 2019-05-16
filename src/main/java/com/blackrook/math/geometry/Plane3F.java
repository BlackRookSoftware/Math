/*******************************************************************************
 * Copyright (c) 2019 Black Rook Software
 * This program and the accompanying materials are made available under 
 * the terms of the MIT License, which accompanies this distribution.
 ******************************************************************************/
package com.blackrook.math.geometry;


/**
 * A three-dimensional plane.
 * @author Matthew Tropiano
 */
public class Plane3F extends PlaneF<Vect3F>
{
	/**
	 * Creates a plane with a specific normal and distance from the origin. 
	 * @param normal	the surface normal (if the vector is not unit length, it will be normalized).
	 * @param distance	the distance of the plane from the origin.
	 */
	public Plane3F(Vect3F normal, float distance)
	{
		this.normal = normal;
		this.normal.normalize();
		this.distance = distance;
	}

	/**
	 * Creates a plane using three non-collinear points.
	 * It will compute the surface normal and distance based on the triangle created by these points.  
	 * @param p1	the first point on the plane.
	 * @param p2	the second point on the plane.
	 * @param p3	the third point on the plane.
	 */
	public Plane3F(Point3F p1, Point3F p2, Point3F p3)
	{
		normal = new Vect3F();
		Vect3F.cross(new Vect3F(p1,p2), new Vect3F(p1,p3), normal);
		normal.normalize();
		distance = normal.dot(p1);
	}
	
	/**
	 * Creates a plane using another plane (copy).
	 * @param plane	the plane to use.
	 */
	public Plane3F(Plane3F plane)
	{
		this(plane.normal, plane.distance);
	}

	@Override
	public Plane3F copy()
	{
		return new Plane3F(this);
	}

	
}
