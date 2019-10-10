/*******************************************************************************
 * Copyright (c) 2019 Black Rook Software
 * This program and the accompanying materials are made available under 
 * the terms of the MIT License, which accompanies this distribution.
 ******************************************************************************/
package com.blackrook.math.geometry;

import com.blackrook.math.struct.Utils;

/**
 * Utility library for geometric stuff.
 * @author Matthew Tropiano
 */
public final class GeometryUtil
{
	/**
	 * Tests if two lines, formed by tuples, overlap their bounding areas.
	 * @param lineAPointA the first line's first point.
	 * @param lineAPointB the first line's second point.
	 * @param lineBPointA the second line's first point.
	 * @param lineBPointB the second line's second point.
	 * @param outVect if they overlap, this is filled with the incident vector.
	 * @return true if one line overlap the other, false otherwise.
	 */
	public static boolean lineOverlaps(
		Tuple2D lineAPointA, Tuple2D lineAPointB, Tuple2D lineBPointA, 
		Tuple2D lineBPointB, Tuple2D outVect)
	{
		double minxa = Math.min(lineAPointA.x, lineAPointB.x);
		double minya = Math.min(lineAPointA.y, lineAPointB.y);
		double maxxa = Math.max(lineAPointA.x, lineAPointB.x);
		double maxya = Math.max(lineAPointA.y, lineAPointB.y);

		double minxb = Math.min(lineBPointA.x, lineBPointB.x);
		double minyb = Math.min(lineBPointA.y, lineBPointB.y);
		double maxxb = Math.max(lineBPointA.x, lineBPointB.x);
		double maxyb = Math.max(lineBPointA.y, lineBPointB.y);

		double midxa = (minxa + maxxa) / 2.0;
		double midxb = (minxb + maxxb) / 2.0;

		double midya = (minya + maxya) / 2.0;
		double midyb = (minyb + maxyb) / 2.0;
		
		// left
		if (midxa < midxb)
		{
			if (midya < midyb) // bottom
			{
				if (maxxa >= minxb && maxya >= minyb)
				{
					if (outVect != null) outVect.set(minxb - maxxa , minyb - maxya);
					return true;
				}
				else
					return false;
			}
			else // top
			{
				if (maxxa >= minxb && minya <= maxyb)
				{
					if (outVect != null) outVect.set(minxb - maxxa, maxyb - minya);
					return true;
				}
				else
					return false;
			}
		}
		// right
		else
		{
			if (midya < midyb) // bottom
			{
				if (minxa <= maxxb && maxya >= minyb)
				{
					if (outVect != null) outVect.set(maxxb - minxa, minyb - maxya);
					return true;
				}
				else
					return false;
			}
			else // top
			{
				if (minxa <= maxxb && minya <= maxyb)
				{
					if (outVect != null) outVect.set(maxxb - minxa, maxyb - minya);
					return true;
				}
				else
					return false;
			}
		}
	}

	/**
	 * Projects a point onto a 2D vector.
	 * The input point is altered.
	 * @param point the input point to change.
	 * @param vx the vector to project onto, x-component.
	 * @param vy the vector to project onto, y-component.
	 */
	public static void getProjectedPoint(Tuple2D point, double vx, double vy)
	{
		double dotp = Utils.getVectorDotProduct(point.x, point.y, vx, vy);
		double fact = vx * vx + vy * vy;
		double dpofact = dotp / fact;
		point.x = dpofact * vx;
		point.y = dpofact * vy;
	}

	/**
	 * Projects a circle onto a 2D vector - result is a line segment.
	 * @param outLine the output line to change.
	 * @param vx the vector to project onto, x-component.
	 * @param vy the vector to project onto, y-component.
	 * @param cx the circle center point, x-coordinate.
	 * @param cy the circle center point, y-coordinate.
	 * @param crad the circle radius.
	 */
	public static void getProjectedCircle(Line2D outLine, double vx, double vy, double cx, double cy, double crad)
	{
		getProjectedCircleFirstPoint(outLine.pointA, vx, vy, cx, cy, crad);
		getProjectedCircleSecondPoint(outLine.pointB, vx, vy, cx, cy, crad);
	}

	// Projects the first circle point.
	private static void getProjectedCircleFirstPoint(Tuple2D outPoint, double vx, double vy, double cx, double cy, double crad)
	{
		boolean swap = vx < 0 ^ vy < 0;
		
		if (vx != 0.0)
		{
			double theta = Math.atan(vy / vx);
			if (theta < 0) theta = -theta;
			double dx = crad * Math.cos(theta);
			double dy = crad * Math.sin(theta);
			
			if (swap)
			{
				outPoint.x = cx - dx;
				outPoint.y = cy + dy;
			}
			else
			{
				outPoint.x = cx - dx;
				outPoint.y = cy - dy;
			}
		}
		else
		{
			outPoint.x = cx;
			outPoint.y = cy - crad;
		}
	
		getProjectedPoint(outPoint, vx, vy);
	}

