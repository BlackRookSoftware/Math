/*******************************************************************************
 * Copyright (c) 2019 Black Rook Software
 * This program and the accompanying materials are made available under 
 * the terms of the MIT License, which accompanies this distribution.
 ******************************************************************************/
package com.blackrook.math.geometry;

import com.blackrook.math.struct.Utils;

/**
 * Abstract, three-dimensional set of coordinates.
 * @author Matthew Tropiano
 */
public abstract class Tuple3D extends Tuple2D
{
	/** Z-coordinate value. */
	public double z;

	/**
	 * Creates a copy of another Vect3D.
	 * @param t the source tuple.
	 */
	public Tuple3D(Tuple3D t)
	{
		this(t.x,t.y,t.z);
	}

	/**
	 * Creates a new three-dimensional tuple, from a Tuple2D.
	 * The missing dimensions are filled with zeroes.
	 * @param t the source tuple.
	 */
	public Tuple3D(Tuple2D t)
	{
		this(t.x,t.y,0);
	}

	/**
	 * Creates a new three-dimensional tuple, from a Tuple1D.
	 * The missing dimensions are filled with zeroes.
	 * @param t the source tuple.
	 */
	public Tuple3D(Tuple1D t)
	{
		this(t.x,0,0);
	}

	/**
	 * Creates a new three-dimensional tuple, with (0,0,0) as its value. 
	 */
	public Tuple3D()
	{
		this(0,0,0);
	}

