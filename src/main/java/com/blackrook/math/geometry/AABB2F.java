/*******************************************************************************
 * Copyright (c) 2019 Black Rook Software
 * This program and the accompanying materials are made available under 
 * the terms of the MIT License, which accompanies this distribution.
 ******************************************************************************/
package com.blackrook.math.geometry;

/**
 * Object that describes an origin-less axis-aligned bounding box.
 * @author Matthew Tropiano
 */
public class AABB2F
{
	/** Rectangle half-width. */
	public float halfWidth;
	/** Rectangle half-height. */
	public float halfHeight;
	
	/**
	 * Creates a box with.
	 */
	public AABB2F()
	{
		this(0, 0);
	}
	
	/**
	 * Creates a two-dimensional AABB using a rectangle.
	 * @param r the source rectangle to copy.
	 */
	public AABB2F(Rectangle2F r)
	{
		this(r.width / 2, r.height / 2);
	}
	
	/**
	 * Creates a new two-dimensional AABB.
	 * @param halfWidth box half-width.
	 * @param halfHeight box half-height.
	 */
	public AABB2F(float halfWidth, float halfHeight)
	{
		this.halfWidth = halfWidth;
		this.halfHeight = halfHeight;
	}
	
	/**
	 * Projects this bounding box onto a line, <code>out</code>.
	 * @param target the target tuple, treated as a vector.
	 * @param out the output line.
	 */
	public void projectOnto(Tuple2F target, Line2F out)
	{
		if (target.x != 0.0)
		{
			double slope = target.y / target.x;
			double theta = Math.atan(slope);
			if (theta < 0)
				theta = -theta;
			double hl = (halfHeight * Math.sin(theta)) + (halfWidth * Math.cos(theta));
			double dx = (hl * Math.cos(theta));
			double dy = (hl * Math.sin(theta));
			
			boolean swap = target.x < 0 ^ target.y < 0;

			if (swap)
			{
				out.pointA.x = (float)-dx;
				out.pointA.y = (float)dy;
				out.pointB.x = (float)dx;
				out.pointB.y = (float)-dy;
			}
			else
			{
				out.pointA.x = (float)-dx;
				out.pointA.y = (float)-dy;
				out.pointB.x = (float)dx;
				out.pointB.y = (float)dy;
			}
		}
		else
		{
			out.pointA.x = 0f;
			out.pointA.y = (float)-halfHeight;
			out.pointB.x = 0f;
			out.pointB.y = (float)halfHeight;
		}
	}

}