	// Projects the second circle point.
	private static void getProjectedCircleSecondPoint(Tuple2D outPoint, double vx, double vy, double cx, double cy, double crad)
	{
		boolean swap = vx < 0 ^ vy < 0;
		
		if (vx != 0.0)
		{
			double theta = Math.atan(vy / vx);
			if (theta < 0) theta = -theta;
			double dx = crad * Math.cos(theta);
			double dy = crad * Math.sin(theta);
			
			if (swap)
			{
				outPoint.x = cx + dx;
				outPoint.y = cy - dy;
			}
			else
			{
				outPoint.x = cx + dx;
				outPoint.y = cy + dy;
			}
		}
		else
		{
			outPoint.x = cx;
			outPoint.y = cy + crad;
		}
	
		getProjectedPoint(outPoint, vx, vy);
	}

	/**
	 * Projects a box onto a 2D vector - result is a line segment.
	 * @param outLine the output line to change.
	 * @param vx the vector to project onto, x-component.
	 * @param vy the vector to project onto, y-component.
	 * @param cx the box center point, x-coordinate.
	 * @param cy the box center point, y-coordinate.
	 * @param hw the box center point, half-width.
	 * @param hh the box center point, half-height.
	 */
	public static void getProjectedBox(Line2D outLine, double vx, double vy, double cx, double cy, double hw, double hh)
	{
		getProjectedBoxFirstPoint(outLine.pointA, vx, vy, cx, cy, hw, hh);
		getProjectedBoxSecondPoint(outLine.pointB, vx, vy, cx, cy, hw, hh);
	}

	// Projects the first box corner.
	private static void getProjectedBoxFirstPoint(Tuple2D outPoint, double vx, double vy, double cx, double cy, double hw, double hh)
	{
		boolean swap = vx < 0 ^ vy < 0;
		
		if (vx != 0.0)
		{
			double theta = Math.atan(vy / vx);
			if (theta < 0) theta = -theta;
			double hl = (hh * Math.sin(theta)) + (hw * Math.cos(theta));
			double dx = hl * Math.cos(theta);
			double dy = hl * Math.sin(theta);
			
			if (swap)
			{
				outPoint.x = cx - dx;
				outPoint.y = cy + dy;
			}
			else
			{
				outPoint.x = cx - dx;
				outPoint.y = cy - dy;
			}
		}
		else
		{
			outPoint.x = cx;
			outPoint.y = cy - hh;
		}
	
		getProjectedPoint(outPoint, vx, vy);
	}

	// Projects the second box corner.
	private static void getProjectedBoxSecondPoint(Tuple2D outPoint, double vx, double vy, double cx, double cy, double hw, double hh)
	{
		boolean swap = vx < 0 ^ vy < 0;
		
		if (vx != 0.0)
		{
			double theta = Math.atan(vy / vx);
			if (theta < 0) theta = -theta;
			double hl = (hh * Math.sin(theta)) + (hw * Math.cos(theta));
			double dx = hl * Math.cos(theta);
			double dy = hl * Math.sin(theta);
			
			if (swap)
			{
				outPoint.x = cx + dx;
				outPoint.y = cy - dy;
			}
			else
			{
				outPoint.x = cx + dx;
				outPoint.y = cy + dy;
			}
		}
		else
		{
			outPoint.x = cx;
			outPoint.y = cy + hh;
		}
	
		getProjectedPoint(outPoint, vx, vy);
	}

	/**
	 * Gets the overlap correction for a point lying behind a 2D plane, represented by a line.
	 * An intersection is assumed to have happened, or the results are undefined.
	 * Directionality of the line affects the plane - plane normal goes away from the "right" side of the line.
	 * @param outOverlapVector the output vector for the overlap (a.k.a. incident vector).
	 * @param outIncidentPoint the output point for the incident point.
	 * @param ax the line segment, first point, x-coordinate.
	 * @param ay the line segment, first point, y-coordinate.
	 * @param bx the line segment, second point, x-coordinate.
	 * @param by the line segment, second point, y-coordinate.
	 * @param px the point, x-coordinate.
	 * @param py the point, y-coordinate.
	 */
	public static void getOverlapPoint2DPlane(Tuple2D outOverlapVector, Tuple2D outIncidentPoint, double ax, double ay, double bx, double by, double px, double py)
	{
		outIncidentPoint.set(px, py);
		outOverlapVector.set(bx - ax, by - ay);
		outIncidentPoint.projectOnto(outOverlapVector);
		outOverlapVector.rightNormal();
		outOverlapVector.setLength(Utils.getVectorLength(px - outIncidentPoint.x, py - outIncidentPoint.y));
	}

