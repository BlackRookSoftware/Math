/*******************************************************************************
 * Copyright (c) 2019 Black Rook Software
 * From Black Rook Base https://github.com/BlackRookSoftware/Base 
 * This program and the accompanying materials are made available under 
 * the terms of the MIT License, which accompanies this distribution.
 ******************************************************************************/
package com.blackrook.math.struct;

import java.util.Random;

/**
 * A class with static methods that perform "other" types of mathematics.
 * <p>
 * A bunch of the collision/intersection algorithms found in this class are 
 * adapted from Real-Time Collision Detection by Christer Ericson (ISBN-13: 978-1-55860-732-3).
 * <p>
 * <b>This is a subset of methods from Black Rook Base MathUtils and not meant to be used outside of the library.</b>
 * @author Matthew Tropiano
 */
public final class Utils
{
	/** PI over Two. Equivalent to <code> {@link Math#PI} / 2.0</code>. */
	public static final double PI_OVER_TWO = Math.PI / 2.0;
	/** Two PI. Equivalent to <code> 2.0 * {@link Math#PI}</code>. */
	public static final double TWO_PI = 2.0 * Math.PI;
	/** Three PI over Two. Equivalent to <code> 3.0 * {@link Math#PI} / 2.0</code>. */
	public static final double THREE_PI_OVER_TWO = 3.0 * Math.PI / 2.0;
	private Utils() {}
	
	/**
	 * Rotates the bits of a number to the left.
	 * @param n the input number.
	 * @param x the number of positions.
	 * @return the resultant number.
	 */
	public static int rotateLeft(int n, int x)
	{
		if (x < 0) return rotateRight(n, -x);
		x = x % 32;
		int m = ((1 << x) - 1) << (32 - x);
		return (n << x) | ((n & m) >>> (32 - x));
	}

	/**
	 * Rotates the bits of a number to the right.
	 * @param n the input number.
	 * @param x the number of positions.
	 * @return the resultant number.
	 */
	public static int rotateRight(int n, int x)
	{
		if (x < 0) return rotateLeft(n, -x);
		x = x % 32;
		int m = ((1 << x) - 1);
		return (n >>> x) | ((n & m) << (32 - x));
	}

	/**
	 * Coerces a double to the range bounded by lo and hi.
	 * <br>Example: clampValue(32,-16,16) returns 16.
	 * <br>Example: clampValue(4,-16,16) returns 4.
	 * <br>Example: clampValue(-1000,-16,16) returns -16.
	 * @param val the double.
	 * @param lo the lower bound.
	 * @param hi the upper bound.
	 * @return the value after being "forced" into the range.
	 */
	public static double clampValue(double val, double lo, double hi)
	{
		return Math.min(Math.max(val,lo),hi);
	}

	/**
	 * Coerces an integer to the range bounded by lo and hi, by "wrapping" the value.
	 * <br>Example: wrapValue(32,-16,16) returns 0.
	 * <br>Example: wrapValue(4,-16,16) returns 4.
	 * <br>Example: wrapValue(-1000,-16,16) returns 8.
	 * @param val the integer.
	 * @param lo the lower bound.
	 * @param hi the upper bound.
	 * @return the value after being "wrapped" into the range.
	 */
	public static int wrapValue(int val, int lo, int hi)
	{
		val = val - (int)(val - lo) / (hi - lo) * (hi - lo);
	   	if (val < 0)
	   		val = val + hi - lo;
	   	return val;
	}

	/**
	 * Coerces a short to the range bounded by lo and hi, by "wrapping" the value.
	 * <br>Example: wrapValue(32,-16,16) returns 0.
	 * <br>Example: wrapValue(4,-16,16) returns 4.
	 * <br>Example: wrapValue(-1000,-16,16) returns 8.
	 * @param val the short.
	 * @param lo the lower bound.
	 * @param hi the upper bound.
	 * @return the value after being "wrapped" into the range.
	 */
	public static short wrapValue(short val, short lo, short hi)
	{
		val = (short)(val - (val - lo) / (hi - lo) * (hi - lo));
	   	if (val < 0)
	   		val = (short)(val + hi - lo);
	   	return val;
	}

	/**
	 * Coerces a float to the range bounded by lo and hi, by "wrapping" the value.
	 * <br>Example: wrapValue(32,-16,16) returns 0.
	 * <br>Example: wrapValue(4,-16,16) returns 4.
	 * <br>Example: wrapValue(-1000,-16,16) returns 8.
	 * @param val the float.
	 * @param lo the lower bound.
	 * @param hi the upper bound.
	 * @return the value after being "wrapped" into the range.
	 */
	public static float wrapValue(float val, float lo, float hi)
	{
		float range = hi - lo;
		val = val - lo;
		val = (val % range);
		if (val < 0.0)
			val = val + hi;
		return val;
	}

