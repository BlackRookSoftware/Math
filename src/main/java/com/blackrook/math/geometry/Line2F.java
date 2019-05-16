/*******************************************************************************
 * Copyright (c) 2019 Black Rook Software
 * This program and the accompanying materials are made available under 
 * the terms of the MIT License, which accompanies this distribution.
 ******************************************************************************/
package com.blackrook.math.geometry;


/**
 * Two-dimensional line segment.
 * @author Matthew Tropiano
 */
public class Line2F extends LineF<Point2F>
{
	/**
	 * Creates a line segment with two points, both (0, 0).
	 */
	public Line2F()
	{
		this(0.0f, 0.0f, 0.0f, 0.0f);
	}
	
	/**
	 * Creates a line segment from two points.
	 * @param a the first point.
	 * @param b the second point.
	 */
	public Line2F(Point2F a, Point2F b)
	{
		this(a.x, a.y, b.x, b.y);
	}
	
	/**
	 * Creates a line segment from a set from 
	 * coordinates making up two points.
	 * @param ax start point x-coordinate.
	 * @param ay start point y-coordinate.
	 * @param bx end point x-coordinate.
	 * @param by end point y-coordinate.
	 */
	public Line2F(float ax, float ay, float bx, float by)
	{
		this.pointA = new Point2F(ax, ay);
		this.pointB = new Point2F(bx, by);
	}
	
	/**
	 * Creates a line by copying another.
	 * @param line the source line
	 */
	public Line2F(Line2F line)
	{
		this.pointA = new Point2F(line.pointA);
		this.pointB = new Point2F(line.pointB);
	}
	
	/**
	 * @return the length of this line in units.
	 */
	public float getLength()
	{
		return pointA.getDistanceTo(pointB);
	}
	
	@Override
	public Line2F copy()
	{
		return new Line2F(this);
	}
	
}