	/**
	 * Gets the overlap correction for a circle (in whole or part) lying behind a 2D plane, represented by a line.
	 * An intersection is assumed to have happened, or the results are undefined.
	 * Directionality of the line affects the plane - plane normal goes away from the "right" side of the line.
	 * @param outOverlapVector the output vector for the overlap (a.k.a. incident vector).
	 * @param outIncidentPoint the output point for the incident point.
	 * @param ax the line segment, first point, x-coordinate.
	 * @param ay the line segment, first point, y-coordinate.
	 * @param bx the line segment, second point, x-coordinate.
	 * @param by the line segment, second point, y-coordinate.
	 * @param ccx the circle centerpoint, x-coordinate.
	 * @param ccy the circle centerpoint, y-coordinate.
	 * @param crad the circle centerpoint, radius.
	 */
	public static void getOverlapCircle2DPlane(Tuple2D outOverlapVector, Tuple2D outIncidentPoint, double ax, double ay, double bx, double by, double ccx, double ccy, double crad)
	{
		// translate into vector space
		double tcx = ccx - ax;
		double tcy = ccy - ay;
		// translate line into vector space
		double tbx = bx - ax;
		double tby = by - ay;
		
		outOverlapVector.set(tbx, tby);
		outIncidentPoint.set(tcx, tcy);
		outIncidentPoint.projectOnto(outOverlapVector);
		
		outOverlapVector.rightNormal();
		outOverlapVector.setLength(crad);
		
		double cp1x = tcx + outOverlapVector.x;
		double cp1y = tcy + outOverlapVector.y;
		double cp2x = tcx - outOverlapVector.x;
		double cp2y = tcy - outOverlapVector.y;
		
		
		double len1 = 0.0;
		double len2 = 0.0;
		if (Utils.getIntersectionPoint2DPlane(0, 0, tbx, tby, cp1x, cp1y))
			len1 = Utils.getVectorLength(cp1x - outIncidentPoint.x, cp1y - outIncidentPoint.y);
		if (Utils.getIntersectionPoint2DPlane(0, 0, tbx, tby, cp2x, cp2y))
			len2 = Utils.getVectorLength(cp2x - outIncidentPoint.x, cp2y - outIncidentPoint.y);
	
		if (len1 != 0.0 || len2 != 0.0)
		{
			if (len1 > len2)
				outOverlapVector.set(cp1x - outIncidentPoint.x, cp1y - outIncidentPoint.y);
			else
				outOverlapVector.set(cp2x - outIncidentPoint.x, cp2y - outIncidentPoint.y);
		}
		
		// move back to coordinate space.
		outIncidentPoint.set(outIncidentPoint.x + ax, outIncidentPoint.y + ay);
	}

	/**
	 * Gets the overlap correction for an axis-aligned (in whole or part) lying behind a 2D plane, represented by a line.
	 * An intersection is assumed to have happened, or the results are undefined.
	 * Directionality of the line affects the plane - plane normal goes away from the "right" side of the line.
	 * @param outOverlapVector the output vector for the overlap (a.k.a. incident vector).
	 * @param outIncidentPoint the output point for the incident point.
	 * @param ax the line segment, first point, x-coordinate.
	 * @param ay the line segment, first point, y-coordinate.
	 * @param bx the line segment, second point, x-coordinate.
	 * @param by the line segment, second point, y-coordinate.
	 * @param bcx the box center, x-coordinate.
	 * @param bcy the box center, y-coordinate.
	 * @param bhw the box half width.
	 * @param bhh the box half height.
	 */
	public static void getOverlapBox2DPlane(Tuple2D outOverlapVector, Tuple2D outIncidentPoint, double ax, double ay, double bx, double by, double bcx, double bcy, double bhw, double bhh)
	{
		// translate into vector space
		double tbcx = bcx - ax;
		double tbcy = bcy - ay;
		// translate line into vector space
		double tbx = bx - ax;
		double tby = by - ay;
	
		outOverlapVector.set(bx - ax, by - ay);
	
		double len1 = 0.0;
		double len2 = 0.0;
		double len3 = 0.0;
		double len4 = 0.0;
	
		if (Utils.getIntersectionPoint2DPlane(0, 0, tbx, tby, tbcx + bhw, tbcy + bhh))
		{
			outIncidentPoint.set(tbcx + bhw, bcy + bhh);
			outIncidentPoint.projectOnto(outOverlapVector);
			len1 = Utils.getVectorLength(tbcx + bhw - outOverlapVector.x, tbcy + bhh - outOverlapVector.y);
		}
		if (Utils.getIntersectionPoint2DPlane(0, 0, tbx, tby, tbcx - bhw, tbcy + bhh))
		{
			outIncidentPoint.set(tbcx - bhw, bcy + bhh);
			outIncidentPoint.projectOnto(outOverlapVector);
			len2 = Utils.getVectorLength(tbcx - bhw - outOverlapVector.x, tbcy + bhh - outOverlapVector.y);
		}
		if (Utils.getIntersectionPoint2DPlane(0, 0, tbx, tby, tbcx + bhw, tbcy - bhh))
		{
			outIncidentPoint.set(tbcx + bhw, tbcy - bhh);
			outIncidentPoint.projectOnto(outOverlapVector);
			len3 = Utils.getVectorLength(tbcx + bhw - outOverlapVector.x, tbcy - bhh - outOverlapVector.y);
		}
		if (Utils.getIntersectionPoint2DPlane(0, 0, tbx, tby, tbcx - bhw, tbcy - bhh))
		{
			outIncidentPoint.set(tbcx - bhw, tbcy - bhh);
			outIncidentPoint.projectOnto(outOverlapVector);
			len4 = Utils.getVectorLength(tbcx - bhw - outOverlapVector.x, tbcy - bhh - outOverlapVector.y);
		}
		
		double maxLen = len1;
		int point = 0;
		if (len2 > maxLen)
		{
			point = 1;
			maxLen = len2;
		}
		if (len3 > maxLen)
		{
			point = 2;
			maxLen = len3;
		}
		if (len4 > maxLen)
		{
			point = 3;
			maxLen = len4;
		}
		
		switch (point)
		{
			case 0:
				outIncidentPoint.set(tbcx + bhw, tbcy + bhh);
				outIncidentPoint.projectOnto(outOverlapVector);
				outOverlapVector.set(tbcx + bhw - outIncidentPoint.x, tbcy + bhh - outIncidentPoint.y);
				break;
			case 1:
				outIncidentPoint.set(tbcx - bhw, tbcy + bhh);
				outIncidentPoint.projectOnto(outOverlapVector);
				outOverlapVector.set(tbcx - bhw - outIncidentPoint.x, tbcy + bhh - outIncidentPoint.y);
				break;
			case 2:
				outIncidentPoint.set(tbcx + bhw, tbcy - bhh);
				outIncidentPoint.projectOnto(outOverlapVector);
				outOverlapVector.set(tbcx + bhw - outIncidentPoint.x, tbcy - bhh - outIncidentPoint.y);
				break;
			case 3:
				outIncidentPoint.set(tbcx - bhw, tbcy - bhh);
				outIncidentPoint.projectOnto(outOverlapVector);
				outOverlapVector.set(tbcx - bhw - outIncidentPoint.x, tbcy - bhh - outIncidentPoint.y);
				break;
		}
	
		// move back to coordinate space.
		outIncidentPoint.set(outIncidentPoint.x + ax, outIncidentPoint.y + ay);
	}