	/**
	 * Coerces a double to the range bounded by lo and hi, by "wrapping" the value.
	 * <br>Example: wrapValue(32,-16,16) returns 0.
	 * <br>Example: wrapValue(4,-16,16) returns 4.
	 * <br>Example: wrapValue(-1000,-16,16) returns 8.
	 * @param val the double.
	 * @param lo the lower bound.
	 * @param hi the upper bound.
	 * @return the value after being "wrapped" into the range.
	 */
	public static double wrapValue(double val, double lo, double hi)
	{
		double range = hi - lo;
		val = val - lo;
		val = (val % range);
		if (val < 0.0)
			val = val + hi;
		return val;
	}

	/**
	 * Converts degrees to radians.
	 * @param degrees the input angle in degrees.
	 * @return the resultant angle in radians.
	 */
	public static double degToRad(double degrees)
	{
		return (degrees * Math.PI)/180;
	}

	/**
	 * Returns (val - min) if val is closer to min than max, (max - val) otherwise.
	 * Result is always positive.
	 * @param val the value to test.
	 * @param min the minimum bound.
	 * @param max the maximum bound.
	 * @return the closer component value.
	 */
	public static double closerComponent(double val, double min, double max)
	{
		return Math.abs(val - min) < Math.abs(val - max) ? (val - min) : (max - val);
	}

	/**
	 * Gives a value that is the result of a linear interpolation between two values.
	 * @param factor the interpolation factor.
	 * @param x the first value.
	 * @param y the second value.
	 * @return the interpolated value.
	 */
	public static double linearInterpolate(double factor, double x, double y)
	{
		return factor * (y - x) + x;
	}

	/**
	 * Gives a value that is the result of a cosine interpolation between two values.
	 * @param factor the interpolation factor.
	 * @param x the first value.
	 * @param y the second value.
	 * @return the interpolated value.
	 */
	public static double cosineInterpolate(double factor, double x, double y)
	{
		double ft = factor * Math.PI;
		double f = (1 - Math.cos(ft)) * .5;
		return f * (y - x) + x;
	}

	/**
	 * Gives a value that is the result of a cublic interpolation between two values.
	 * Requires two outside values to predict a curve more accurately.
	 * @param factor the interpolation factor between x and y.
	 * @param w the value before the first.
	 * @param x the first value.
	 * @param y the second value.
	 * @param z the value after the second.
	 * @return the interpolated value.
	 */
	public static double cubicInterpolate(double factor, double w, double x, double y, double z)
	{
		double p = (z - y) - (w - x);
		double q = (w - x) - p;
		double r = y - w;
		double s = x;
		return (p*factor*factor*factor) + (q*factor*factor) + (r*factor) + s;
	}

	/**
	 * Takes an angle in radians and corrects it to the [0, 2PI) interval.
	 * @param angle the input angle.
	 * @return the equivalent angle in radians.
	 */
	public static double sanitizeAngleRadians(double angle)
	{
		if (angle >= TWO_PI)
			angle %= TWO_PI;
		else if (angle < 0.0)
			angle = (angle % TWO_PI) + TWO_PI;
		return angle;
	}

	/**
	 * Returns the length of a line by 
	 * the coordinates of the two points that comprise it.
	 * @param x0 the first point's x-component.
	 * @param y0 the first point's y-component.
	 * @param x1 the second point's x-component.
	 * @param y1 the second point's y-component.
	 * @return the length of the line.
	 */
	public static double getLineLength(double x0, double y0, double x1, double y1)
	{
		return Math.sqrt(getLineLengthSquared(x0, y0, x1, y1));
	}

	/**
	 * Returns the squared length of a line by 
	 * the coordinates of the two points that comprise it.
	 * @param x0 the first point's x-component.
	 * @param y0 the first point's y-component.
	 * @param x1 the second point's x-component.
	 * @param y1 the second point's y-component.
	 * @return the length of the line.
	 */
	public static double getLineLengthSquared(double x0, double y0, double x1, double y1)
	{
		return getVectorLengthSquared(x1 - x0, y1 - y0);
	}

	/**
	 * Returns the length of a vector by its components.
	 * @param x the x-component.
	 * @param y the y-component.
	 * @return the length of the vector.
	 */
	public static double getVectorLength(double x, double y)
	{
		return Math.sqrt(getVectorLengthSquared(x, y));
	}

	/**
	 * Returns the squared length of a vector by its components.
	 * @param x the x-component.
	 * @param y the y-component.
	 * @return the length of the vector.
	 */
	public static double getVectorLengthSquared(double x, double y)
	{
		return x*x + y*y;
	}