	/**
	 * Creates a new three-dimensional tuple. 
	 * @param x		the initial x-coordinate value of this tuple.
	 * @param y		the initial y-coordinate value of this tuple.
	 * @param z		the initial z-coordinate value of this tuple.
	 */
	public Tuple3D(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Sets the values of this tuple.
	 * @param x		the x value.
	 * @param y		the y value.
	 * @param z		the z value.
	 */
	public void set(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Sets the values of this tuple.
	 * @param t the source tuple.
	 */
	public void set(Tuple3D t)
	{
		x = t.x;
		y = t.y;
		z = t.z;
	}

	@Override
	public double length()
	{
		return Utils.getVectorLength(x, y, z);
	}

	@Override
	public double squareLength()
	{
		return Utils.getVectorLengthSquared(x, y, z);
	}

	/**
	 * Yields the dot product of a Tuple3D with this one.
	 * @param v the tuple to use with this one.
	 * @return the dot product.
	 */
	public double dot(Tuple3D v)
	{
		return Utils.getVectorDotProduct(x, y, z, v.x, v.y, v.z);
	}

	@Override
	public boolean isZero()
	{
		return x == 0 && y == 0 && z == 0;
	}

	/**
	 * Projects this tuple onto another tuple. This tuple's data is replaced by the result.
	 * @param v	the target tuple to project this tuple onto.
	 */
	public void projectOnto(Tuple3D v)
	{
		projectOnto(this,v,this);
	}

	/**
	 * Adds another Tuple2D to this one. This tuple's data is replaced by the result.
	 * @param v the tuple to add to this one.
	 */
	public void add(Tuple3D v)
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
	 * Scales this tuple.
	 * @param sx scalar factor for x-axis. 
	 * @param sy scalar factor for y-axis. 
	 * @param sz scalar factor for z-axis. 
	 */
	public void scale(double sx, double sy, double sz)
	{
		scale(sx,sy,sz,this,this);
	}

	/**
	 * Crosses this tuple with another (cross product).
	 * @param v the input tuple.
	 */
	public void cross(Tuple3D v)
	{
		cross(this,v,this);
	}

	@Override
	public String toString()
	{
		return "["+x+", "+y+", "+z+"]";
	}

	/**
	 * Returns true if the target has the same component values as this one. 
	 * @param v the other tuple.
	 * @return true if so, false if not.
	 */
	public boolean equals(Tuple3D v)
	{
		return x == v.x && y == v.y && z == v.z;
	}

	@Override
	public int hashCode()
	{
		return Double.hashCode(x) + Double.hashCode(y) + Double.hashCode(z);
	}

	/**
	 * Gets the distance in units from this tuple to another.
	 * @param point the other tuple.
	 * @return the distance.
	 */
	public double getDistanceTo(Tuple3D point)
	{
		return Math.sqrt(getSquareDistanceTo(point));
	}

	/**
	 * Gets the square distance in units from this tuple to another (no square root step at the end).
	 * @param point the other tuple.
	 * @return the squared distance.
	 */
	public double getSquareDistanceTo(Tuple3D point)
	{
		double dx = point.x - x;
		double dy = point.y - y;
		double dz = point.z - z;
		return dx*dx + dy*dy + dz*dz;
	}

	/**
	 * Projects this tuple onto another tuple.
	 * @param v			the source tuple.
	 * @param target	the target tuple.
	 * @param out		the output tuple.
	 */
	public static void projectOnto(Tuple3D v, Tuple3D target, Tuple3D out)
	{
		double dotp = v.dot(target);
		double fact = target.x*target.x + target.y*target.y + target.z*target.z;
		double dpofact = dotp/fact;
		out.x = dpofact * target.x;
		out.y = dpofact * target.y;
		out.z = dpofact * target.z;
	}

	/**
	 * Adds two tuples and puts the result into a third.
	 * @param v1	the first tuple.
	 * @param v2	the second tuple.
	 * @param out	the output tuple.
	 */
	public static void add(Tuple3D v1, Tuple3D v2, Tuple3D out)
	{
		out.x = v1.x + v2.x;
		out.y = v1.y + v2.y;
		out.z = v1.z + v2.z;
	}

	/**
	 * Turns this tuple into a unit tuple of length 1, while keeping direction intact.
	 * @param in	the input tuple.
	 * @param out	the output tuple.
	 */
	public static void normalize(Tuple3D in, Tuple3D out)
	{
		double len = in.length();
		out.x = in.x / len;
		out.y = in.y / len;
		out.z = in.z / len;
	}

	/**
	 * Scales a tuple to a certain length.
	 * @param in	the input tuple.
	 * @param len	the new length of the tuple.
	 * @param out	the output tuple.
	 */
	public static void setLength(Tuple3D in, double len, Tuple3D out)
	{
		out.x = in.x;
		out.y = in.y;
		out.z = in.z;
		out.normalize();
		if (len != 1)
			out.scale(len);
	}

	/**
	 * Negates a tuple. Puts result in the output tuple.
	 * @param in	the input tuple.
	 * @param out	the output tuple.
	 */
	public static void negate(Tuple3D in, Tuple3D out)
	{
		out.x = -in.x;
		out.y = -in.y;
		out.z = -in.z;
	}

	/**
	 * Scales this tuple on all axes.
	 * @param s		scalar factor. 
	 * @param in	the input tuple.
	 * @param out	the output tuple.
	 */
	public static void scale(double s, Tuple3D in, Tuple3D out)
	{
		out.x = in.x*s;
		out.y = in.y*s;
		out.z = in.z*s;
	}

	/**	
	 * Scales this tuple.
	 * @param sx	scalar factor for x-axis. 
	 * @param sy	scalar factor for y-axis. 
	 * @param sz	scalar factor for z-axis. 
	 * @param in	the input tuple.
	 * @param out	the output tuple.
	 */
	public static void scale(double sx, double sy, double sz, Tuple3D in, Tuple3D out)
	{
		out.x = in.x*sx;
		out.y = in.y*sy;
		out.z = in.z*sz;
	}

	/**
	 * Crosses this tuple with another (cross product).
	 * @param v1	the first tuple.
	 * @param v2	the second tuple.
	 * @param out	the output tuple.
	 */
	public static void cross(Tuple3D v1, Tuple3D v2, Tuple3D out)
	{
		double newx = v1.y*v2.z - v1.z*v2.y;
		double newy = v1.z*v2.x - v1.x*v2.z;
		double newz = v1.x*v2.y - v1.y*v2.x;
		out.x = newx;
		out.y = newy;
		out.z = newz;
	}

	/**
	 * Rotates this tuple around the X axis.
	 * @param radians	 the angle in radians to rotate.
	 * @param in	the input tuple.
	 * @param out	the output tuple.
	 */
	public static void rotateX(double radians, Tuple3D in, Tuple3D out)
	{
		double newy = in.y*Math.cos(radians)-in.z*Math.sin(radians);
		double newz = in.y*Math.sin(radians)+in.z*Math.cos(radians);
		out.x = in.x;
		out.y = newy;
		out.z = newz;
	}

	/**
	 * Rotates this tuple around the Y axis. 
	 * @param radians	 the angle in radians to rotate.
	 * @param in	the input tuple.
	 * @param out	the output tuple.
	 */
	public static void rotateY(double radians, Tuple3D in, Tuple3D out)
	{
		double newx = in.x*Math.cos(radians)+in.z*Math.sin(radians);
		double newz = -in.x*Math.sin(radians)+in.z*Math.cos(radians);
		out.x = newx;
		out.y = in.y;
		out.z = newz;
	}

	/**
	 * Rotates this tuple around the Z axis. 
	 * @param radians	 the angle in radians to rotate.
	 * @param in	the input tuple.
	 * @param out	the output tuple.
	 */
	public static void rotateZ(double radians, Tuple3D in, Tuple3D out)
	{
		double newx = in.x*Math.cos(radians)-in.y*Math.sin(radians);
		double newy = in.x*Math.sin(radians)+in.y*Math.cos(radians);
		out.x = newx;
		out.y = newy;
		out.z = in.z;
	}

}