	/**
	 * Calculates the intersection point as the result of a <code>MathUtils.getIntersectionLine</code> call.
	 * @param out the point to write the information to.
	 * @param ax the line segment, first point, x-coordinate.
	 * @param ay the line segment, first point, y-coordinate.
	 * @param bx the line segment, second point, x-coordinate.
	 * @param by the line segment, second point, y-coordinate.
	 * @param intersectionScalar the scalar along the line.
	 */
	public static void getOverlapPoint(Tuple2D out, double ax, double ay, double bx, double by, double intersectionScalar)
	{
		out.x = ax + intersectionScalar * (bx - ax);
		out.y = ay + intersectionScalar * (by - ay);
	}

	/**
	 * Calculates the intersection point as the result of a <code>MathUtils.getIntersectionLinePlane</code> call.
	 * @param out the point to write the information to.
	 * @param ax the line segment, first point, x-coordinate.
	 * @param ay the line segment, first point, y-coordinate.
	 * @param az the line segment, first point, z-coordinate.
	 * @param bx the line segment, second point, x-coordinate.
	 * @param by the line segment, second point, y-coordinate.
	 * @param bz the line segment, second point, z-coordinate.
	 * @param intersectionScalar the scalar along the line.
	 */
	public static void getOverlapPoint(Tuple3D out, double ax, double ay, double az, double bx, double by, double bz, double intersectionScalar)
	{
		out.x = ax + intersectionScalar * (bx - ax);
		out.y = ay + intersectionScalar * (by - ay);
		out.z = az + intersectionScalar * (bz - az);
	}

	/**
	 * Returns the overlap of a line-circle intersection.
	 * An intersection is assumed to have happened.  
	 * @param outOverlapVector the output vector for the overlap (a.k.a. incident vector).
	 * @param outIncidentPoint the output point for the incident point.
	 * @param ax the line segment, first point, x-coordinate.
	 * @param ay the line segment, first point, y-coordinate.
	 * @param bx the line segment, second point, x-coordinate.
	 * @param by the line segment, second point, y-coordinate.
	 * @param ccx the circle center, x-coordinate.
	 * @param ccy the circle center, y-coordinate.
	 * @param crad the circle radius.
	 */
	public static void getOverlapLineCircle(Tuple2D outOverlapVector, Tuple2D outIncidentPoint, double ax, double ay, double bx, double by, double ccx, double ccy, double crad)
	{
		// perpendicular vector axis to line.
		double pvx = -(by - ay);
		double pvy = bx - ax;
	
		if (ax == bx && ay == by)
		{
			outOverlapVector.set(crad, 0.0);
			outIncidentPoint.set(ax, ay);
			return;
		}
	
		if (ax == ccx && ay == ccy)
		{
			outOverlapVector.set(pvx, pvy);
			outOverlapVector.setLength(crad);
			outIncidentPoint.set(ax, ay);
			return;
		}
		
		// use incident point object for temp point.
		
		// centerpoint of projection.
		outIncidentPoint.set(ax, ay);
		getProjectedPoint(outIncidentPoint, pvx, pvy);
		double px = outIncidentPoint.x;
		double py = outIncidentPoint.y;
	
		// centerpoint of projection onto line axis.
		outIncidentPoint.set(ccx, ccy);
		getProjectedPoint(outIncidentPoint, bx - ax, by - ay);
		double cpx = outIncidentPoint.x;
		double cpy = outIncidentPoint.y;
	
		getProjectedCircleFirstPoint(outIncidentPoint, pvx, pvy, ccx, ccy, crad);
		double p1x = outIncidentPoint.x; 
		double p1y = outIncidentPoint.y; 
		getProjectedCircleSecondPoint(outIncidentPoint, pvx, pvy, ccx, ccy, crad);
		double p2x = outIncidentPoint.x; 
		double p2y = outIncidentPoint.y; 
		
		if (Utils.getVectorLengthSquared(px - p1x, py - p1y) < Utils.getVectorLengthSquared(px - p2x, py - p2y))
			outOverlapVector.set(p1x - px, p1y - py);
		else
			outOverlapVector.set(p2x - px, p2y - py);
	
		double seg = Utils.getVectorLength(cpx - ccx, cpy - ccy);
	
		double length = Math.sqrt((crad * crad) + (seg * seg));
	
		if (Utils.getVectorLength(ax - ccx, ay - ccx) < crad)
			outIncidentPoint.set(ax, ay);
		else
		{
			outIncidentPoint.set(ax - cpx, ay - cpy);
			outIncidentPoint.setLength(length);
			outIncidentPoint.set(outIncidentPoint.x + cpx, outIncidentPoint.y + cpy);
		}
		
	}