	/**
	 * Returns the length of a vector by its components.
	 * @param x the x-component.
	 * @param y the y-component.
	 * @param z the z-component.
	 * @return the length of the vector.
	 */
	public static double getVectorLength(double x, double y, double z)
	{
		return Math.sqrt(getVectorLengthSquared(x, y, z));
	}

	/**
	 * Returns the squared length of a vector by its components.
	 * @param x the x-component.
	 * @param y the y-component.
	 * @param z the z-component.
	 * @return the length of the vector.
	 */
	public static double getVectorLengthSquared(double x, double y, double z)
	{
		return x*x + y*y + z*z;
	}

	/**
	 * Returns the angular rotation of a vector described in two dimensions.
	 * Result is in radians.
	 * @param x the x-component.
	 * @param y the y-component.
	 * @return a number in the range [0, 2*PI). 0 is considered to be EAST. 
	 */
	public static double getVectorAngleRadians(double x, double y)
	{
		if (x != 0.0)
		{
			double rad = sanitizeAngleRadians(Math.atan(y / x));
			return rad + (x < 0 ? Math.PI : 0);
		}
		else
		{
			return (y < 0 ? THREE_PI_OVER_TWO : PI_OVER_TWO);
		}
	}

	/**
	 * Returns the dot product of two vectors.
	 * @param v1x the first vector's x-component.
	 * @param v1y the first vector's y-component.
	 * @param v2x the second vector's x-component.
	 * @param v2y the second vector's y-component.
	 * @return the dot product of both vectors.
	 */
	public static double getVectorDotProduct(double v1x, double v1y, double v2x, double v2y)
	{
		return v1x * v2x + v1y * v2y;
	}

	/**
	 * Returns the dot product of two vectors.
	 * @param v1x the first vector's x-component.
	 * @param v1y the first vector's y-component.
	 * @param v1z the first vector's z-component.
	 * @param v2x the second vector's x-component.
	 * @param v2y the second vector's y-component.
	 * @param v2z the second vector's z-component.
	 * @return the dot product of both vectors.
	 */
	public static double getVectorDotProduct(double v1x, double v1y, double v1z, double v2x, double v2y, double v2z)
	{
		return v1x * v2x + v1y * v2y + v1z * v2z;
	}

	/**
	 * Returns the dot product of two vectors, converted to unit vectors first.
	 * NOTE: Zero vectors will cause a <b>divide by zero</b>!
	 * @param v1x the first vector's x-component.
	 * @param v1y the first vector's y-component.
	 * @param v2x the second vector's x-component.
	 * @param v2y the second vector's y-component.
	 * @return the dot product of both vectors.
	 */
	public static double getVectorUnitDotProduct(double v1x, double v1y, double v2x, double v2y)
	{
		double v1d = getVectorLength(v1x, v1y); 
		double v2d = getVectorLength(v2x, v2y);
		v1x = v1x / v1d;
		v1y = v1y / v1d;
		v2x = v2x / v2d;
		v2y = v2y / v2d;
		return getVectorDotProduct(v1x, v1y, v2x, v2y);
	}

	/**
	 * Returns the dot product of two vectors, converted to unit vectors first.
	 * NOTE: Zero vectors will cause a <b>divide by zero</b>!
	 * @param v1x the first vector's x-component.
	 * @param v1y the first vector's y-component.
	 * @param v1z the first vector's z-component.
	 * @param v2x the second vector's x-component.
	 * @param v2y the second vector's y-component.
	 * @param v2z the second vector's z-component.
	 * @return the dot product of both vectors.
	 */
	public static double getVectorUnitDotProduct(double v1x, double v1y, double v1z, double v2x, double v2y, double v2z)
	{
		double v1d = getVectorLength(v1x, v1y, v1z); 
		double v2d = getVectorLength(v2x, v2y, v2z);
		v1x = v1x / v1d;
		v1y = v1y / v1d;
		v1z = v1z / v1d;
		v2x = v2x / v2d;
		v2y = v2y / v2d;
		v2z = v2z / v2d;
		return getVectorDotProduct(v1x, v1y, v1z, v2x, v2y, v2z);
	}

	/**
	 * Tests if an intersection occurs between two line segments.
	 * @param ax the first line segment, first point, x-coordinate.
	 * @param ay the first line segment, first point, y-coordinate.
	 * @param bx the first line segment, second point, x-coordinate.
	 * @param by the first line segment, second point, y-coordinate.
	 * @param cx the second line segment, first point, x-coordinate.
	 * @param cy the second line segment, first point, y-coordinate.
	 * @param dx the second line segment, second point, x-coordinate.
	 * @param dy the second line segment, second point, y-coordinate.
	 * @return a scalar value representing how far along the first line segment the intersection occurred, or {@link Double#NaN} if no intersection.
	 */
	public static double getIntersectionLine(double ax, double ay, double bx, double by, double cx, double cy, double dx, double dy)
	{
		double a1 = getTriangleAreaDoubleSigned(ax, ay, bx, by, dx, dy);
		double a2 = getTriangleAreaDoubleSigned(ax, ay, bx, by, cx, cy);
		
		// If the triangle areas have opposite signs. 
		if (a1 != 0.0 && a2 != 0.0 && a1 * a2 < 0.0)
		{
			double a3 = getTriangleAreaDoubleSigned(cx, cy, dx, dy, ax, ay);
			double a4 = a3 + a2 - a1;
			
			if (a3 * a4 < 0.0)
			{
				return a3 / (a3 - a4);
			}
		}
		
		return Double.NaN;
	}

