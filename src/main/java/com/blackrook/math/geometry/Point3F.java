/*******************************************************************************
 * Copyright (c) 2019 Black Rook Software
 * This program and the accompanying materials are made available under 
 * the terms of the MIT License, which accompanies this distribution.
 ******************************************************************************/
package com.blackrook.math.geometry;

/**
 * A three dimensional point in space.  
 * @author Matthew Tropiano
 */
public class Point3F extends Tuple3F implements PointF
{
	/**
	 * Creates a new three-dimensional point, from a Point3D.
	 * The missing dimensions are filled with zeroes.
	 * @param p the source point.
	 */
	public Point3F(Point3F p)
	{
		super(p.x,p.y,p.z);
	}
	
	/**
	 * Creates a new three-dimensional point, from a Point2D.
	 * The missing dimensions are filled with zeroes.
	 * @param p the source point.
	 */
	public Point3F(Point2F p)
	{
		super(p.x,p.y,0f);
	}
	
	/**
	 * Creates a new three-dimensional point, from a Point1D.
	 * The missing dimensions are filled with zeroes.
	 * @param p the source point.
	 */
	public Point3F(Point1F p)
	{
		super(p.x,0f,0f);
	}
	
	/**
	 * Creates a new three-dimensional point, with (0,0,0) as its value. 
	 */
	public Point3F()
	{
		super(0,0,0);
	}
	
	/**
	 * Creates a new three-dimensional point. 
	 * @param x	the initial x-coordinate value of this point.
	 * @param y	the initial y-coordinate value of this point.
	 * @param z	the initial z-coordinate value of this point.
	 */
	public Point3F(float x, float y, float z)
	{
		super(x,y,z);
	}

	/**
	 * Gets the closest point in a list to this one.
	 * If the list is empty, null is returned.
	 * @param points the list of points.
	 * @return the closest point or null if no points provided.
	 */
	public Point3F getClosestPoint(Point3F ... points)
	{
		if (points.length == 0)
			return null;
		else if (points.length == 1)
			return points[0];
		else 
		{
			float len = Float.MAX_VALUE; 
			float next = 0; 
			Point3F out = null;
			for (int i = 0; i < points.length; i++)
			{
				next = getDistanceTo(points[i]);
				if (next < len)
				{
					out = points[i];
					len = next;
				}
			}
			return out;
		}
	}

}