	/**
	 * Returns the overlap of a line-box intersection.
	 * An intersection is assumed to have happened.  
	 * @param outOverlapVector the output vector for the overlap (a.k.a. incident vector).
	 * @param outIncidentPoint the output point for the incident point.
	 * @param ax the line segment, first point, x-coordinate.
	 * @param ay the line segment, first point, y-coordinate.
	 * @param bx the line segment, second point, x-coordinate.
	 * @param by the line segment, second point, y-coordinate.
	 * @param bcx the box center, x-coordinate.
	 * @param bcy the box center, y-coordinate.
	 * @param bhw the box half width.
	 * @param bhh the box half height.
	 */
	public static void getOverlapLineBox(Tuple2D outOverlapVector, Tuple2D outIncidentPoint, double ax, double ay, double bx, double by, double bcx, double bcy, double bhw, double bhh)
	{
		if (ax == bx && ay == by)
		{
			outIncidentPoint.set(ax, ay);
			bx = ax + 1.0;
		}
	
		// perpendicular vector axis to line.
		double pvx = -(by - ay);
		double pvy = bx - ax;
	
		// use incident point object for temp point.
		
		// centerpoint of projection.
		outIncidentPoint.set(ax, ay);
		getProjectedPoint(outIncidentPoint, pvx, pvy);
		double cx = outIncidentPoint.x;
		double cy = outIncidentPoint.y;
	
		getProjectedBoxFirstPoint(outIncidentPoint, pvx, pvy, bcx, bcy, bhw, bhh);
		double p1x = outIncidentPoint.x; 
		double p1y = outIncidentPoint.y; 
		getProjectedBoxSecondPoint(outIncidentPoint, pvx, pvy, bcx, bcy, bhw, bhh);
		double p2x = outIncidentPoint.x; 
		double p2y = outIncidentPoint.y; 
		
		if (Utils.getVectorLengthSquared(cx - p1x, cy - p1y) < Utils.getVectorLengthSquared(cx - p2x, cy - p2y))
			outOverlapVector.set(p1x - cx, p1y - cy);
		else
			outOverlapVector.set(p1x - cx, p1y - cy);
		
		double tx0 = bcx - bhw;
		double tx1 = bcx + bhw;
		double ty0 = bcy - bhh;
		double ty1 = bcy + bhh;
		double intersection = Double.NaN;
		
		// Voronoi Region Test for line start.
		if (ax < tx0)
		{
			if (ay < ty0) // southwest
			{
				if (Double.isNaN(intersection = Utils.getIntersectionLine(ax, ay, bx, by, tx0, ty0, tx0, ty1)))
					intersection = Utils.getIntersectionLine(ax, ay, bx, by, tx0, ty0, tx1, ty0);
				if (Double.isNaN(intersection))
					intersection = 1.0;
				getOverlapPoint(outIncidentPoint, ax, ay, bx, by, intersection);
			}
			else if (ay > ty1) // northwest
			{
				if (Double.isNaN(intersection = Utils.getIntersectionLine(ax, ay, bx, by, tx0, ty0, tx0, ty1)))
					intersection = Utils.getIntersectionLine(ax, ay, bx, by, tx0, ty1, tx1, ty1);
				if (Double.isNaN(intersection))
					intersection = 1.0;
				getOverlapPoint(outIncidentPoint, ax, ay, bx, by, intersection);
			}
			else // west
			{
				intersection = Utils.getIntersectionLine(ax, ay, bx, by, tx0, ty0, tx0, ty1);
				if (Double.isNaN(intersection))
					intersection = 1.0;
				getOverlapPoint(outIncidentPoint, ax, ay, bx, by, intersection);
			}
		}
		else if (ax > tx1)
		{
			if (ay < ty0) // southeast
			{
				if (Double.isNaN(intersection = Utils.getIntersectionLine(ax, ay, bx, by, tx1, ty0, tx1, ty1)))
					intersection = Utils.getIntersectionLine(ax, ay, bx, by, tx0, ty0, tx1, ty0);
				if (Double.isNaN(intersection))
					intersection = 1.0;
				getOverlapPoint(outIncidentPoint, ax, ay, bx, by, intersection);
			}
			else if (ay > ty1) // northeast
			{
				if (Double.isNaN(intersection = Utils.getIntersectionLine(ax, ay, bx, by, tx1, ty0, tx1, ty1)))
					intersection = Utils.getIntersectionLine(ax, ay, bx, by, tx0, ty1, tx1, ty1);
				if (Double.isNaN(intersection))
					intersection = 1.0;
				getOverlapPoint(outIncidentPoint, ax, ay, bx, by, intersection);
			}
			else // east
			{
				intersection = Utils.getIntersectionLine(ax, ay, bx, by, tx1, ty0, tx1, ty1);
				if (Double.isNaN(intersection))
					intersection = 1.0;
				getOverlapPoint(outIncidentPoint, ax, ay, bx, by, intersection);
			}
		}
		else
		{
			if (ay < ty0) // south
			{
				intersection = Utils.getIntersectionLine(ax, ay, bx, by, tx0, ty0, tx1, ty0);
				if (Double.isNaN(intersection))
					intersection = 1.0;
				getOverlapPoint(outIncidentPoint, ax, ay, bx, by, intersection);
			}
			else if (ay > ty1) // north
			{
				intersection = Utils.getIntersectionLine(ax, ay, bx, by, tx0, ty1, tx1, ty1);
				if (Double.isNaN(intersection))
					intersection = 1.0;
				getOverlapPoint(outIncidentPoint, ax, ay, bx, by, intersection);
			}
			else // incident is inside box
				outIncidentPoint.set(ax, ay);
		}
		
	}

