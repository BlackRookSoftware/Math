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
public class Line2D extends LineD<Point2D>
{
	/**
	 * Creates a line segment with two points, both (0, 0).
	 */
	public Line2D()
	{
		this(0, 0, 0, 0);
	}
	
	/**
	 * Creates a line segment from two points.
	 * @param a the first point.
	 * @param b the second point.
	 */
	public Line2D(Point2D a, Point2D b)
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
	public Line2D(double ax, double ay, double bx, double by)
	{
		this.pointA = new Point2D(ax, ay);
		this.pointB = new Point2D(bx, by);
	}
	
	/**
	 * Creates a line by copying another.
	 * @param line the source line
	 */
	public Line2D(Line2D line)
	{
		this.pointA = new Point2D(line.pointA);
		this.pointB = new Point2D(line.pointB);
	}
	
	/**
	 * @return the length of this line in units.
	 */
	public double getLength()
	{
		return pointA.getDistanceTo(pointB);
	}
	
	@Override
	public Line2D copy()
	{
		return new Line2D(this);
	}
	
}