	/**
	 * Checks if a point lies behind a 2D plane, represented by a line.
	 * Directionality of the line affects the plane - plane normal goes away from the "left" side of the line.
	 * @param ax the line segment, first point, x-coordinate.
	 * @param ay the line segment, first point, y-coordinate.
	 * @param bx the line segment, second point, x-coordinate.
	 * @param by the line segment, second point, y-coordinate.
	 * @param px the point, x-coordinate.
	 * @param py the point, y-coordinate.
	 * @return true if the point is past the plane, false if not.
	 * @see #getLinePointSide(double, double, double, double, double, double)
	 */
	public static boolean getIntersectionPoint2DPlane(double ax, double ay, double bx, double by, double px, double py)
	{
		return isPointBehindPlane(ax, ay, bx, by, px, py);
	}

	// Single "behind plane" consistent check.
	private static boolean isPointBehindPlane(double ax, double ay, double bx, double by, double px, double py)
	{
		return getLinePointSide(ax, ay, bx, by, px, py) < 0;
	}

	/**
	 * Returns the doubled signed area of a triangular area made up of 3 points.  
	 * @param ax the first point, x-coordinate.
	 * @param ay the first point, y-coordinate.
	 * @param bx the second point, x-coordinate.
	 * @param by the second point, y-coordinate.
	 * @param cx the third point, x-coordinate.
	 * @param cy the third point, y-coordinate.
	 * @return the calculated area.
	 */
	public static double getTriangleAreaDoubleSigned(double ax, double ay, double bx, double by, double cx, double cy)
	{
		return (ax - cx) * (by - cy) - (ay - cy) * (bx - cx);
	}

	/**
	 * Checks what "side" a point is on a line.
	 * Line orientation is "point A looking at point B"
	 * @param ax the line segment, first point, x-coordinate.
	 * @param ay the line segment, first point, y-coordinate.
	 * @param bx the line segment, second point, x-coordinate.
	 * @param by the line segment, second point, y-coordinate.
	 * @param px the point, x-coordinate.
	 * @param py the point, y-coordinate.
	 * @return a scalar value representing the line side (<code>0</code> is on the line, <code>&lt; 0</code> is left, <code>&gt; 0</code> is right).
	 */
	public static double getLinePointSide(double ax, double ay, double bx, double by, double px, double py)
	{
		double nax = (by - ay);
		double nay = -(bx - ax);
		return getVectorDotProduct(nax, nay, px - ax, py - ay);
	}

	/**
	 * Gets a scalar factor that equals how "far along" a value is along an interval.
	 * @param value the value to test.
	 * @param lo the lower value of the interval.
	 * @param hi the higher value of the interval.
	 * @return a value between 0 and 1 describing this distance 
	 * 		(0 = beginning or less, 1 = end or greater), or 0 if lo and hi are equal.
	 */
	public static double getInterpolationFactor(double value, double lo, double hi)
	{
		if (lo == hi)
			return 0.0;
		return clampValue((value - lo) / (hi - lo), 0, 1);
	}

	/**
	 * @param rand the random number generator.
	 * @return a random double value from [0 to 1) (inclusive/exclusive).
	 */
	public static double randDouble(Random rand)
	{
	    return rand.nextDouble();
	}

	/**
	 * Returns a random boolean.
	 * @param rand the random number generator.
	 * @return true or false.
	 */
	public static boolean randBoolean(Random rand)
	{
	    return rand.nextBoolean();
	}

	/**
	 * Returns a random double from lo to hi (inclusive).
	 * @param rand the random number generator.
	 * @param lo the lower bound.
	 * @param hi the upper bound.
	 * @return the next double.
	 */
	public static double randDouble(Random rand, double lo, double hi)
	{
		return (hi - lo) * randDouble(rand) + lo;
	}

	/**
	 * Returns a random double value from -1 to 1 (inclusive).
	 * @param rand the random number generator.
	 * @return the next double.
	 */
	public static double randDoubleN(Random rand)
	{
	    return randDouble(rand) * (randBoolean(rand)? -1.0 : 1.0);
	}
	
}