	/**
	 * Returns the overlap of two circles.
	 * The circles are assumed to be intersecting.  
	 * @param outOverlapVector the output vector for the overlap (a.k.a. incident vector).
	 * @param outIncidentPoint the output point for the incident point.
	 * @param spx the first circle center, x-coordinate.
	 * @param spy the first circle center, y-coordinate.
	 * @param srad the first circle radius.
	 * @param tpx the second circle center, x-coordinate.
	 * @param tpy the second circle center, y-coordinate.
	 * @param trad the second circle radius.
	 */
	public static void getOverlapCircle(Tuple2D outOverlapVector, Tuple2D outIncidentPoint, double spx, double spy, double srad, double tpx, double tpy, double trad)
	{
		if (spx == tpx && spy == tpy)
		{
			double rdist = srad + trad;
			outIncidentPoint.set(spx - srad, spy);
			outOverlapVector.set(rdist, 0.0);
			return;
		}
		
		double cdist = Utils.getLineLength(spx, spy, tpx, tpy);
		double rdist = srad + trad;
		outOverlapVector.set(tpx - spx, tpy - spy);
		outOverlapVector.setLength(srad);
	
		double dx = outOverlapVector.x;
		double dy = outOverlapVector.y;
		
		outOverlapVector.setLength(rdist - cdist);
		outIncidentPoint.set(spx + dx - outOverlapVector.x, spy + dy - outOverlapVector.y);
	}

	/**
	 * Returns the amount of overlap in a circle-to-box collision in a provided output vector.
	 * @param outOverlapVector the output vector for the overlap (a.k.a. incident vector).
	 * @param outIncidentPoint the output point for the incident point.
	 * @param ccx the circle center, x-coordinate.
	 * @param ccy the circle center, y-coordinate.
	 * @param crad the circle radius.
	 * @param bcx the box center, x-coordinate.
	 * @param bcy the box center, y-coordinate.
	 * @param bhw the box half width.
	 * @param bhh the box half height.
	 */
	public static void getOverlapCircleBox(Tuple2D outOverlapVector, Tuple2D outIncidentPoint, double ccx, double ccy, double crad, double bcx, double bcy, double bhw, double bhh)
	{
		double tx0 = bcx - bhw;
		double tx1 = bcx + bhw;
		double ty0 = bcy - bhh;
		double ty1 = bcy + bhh;
	
		// Voronoi Region Test.
		if (ccx < tx0)
		{
			if (ccy < ty0)
				getOverlapCalculationCircleBox(outOverlapVector, outIncidentPoint, crad, ccx, ccy, tx0, ty0);
			else if (ccy > ty1)
				getOverlapCalculationCircleBox(outOverlapVector, outIncidentPoint, crad, ccx, ccy, tx0, ty1);
			else
				getOverlapCalculationCircleBox(outOverlapVector, outIncidentPoint, crad, ccx, ccy, tx0, ccy);
		}
		else if (ccx > tx1)
		{
			if (ccy < ty0)
				getOverlapCalculationCircleBox(outOverlapVector, outIncidentPoint, crad, ccx, ccy, tx1, ty0);
			else if (ccy > ty1)
				getOverlapCalculationCircleBox(outOverlapVector, outIncidentPoint, crad, ccx, ccy, tx1, ty1);
			else
				getOverlapCalculationCircleBox(outOverlapVector, outIncidentPoint, crad, ccx, ccy, tx1, ccy);
		}
		else
		{
			if (ccy < ty0)
				getOverlapCalculationCircleBox(outOverlapVector, outIncidentPoint, crad, ccx, ccy, ccx, ty0);
			else if (ccy > ty1)
				getOverlapCalculationCircleBox(outOverlapVector, outIncidentPoint, crad, ccx, ccy, ccx, ty1);
			else // circle center is inside box
			{
				double closeX = Utils.closerComponent(ccx, tx0, tx1);
				double closeY = Utils.closerComponent(ccy, ty0, ty1);
				
				if (closeX < closeY)
				{
					double tpx = bcx;
					if (ccx < tpx)
					{
						outOverlapVector.set(closeX + crad, 0);
						outIncidentPoint.set(tx0, ccy);
					}
					else
					{
						outOverlapVector.set(-closeX - crad, 0);
						outIncidentPoint.set(tx1, ccy);
					}
				}
				else
				{
					double tpy = bcy;
					if (ccy < tpy)
					{
						outOverlapVector.set(0, closeY + crad);
						outIncidentPoint.set(ccx, ty0);
					}
					else
					{
						outOverlapVector.set(0, -closeY - crad);
						outIncidentPoint.set(ccx, ty1);
					}
				}
			}
		}
	}

