/*******************************************************************************
 * Copyright (c) 2019 Black Rook Software
 * This program and the accompanying materials are made available under 
 * the terms of the MIT License, which accompanies this distribution.
 ******************************************************************************/
package com.blackrook.math.geometry;

import com.blackrook.math.util.Utils;

/**
 * Abstract, two-dimensional set of coordinates.
 * @author Matthew Tropiano
 */
public abstract class Tuple2D extends Tuple1D
{
	/** Y-coordinate value. */
	public double y;

	/**
	 * Creates a new two-dimensional point, with (0, 0) as its value. 
	 */
	public Tuple2D()
	{
		this(0, 0);
	}

	/**
	 * Creates a copy of another Tuple2D.
	 * @param v the source tuple.
	 */
	public Tuple2D(Tuple2D v)
	{
		this(v.x, v.y);
	}

	/**
	 * Creates a new two-dimensional point, from a Tuple1D.
	 * The missing dimensions are filled with zeroes.
	 * @param v the source tuple.
	 */
	public Tuple2D(Tuple1D v)
	{
		this(v.x, 0);
	}

	/**
	 * Creates a new two-dimensional tuple. 
	 * @param x the initial x-coordinate value of this tuple.
	 * @param y the initial y-coordinate value of this tuple.
	 */
	public Tuple2D(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Sets the values of this tuple.
	 * @param x the x value.
	 * @param y the y value.
	 */
	public void set(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Sets the values of this tuple.
	 * @param t the source tuple.
	 */
	public void set(Tuple2D t)
	{
		x = t.x;
		y = t.y;
	}

	@Override
	public double length()
	{
		return Utils.getVectorLength(x, y);
	}

	/**
	 * @return the squared length of this tuple (no square root step at the end).
	 */
	public double squareLength()
	{
		return Utils.getVectorLengthSquared(x, y);
	}

	/**
	 * Yields the dot product of a Tuple2D with this one.
	 * @param v the tuple to use with this one.
	 * @return the dot product.
	 */
	public double dot(Tuple2D v)
	{
		return Utils.getVectorDotProduct(x, y, v.x, v.y);
	}

	@Override
	public boolean isZero()
	{
		return x == 0 && y == 0;
	}

	/**
	 * Projects this tuple onto another tuple. This tuple's data is replaced by the result.
	 * @param v	the target tuple to project this tuple onto.
	 */
	public void projectOnto(Tuple2D v)
	{
		projectOnto(this,v,this);
	}

	/**
	 * Adds another Tuple2D to this one. This tuple's data is replaced by the result.
	 * @param v	the tuple to add to this one.
	 */
	public void add(Tuple2D v)
	{
		add(this,v,this);
	}

	@Override
	public void setLength(double len)
	{
		setLength(this,len,this);
	}

	@Override
	public void negate()
	{
		negate(this,this);
	}

	@Override
	public void normalize()
	{
		normalize(this,this);
	}

	@Override
	public void scale(double s)
	{
		scale(s,this,this);
	}

	/**
	 * Scales this tuple.
	 * @param sx scalar factor for x-axis. 
	 * @param sy scalar factor for y-axis. 
	 */
	public void scale(double sx, double sy)
	{
		scale(sx,sy,this,this);
	}

	@Override
	public void rotateX(double radians)
	{
		rotateX(radians,this,this);
	}

	@Override
	public void rotateY(double radians)
	{
		rotateY(radians,this,this);
	}

	@Override
	public void rotateZ(double radians)
	{
		rotateZ(radians,this,this);
	}

	/**
	 * Turns this tuple into its left-hand normal.
	 */
	public void leftNormal()
	{
		leftNormal(this, this);
	}

	/**
	 * Turns this tuple into its right-hand normal.
	 */
	public void rightNormal()
	{
		rightNormal(this, this);
	}

	@Override
	public String toString()
	{
		return "["+x+", "+y+"]";
	}

	/**
	 * Compares tuple components.
	 * @param p the other tuple.
	 * @return a positive number if greater, negative if less, zero if equal.
	 */
	public double compareTo(Tuple2D p)
	{
		return (x - p.x) + (y - p.y);
	}

	/**
	 * Returns true if the target has the same component values as this one. 
	 * @param v the other tuple.
	 * @return true if so, false if not.
	 */
	public boolean equals(Tuple2D v)
	{
		return x == v.x && y == v.y;
	}

	@Override
	public int hashCode()
	{
		return Double.hashCode(x) + Double.hashCode(y);
	}

	/**
	 * Gets the distance in units from this tuple to another.
	 * @param tuple the other tuple.
	 * @return the distance.
	 */
	public double getDistanceTo(Tuple2D tuple)
	{
		return Math.sqrt(getSquareDistanceTo(tuple));
	}

	/**
	 * Gets the square distance in units from this tuple to another (no square root step at the end).
	 * @param tuple the other tuple.
	 * @return the squared distance.
	 */
	public double getSquareDistanceTo(Tuple2D tuple)
	{
		double dx = tuple.x - x;
		double dy = tuple.y - y;
		return dx*dx + dy*dy;
	}

	/**
	 * Projects a tuple onto another tuple.
	 * @param source the source tuple.
	 * @param target the target tuple.
	 * @param out the output tuple.
	 */
	public static void projectOnto(Tuple2D source, Tuple2D target, Tuple2D out)
	{
		double dotp = source.dot(target);
		double tx = target.x;
		double ty = target.y;
		double fact = tx*tx + ty*ty;
		double dpofact = dotp/fact;
		out.x = dpofact * tx;
		out.y = dpofact * ty;
	}

	/**
	 * Adds two tuples and puts the result into a third.
	 * @param v1 the first tuple.
	 * @param v2 the second tuple.
	 * @param out the output tuple.
	 */
	public static void add(Tuple2D v1, Tuple2D v2, Tuple2D out)
	{
		out.x = v1.x + v2.x;
		out.y = v1.y + v2.y;
	}

	/**
	 * Scales a tuple to a certain length.
	 * @param in the input tuple.
	 * @param len the new length of the tuple.
	 * @param out the output tuple.
	 */
	public static void setLength(Tuple2D in, double len, Tuple2D out)
	{
		out.x = in.x;
		out.y = in.y;
		out.normalize();
		if (len != 1)
			out.scale(len);
	}

	/**
	 * Negates a tuple. Puts result in the output tuple.
	 * @param in the input tuple.
	 * @param out the output tuple.
	 */
	public static void negate(Tuple2D in, Tuple2D out)
	{
		out.x = -in.x;
		out.y = -in.y;
	}

	/**
	 * Turns this tuple into a unit tuple of length 1, while keeping direction intact.
	 * @param in the input tuple.
	 * @param out the output tuple.
	 */
	public static void normalize(Tuple2D in, Tuple2D out)
	{
		double len = in.length();
		out.x = in.x / len;
		out.y = in.y / len;
	}

	/**
	 * Scales this tuple on all axes.
	 * @param s scalar factor. 
	 * @param in the input tuple.
	 * @param out the output tuple.
	 */
	public static void scale(double s, Tuple2D in, Tuple2D out)
	{
		out.x = in.x*s;
		out.y = in.y*s;
	}

	/**	
	 * Scales this tuple.
	 * @param sx scalar factor for x-axis. 
	 * @param sy scalar factor for y-axis. 
	 * @param in the input tuple.
	 * @param out the output tuple.
	 */
	public static void scale(double sx, double sy, Tuple2D in, Tuple2D out)
	{
		out.x = in.x*sx;
		out.y = in.y*sy;
	}

	/**
	 * Gets the left-hand normal of this tuple.
	 * @param in the input tuple.
	 * @param out the output tuple.
	 */
	public static void leftNormal(Tuple2D in, Tuple2D out)
	{
		double newx = -in.y;
		double newy = in.x;
		out.x = newx;
		out.y = newy;
	}

	/**
	 * Gets the right-hand normal of this tuple.
	 * @param in the input tuple.
	 * @param out the output tuple.
	 */
	public static void rightNormal(Tuple2D in, Tuple2D out)
	{
		double newx = in.y;
		double newy = -in.x;
		out.x = newx;
		out.y = newy;
	}

	/**
	 * Rotates this tuple around the X axis.
	 * @param radians the angle in radians to rotate.
	 * @param in the input tuple.
	 * @param out the output tuple.
	 */
	public static void rotateX(double radians, Tuple2D in, Tuple2D out)
	{
		out.x = in.x;
		out.y = in.y*Math.cos(radians);
	}

	/**
	 * Rotates this tuple around the Y axis. 
	 * @param radians the angle in radians to rotate.
	 * @param in the input tuple.
	 * @param out the output tuple.
	 */
	public static void rotateY(double radians, Tuple2D in, Tuple2D out)
	{
		out.x = in.x*Math.cos(radians);
		out.y = in.y;
	}

	/**
	 * Rotates this tuple around the Z axis. 
	 * @param radians the angle in radians to rotate.
	 * @param in the input tuple.
	 * @param out the output tuple.
	 */
	public static void rotateZ(double radians, Tuple2D in, Tuple2D out)
	{
		double newx = in.x*Math.cos(radians)-in.y*Math.sin(radians);
		double newy = in.x*Math.sin(radians)+in.y*Math.cos(radians);
		out.x = newx;
		out.y = newy;
	}

}