	/**
	 * Returns the amount of overlap in a box-to-circle collision in a provided output vector.
	 * @param outOverlapVector the output vector for the overlap (a.k.a. incident vector).
	 * @param outIncidentPoint the output point for the incident point.
	 * @param bcx the box center, x-coordinate.
	 * @param bcy the box center, y-coordinate.
	 * @param bhw the box half width.
	 * @param bhh the box half height.
	 * @param ccx the circle center, x-coordinate.
	 * @param ccy the circle center, y-coordinate.
	 * @param crad the circle radius.
	 */
	public static void getOverlapBoxCircle(Tuple2D outOverlapVector, Tuple2D outIncidentPoint, double bcx, double bcy, double bhw, double bhh, double ccx, double ccy, double crad)
	{
		double bx0 = bcx - bhw;
		double bx1 = bcx + bhw;
		double by0 = bcy - bhh;
		double by1 = bcy + bhh;
	
		// Voronoi Test
		
		// Complete Left
		if (bx1 < ccx)
		{
			// Complete Bottom
			if (by1 < ccy)
				getOverlapCalculationBoxCircle(outOverlapVector, outIncidentPoint, crad, bx1, by1, ccx, ccy);
			// Complete Top
			else if (by0 > ccy)
				getOverlapCalculationBoxCircle(outOverlapVector, outIncidentPoint, crad, bx1, by0, ccx, ccy);
			// Straddle Y
			else
				getOverlapCalculationBoxCircle(outOverlapVector, outIncidentPoint, crad, bx1, ccy, ccx, ccy);
		}
		// Complete Right
		else if (bx0 > ccx)
		{
			// Complete Bottom
			if (by1 < ccy)
				getOverlapCalculationBoxCircle(outOverlapVector, outIncidentPoint, crad, bx0, by1, ccx, ccy);
			// Complete Top
			else if (by0 > ccy)
				getOverlapCalculationBoxCircle(outOverlapVector, outIncidentPoint, crad, bx0, by0, ccx, ccy);
			// Straddle Y
			else
				getOverlapCalculationBoxCircle(outOverlapVector, outIncidentPoint, crad, bx0, ccy, ccx, ccy);
		}
		// Straddle X
		else
		{
			// Complete Bottom
			if (by1 < ccy)
				getOverlapCalculationBoxCircle(outOverlapVector, outIncidentPoint, crad, ccx, by1, ccx, ccy);
			// Complete Top
			else if (by0 > ccy)
				getOverlapCalculationBoxCircle(outOverlapVector, outIncidentPoint, crad, ccx, by0, ccx, ccy);
			// Straddle Y
			else
			{
				double closeX = Utils.closerComponent(ccx, bx0, bx1);
				double closeY = Utils.closerComponent(ccy, by0, by1);
	
				if (closeX < closeY)
				{
					if (ccx < bcx)
					{
						outOverlapVector.set(-closeX - crad, 0);
						outIncidentPoint.set(ccx + crad, ccy);
					}
					else
					{
						outOverlapVector.set(closeX + crad, 0);
						outIncidentPoint.set(ccx - crad, ccy);
					}
				}
				else
				{
					if (ccy < bcy)
					{
						outOverlapVector.set(0, -closeY - crad);
						outIncidentPoint.set(ccx, ccy + crad);
					}
					else
					{
						outOverlapVector.set(0, closeY + crad);
						outIncidentPoint.set(ccx, ccy - crad);
					}
				}
			}
		}
	}

	/**
	 * Sets incident vectors and points if a collision occurs between
	 * AABBs and Circles.
	 */
	private static void getOverlapCalculationBoxCircle(Tuple2D outOverlapVector, Tuple2D outIncidentPoint, double radius, double bx, double by, double cx, double cy)
	{
		double dist = Utils.getLineLength(cx, cy, bx, by);
		double theta = Utils.getVectorAngleRadians(bx - cx, by - cy);
		outOverlapVector.set(cx - bx, cy - by);
		outOverlapVector.setLength(radius - dist);
		outIncidentPoint.set(radius * Math.cos(theta) + cx, radius * Math.sin(theta) + cy);
	}

	/**
	 * Sets incident vectors and points if a collision occurs between Circles and Boxes.
	 */
	private static void getOverlapCalculationCircleBox(Tuple2D outOverlapVector, Tuple2D outIncidentPoint, double radius, double spx, double spy, double ax, double ay)
	{
		outOverlapVector.set(ax - spx, ay - spy);
		outOverlapVector.setLength(radius - Utils.getLineLength(spx, spy, ax, ay));
		outIncidentPoint.set(ax, ay);
	}

	/**
	 * Returns the amount of overlap in a box-to-box collision.
	 * @param outOverlapVector the output vector for the overlap (a.k.a. incident vector).
	 * @param outIncidentPoint the output point for the incident point.
	 * @param spx the first box center, x-coordinate.
	 * @param spy the first box center, y-coordinate.
	 * @param shw the first box half width.
	 * @param shh the first box half height.
	 * @param tpx the second box center, x-coordinate.
	 * @param tpy the second box center, y-coordinate.
	 * @param thw the second box half width.
	 * @param thh the second box half height.
	 */
	public static void getOverlapBox(Tuple2D outOverlapVector, Tuple2D outIncidentPoint, double spx, double spy, double shw, double shh, double tpx, double tpy, double thw, double thh)
	{
		if (spx < tpx) // box to the left.
		{
			if (spy < tpy) // box to the bottom.
			{
				double dx = Math.abs((spx + shw) - (tpx - thw));
				double dy = Math.abs((spy + shh) - (tpy - thh));
				
				if (dx < dy)
				{
					outOverlapVector.set(dx, 0);
					double d0 = Math.max(tpy - thh, spy - shh); 
					double d1 = Math.min(tpy + thh, spy + shh); 
					outIncidentPoint.set(spx + shw - dx, (d0 + d1) / 2.0);
				}
				else
				{
					outOverlapVector.set(0, dy);
					double d0 = Math.max(tpx - thw, spx - shw); 
					double d1 = Math.min(tpx + thw, spx + shw); 
					outIncidentPoint.set((d0 + d1) / 2.0, spy + shh - dy);
				}
			}
			else // box to the top.
			{
				double dx = Math.abs((spx + shw) - (tpx - thw));
				double dy = Math.abs((tpy + thh) - (spy - shh));
				
				if (dx < dy)
				{
					outOverlapVector.set(dx, 0);
					double d0 = Math.max(tpy - thh, spy - shh); 
					double d1 = Math.min(tpy + thh, spy + shh); 
					outIncidentPoint.set(spx + shw - dx, (d0 + d1) / 2.0);
				}
				else
				{
					outOverlapVector.set(0, -dy);
					double d0 = Math.max(tpx - thw, spx - shw); 
					double d1 = Math.min(tpx + thw, spx + shw); 
					outIncidentPoint.set((d0 + d1) / 2.0, spy - shh + dy);
				}
			}
		}
		else // box to the right
		{
			if (spy < tpy) // box to the bottom.
			{
				double dx = Math.abs((tpx + thw) - (spx - shw));
				double dy = Math.abs((spy + shh) - (tpy - thh));
				
				if (dx < dy)
				{
					outOverlapVector.set(-dx, 0);
					double d0 = Math.max(tpy - thh, spy - shh); 
					double d1 = Math.min(tpy + thh, spy + shh); 
					outIncidentPoint.set(spx - shw + dx, (d0 + d1) / 2.0);
				}
				else
				{
					outOverlapVector.set(0, dy);
					double d0 = Math.max(tpx - thw, spx - shw); 
					double d1 = Math.min(tpx + thw, spx + shw); 
					outIncidentPoint.set((d0 + d1) / 2.0, spy + shh - dy);
				}
			}
			else // box to the top.
			{
				double dx = Math.abs((tpx + thw) - (spx - shw));
				double dy = Math.abs((tpy + thh) - (spy - shh));
				
				if (dx < dy)
				{
					outOverlapVector.set(-dx, 0);
					double d0 = Math.max(tpy - thh, spy - shh); 
					double d1 = Math.min(tpy + thh, spy + shh); 
					outIncidentPoint.set(spx - shw + dx, (d0 + d1) / 2.0);
				}
				else
				{
					outOverlapVector.set(0, -dy);
					double d0 = Math.max(tpx - thw, spx - shw); 
					double d1 = Math.min(tpx + thw, spx + shw); 
					outIncidentPoint.set((d0 + d1) / 2.0, spy - shh + dy);
				}
			}
		}
	}
	
}
