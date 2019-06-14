/*******************************************************************************
 * Copyright (c) 2019 Black Rook Software
 * This program and the accompanying materials are made available under 
 * the terms of the MIT License, which accompanies this distribution.
 ******************************************************************************/
package com.blackrook.math;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

/**
 * An object for holding a set of ordered pairs.
 * @author Matthew Tropiano
 */
public class PairGroup implements Iterable<PairGroup.Pair>
{
	private static final Comparator<Pair> PAIR_COMPARATOR = (p1, p2) -> {
		return p1.x == p2.x ? (p1.y - p2.y) : (p1.x - p2.x);
	};
	
	/** A list of the contained pairs. */
	private Pair[] pairList;
	/** Current size. */
	private int size;
	
	/**
	 * Created a Pair group with no Pairs added to it.
	 * @param capacity the group capacity.
	 */
	private PairGroup(int capacity)
	{
		setCapacity(capacity);
		this.size = 0;
	}
	
	// Sets the internal capacity.
	private void setCapacity(int capacity)
	{
		int oldCapacity = this.pairList != null ? this.pairList.length : 0;
		Pair[] oldArray = this.pairList;
				
		if (oldCapacity == capacity)
			return;
		
		Pair[] newArray = new Pair[capacity];
		if (oldArray != null)
			System.arraycopy(oldArray, 0, newArray, 0, Math.min(oldCapacity, capacity));
		
		for (int i = oldCapacity; i < capacity; i++)
			newArray[i] = new Pair();

		if (capacity < oldCapacity)
			size = capacity;
		
		this.pairList = newArray;
	}
	
	// Set circle points.
	private void setCirclePoints(int cx, int cy, int x, int y)
	{
		if (x == 0)
		{
			add(cx, cy + y);
			add(cx, cy - y);
			add(cx + y, cy);
			add(cx - y, cy);
		}
		else if (x == y)
		{
			add(cx + x, cy + y);
			add(cx - x, cy + y);
			add(cx + x, cy - y);
			add(cx - x, cy - y);
		}
		else if (x < y) 
		{
			add(cx + x, cy + y);
			add(cx - x, cy + y);
			add(cx + x, cy - y);
			add(cx - x, cy - y);
			add(cx + y, cy + x);
			add(cx - y, cy + x);
			add(cx + y, cy - x);
			add(cx - y, cy - x);
		}
	}

	// Set filled circle points.
	private void connectCirclePoints(int cx, int cy, int x, int y)
	{
		if (x == 0)
		{
			addLine(cx, cy + y, cx, cy - y);
			addLine(cx + y, cy, cx - y, cy);
		}
		else if (x == y)
		{
			addLine(cx + x, cy + y, cx - x, cy + y);
			addLine(cx + x, cy - y, cx - x, cy - y);
		}
		else if (x < y) 
		{
			addLine(cx + x, cy + y, cx - x, cy + y);
			addLine(cx + x, cy - y, cx - x, cy - y);
			addLine(cx + y, cy + x, cx - y, cy + x);
			addLine(cx + y, cy - x, cx - y, cy - x);
		}
	}

	private static <T> int sortFrom(T[] array, int index, Comparator<? super T> comparator)
	{
		while (index > 0 && comparator.compare(array[index], array[index - 1]) < 0)
		{
			arraySwap(array, index, index - 1);
			index--;
		}
		return index;
	}

	private static <T> void arraySwap(T[] array, int a, int b)
	{
		T temp = array[a];
		array[a] = array[b];
		array[b] = temp;
	}

	/**
	 * Creates a new, empty PairGroup.
	 * @return the new PairGroup.
	 */
	public static PairGroup empty()
	{
		return new PairGroup(32);
	}
	
	/**
	 * Creates a new, empty PairGroup with an initial, specific capacity.
	 * @param capacity the group capacity.
	 * @return the new PairGroup.
	 */
	public static PairGroup empty(int capacity)
	{
		return new PairGroup(capacity);
	}
	
	/**
	 * Creates a new group by adding a set of existing pairs.
	 * @param pairs the list of Pairs.
	 * @return the new PairGroup.
	 */
	public static PairGroup wrap(Pair ... pairs)
	{
		PairGroup out = empty(pairs.length);
		for (Pair p : pairs)
			out.add(p.x, p.y);
		return out;
	}
	
	/**
	 * Creates a new group by adding a set of existing pairs.
	 * @param pairs the iterable list of Pairs.
	 * @return the new PairGroup.
	 */
	public static PairGroup wrap(Iterable<Pair> pairs)
	{
		PairGroup out = empty();
		for (Pair p : pairs)
			out.add(p.x, p.y);
		return out;
	}
	
	/**
	 * Creates a new group that contains a single pair.
	 * @param x the point, x-coordinate.
	 * @param y the point, y-coordinate.
	 * @return the new PairGroup.
	 */
	public static PairGroup point(int x, int y)
	{
		PairGroup out = empty(1);
		out.add(x, y);
		return out;
	}
	
	/**
	 * Creates a new group that contains points in a line.
	 * Uses Bresenham's Line Algorithm.
	 * @param x0 the line start point, x-coordinate.
	 * @param y0 the line start point, y-coordinate.
	 * @param x1 the line end point, x-coordinate.
	 * @param y1 the line end point, y-coordinate.
	 * @return the new PairGroup.
	 */
	public static PairGroup line(int x0, int y0, int x1, int y1)
	{
		return line(x0, y0, x1, y1, false);
	}
	
	/**
	 * Creates a new group that contains points in a line.
	 * Uses Bresenham's Line Algorithm.
	 * @param x0 the line start point, x-coordinate.
	 * @param y0 the line start point, y-coordinate.
	 * @param x1 the line end point, x-coordinate.
	 * @param y1 the line end point, y-coordinate.
	 * @param solid if true, this will add Pairs that "complete" the line, and leaves no diagonal gaps. 
	 * @return the new PairGroup.
	 */
	public static PairGroup line(int x0, int y0, int x1, int y1, boolean solid)
	{
		int capacity = Math.max(Math.max(x0, x1) - Math.min(x0, x1) + 1, Math.max(y0, y1) - Math.min(y0, y1) + 1);
		PairGroup out = new PairGroup(capacity);
		out.addLine(x0, y0, x1, y1, solid);
		return out;
	}

	/**
	 * Creates a new group that contains pairs that make up a circle.
	 * Uses Bresenham's Circle Algorithm.
	 * @param cx the center of the circle, x-coordinate.
	 * @param cy the center of the circle, y-coordinate.
	 * @param radius the circle radius.
	 * @return the new PairGroup.
	 */
	public static PairGroup circle(int cx, int cy, int radius)
	{
		radius = radius < 0 ? -radius : radius;
		PairGroup out = new PairGroup(radius * 8);
		out.addCircle(cx, cy, radius);
		return out;
	}
	
	/**
	 * Creates a new group that contains pairs that make up a filled circle.
	 * Uses Bresenham's Circle Algorithm.
	 * @param cx the center of the circle, x-coordinate.
	 * @param cy the center of the circle, y-coordinate.
	 * @param radius the circle radius.
	 * @return the new PairGroup.
	 */
	public static PairGroup circleFilled(int cx, int cy, int radius)
	{
		radius = radius < 0 ? -radius : radius;
		PairGroup out = new PairGroup(radius * radius * 2);
		out.addCircleFilled(cx, cy, radius);
		return out;
	}

	/**
	 * Creates a new group that contains pairs that make up a box.
	 * @param x0 origin x-coordinate.
	 * @param y0 origin y-coordinate.
	 * @param x1 end x-coordinate.
	 * @param y1 end y-coordinate.
	 * @return the new PairGroup.
	 */
	public static PairGroup box(int x0, int y0, int x1, int y1)
	{
		int dx = Math.max(x0, x1) - Math.min(x0, x1) + 1;
		int dy = Math.max(y0, y1) - Math.min(y0, y1) + 1;
		
		int ix = Math.max(dx - 2, 0);
		int iy = Math.max(dy - 2, 0);

		PairGroup out = empty(dx * dy - ix * iy);
		out.addBox(x0, y0, x1, y1);
		return out;
	}
	
	/**
	 * Creates a new group that contains pairs that make up a filled box.
	 * @param x0 origin x-coordinate.
	 * @param y0 origin y-coordinate.
	 * @param x1 end x-coordinate.
	 * @param y1 end y-coordinate.
	 * @return the new PairGroup.
	 */
	public static PairGroup boxFilled(int x0, int y0, int x1, int y1)
	{
		int dx = Math.max(x0, x1) - Math.min(x0, x1) + 1;
		int dy = Math.max(y0, y1) - Math.min(y0, y1) + 1;
		PairGroup out = empty(dx * dy);
		out.addBoxFilled(x0, y0, x1, y1);
		return out;
	}
	
	/**
	 * Clears this group.
	 * @return itself, to chain method calls.
	 */
	public PairGroup clear()
	{
		size = 0;
		return this;
	}
	
	/**
	 * Adds a pair.
	 * @param x x-coordinate.
	 * @param y y-coordinate.
	 * @return itself, to chain method calls.
	 */
	public PairGroup add(int x, int y)
	{
		if (!contains(x, y))
		{
			if (size == pairList.length)
				setCapacity(pairList.length * 2);
			
			pairList[size].x = x; 
			pairList[size].y = y;
			sortFrom(pairList, size, PAIR_COMPARATOR);
			size++;
		}
		
		return this;
	}

	/**
	 * Adds a group of pairs to this group.
	 * Compare with {@link #union(PairGroup)}, which returns a NEW PairGroup.
	 * @param group the group to add.
	 * @return itself, to chain method calls.
	 */
	public PairGroup add(PairGroup group)
	{
		for (Pair p : group)
			add(p.x, p.y);
		return this;
	}

	/**
	 * Adds a set of pairs that make up a line.
	 * Uses Bresenham's Line Algorithm.
	 * <p>Convenience method for: <code>addLine(x0, y0, x1, y1, false)</code>
	 * @param x0 the line start point, x-coordinate.
	 * @param y0 the line start point, y-coordinate.
	 * @param x1 the line end point, x-coordinate.
	 * @param y1 the line end point, y-coordinate.
	 * @return itself, to chain method calls.
	 */
	public PairGroup addLine(int x0, int y0, int x1, int y1)
	{
		return addLine(x0, y0, x1, y1, false);
	}

	/**
	 * Adds a set of pairs that make up a line.
	 * Uses Bresenham's Line Algorithm.
	 * @param x0 the line start point, x-coordinate.
	 * @param y0 the line start point, y-coordinate.
	 * @param x1 the line end point, x-coordinate.
	 * @param y1 the line end point, y-coordinate.
	 * @param solid if true, this will add Pairs that "complete" the line,
	 * and leaves no diagonal gaps. 
	 * @return itself, to chain method calls.
	 */
	public PairGroup addLine(int x0, int y0, int x1, int y1, boolean solid)
	{
		int dy = y1 - y0;
		int dx = x1 - x0;
		int stepx, stepy;
		
		if (dy < 0) { dy = -dy;  stepy = -1; } else { stepy = 1; }
		if (dx < 0) { dx = -dx;  stepx = -1; } else { stepx = 1; }
		dy <<= 1;
		dx <<= 1;
		
		add(x0, y0);
		if (dx > dy)
		{
			int fraction = dy - (dx >> 1);
			while (x0 != x1)
			{
				if (fraction >= 0)
				{
					y0 += stepy;
					fraction -= dx;
					if (solid)
						add(x0, y0);
				}
				x0 += stepx;
				fraction += dy;
				add(x0, y0);
			}
		} 
		else 
		{
			int fraction = dx - (dy >> 1);
			while (y0 != y1) 
			{
				if (fraction >= 0)
				{
					x0 += stepx;
					fraction -= dy;
					if (solid)
						add(x0, y0);
				}
				y0 += stepy;
				fraction += dx;
				add(x0, y0);
			}
		}
		
		return this;
	}

	/**
	 * Adds a set of pairs that make up a circle.
	 * Uses Bresenham's Circle Algorithm.
	 * @param cx the center of the circle, x-coordinate.
	 * @param cy the center of the circle, y-coordinate.
	 * @param radius the circle radius.
	 * @return itself, to chain method calls.
	 */
	public PairGroup addCircle(int cx, int cy, int radius)
	{
		int x = 0;
		int y = radius;
		int p = (5 - radius*4)/4;
		
		setCirclePoints(cx, cy, x, y);
		
		while (x < y)
		{
			x++;
			if (p < 0)
				p += 2*x+1;
			else 
			{
				y--;
				p += 2*(x-y)+1;
			}
			setCirclePoints(cx, cy, x, y);
		}
		
		return this;
	}

	/**
	 * Adds a set of pairs that make up a filled circle.
	 * Uses Bresenham's Circle Algorithm.
	 * @param cx the center of the circle, x-coordinate.
	 * @param cy the center of the circle, y-coordinate.
	 * @param radius the circle radius.
	 * @return itself, to chain method calls.
	 */
	public PairGroup addCircleFilled(int cx, int cy, int radius)
	{
		int x = 0;
		int y = radius;
		int p = (5 - radius*4)/4;
		
		connectCirclePoints(cx, cy, x, y);
		
		while (x < y)
		{
			x++;
			if (p < 0)
				p += 2*x+1;
			else 
			{
				y--;
				p += 2*(x-y)+1;
			}
			connectCirclePoints(cx, cy, x, y);
		}
		
		return this;
	}

	/**
	 * Adds a set of pairs that make up a box.
	 * @param x0 origin x-coordinate.
	 * @param y0 origin y-coordinate.
	 * @param x1 end x-coordinate.
	 * @param y1 end y-coordinate.
	 * @return itself, to chain method calls.
	 */
	public PairGroup addBox(int x0, int y0, int x1, int y1)
	{
		addLine(x0, y0, x1, y0);
		addLine(x1, y0, x1, y1);
		addLine(x1, y1, x0, y1);
		addLine(x0, y1, x0, y0);
		return this;
	}
	
	/**
	 * Adds a set of pairs that make up a filled box.
	 * @param x0 origin x-coordinate.
	 * @param y0 origin y-coordinate.
	 * @param x1 end x-coordinate.
	 * @param y1 end y-coordinate.
	 * @return itself, to chain method calls.
	 */
	public PairGroup addBoxFilled(int x0, int y0, int x1, int y1)
	{
		int minx = Math.min(x0, x1);
		int maxx = Math.max(x0, x1);
		int miny = Math.min(y0, y1);
		int maxy = Math.max(y0, y1);

		for (int j = miny; j <= maxy; j++)
			for (int i = minx; i <= maxx; i++)
				add(i, j);
		return this;
	}
	
	/**
	 * Removes a pair. 
	 * If it doesn't exist in the group, no removal happens.
	 * @param x x-coordinate.
	 * @param y y-coordinate.
	 * @return itself, to chain method calls.
	 */
	public PairGroup remove(int x, int y)
	{
		Cache c = Cache.LOCAL.get();
		c.tempPair.set(x, y);
		int index = Arrays.binarySearch(pairList, c.tempPair, PAIR_COMPARATOR);
		if (index < 0)
			return this;
		
		if (index == size - 1)
		{
			size--;
			return this;
		}
		
		Pair rem = pairList[index];
		while (index < size - 1)
		{
			pairList[index] = pairList[index + 1];
			index++;
		}
		pairList[index] = rem;
		
		size--;
		
		return this;
	}
	
	/**
	 * Removes all points in this group that are specified in another group.
	 * Compare to {@link #difference(PairGroup)}, which returns a NEW PairGroup.
	 * @param group the other group.
	 * @return itself, to chain method calls.
	 */
	public PairGroup remove(PairGroup group)
	{
		for (Pair p : group)
			remove(p.x, p.y);
		return this;
	}
	
	/**
	 * Removes all points in this group that are NOT specified in another group.
	 * Compare to {@link #intersection(PairGroup)}, which returns a NEW PairGroup.
	 * @param group the other group.
	 * @return itself, to chain method calls.
	 */
	public PairGroup removeNotIn(PairGroup group)
	{
		for (Pair p : group)
			if (!contains(p.x, p.y))
				remove(p.x, p.y);
		return this;
	}
	
	/**
	 * Gets the contents of a pair is in this group.
	 * @param index the index of the pair.
	 * @param out the output pair.
	 * @return true if a pair was returned, false if the index was out of range.
	 */
	public boolean get(int index, Pair out)
	{
		if (index < 0 || index >= size())
			return false;
		
		out.set(pairList[index]);
		return true;
	}

	/**
	 * Checks if a pair is in this group.
	 * @param x x-coordinate.
	 * @param y y-coordinate.
	 * @return true if so, false if not.
	 */
	public boolean contains(int x, int y)
	{
		Cache c = Cache.LOCAL.get();
		c.tempPair.set(x, y);
		return Arrays.binarySearch(pairList, 0, size, c.tempPair, PAIR_COMPARATOR) >= 0;
	}

	/**
	 * Translate all of the Pairs in this group by
	 * some amount on each axis.
	 * @param x x-coordinate amount.
	 * @param y y-coordinate amount.
	 * @return itself, to chain method calls.
	 */
	public PairGroup translate(int x, int y)
	{
		for (int i = 0; i < size; i++)
		{
			pairList[i].x += x;
			pairList[i].y += y;
		}
		return this;
	}

	/**
	 * Creates a new PairGroup that is a copy of this PairGroup. 
	 * @return the new PairGroup, which contains the source group.
	 */
	public PairGroup copy()
	{
		PairGroup out = new PairGroup(pairList.length);
		for (Pair p : this)
			out.add(p.x, p.y);
		return out;
	}
	
	/**
	 * Creates a new PairGroup that is the union of
	 * two groups. The new group will have the Pairs that
	 * are in this group plus the provided group.
	 * @param group the provided group.
	 * @return a new PairGroup.
	 */
	public PairGroup union(PairGroup group)
	{
		PairGroup out = copy();
		out.add(group);
		return out;
	}

	/**
	 * Creates a new PairGroup that is the intersection of
	 * two groups. The new group will only have the Pairs that
	 * are both in this group and the provided group.
	 * @param group the provided group.
	 * @return a new PairGroup.
	 */
	public PairGroup intersection(PairGroup group)
	{
		// Intersections will never be larger than the smallest set - compare with smallest.
		PairGroup smaller = this.size < group.size ? this : group;
		PairGroup larger = smaller == this ? group : this;
		
		PairGroup out = new PairGroup(smaller.size);
		for (int i = 0; i < smaller.size; i++)
		{
			Pair p = pairList[i];
			if (larger.contains(p.x, p.y))
				out.add(p.x, p.y);
		}
		return out;
	}

	/**
	 * Creates a new PairGroup that is a copy of this group
	 * minus the pairs in the provided group.
	 * @param group the provided group.
	 * @return a new PairGroup.
	 */
	public PairGroup difference(PairGroup group)
	{
		PairGroup out = copy();
		out.remove(group);
		return out;
	}

	/**
	 * Creates a new PairGroup that is a union of this group
	 * and the provided group, minus the intersection.
	 * @param group the provided group.
	 * @return a new PairGroup.
	 */
	public PairGroup xor(PairGroup group)
	{
		PairGroup out = copy();
		for (Pair p : group)
			if (out.contains(p.x, p.y))
				out.remove(p.x, p.y);
			else
				out.add(p.x, p.y);
		return out;
	}
	
	/**
	 * Creates a new PairGroup that is a randomly-selected
	 * collection of the pairs inside this group.
	 * @param random the random number generator to use.
	 * @return a new PairGroup.
	 */
	public PairGroup random(Random random)
	{
		return random(random, 0.5f);
	}

	/**
	 * Creates a new PairGroup that is a randomly-selected
	 * collection of the pairs inside this group, using a factor
	 * that decides the chance that each pair gets picked.
	 * @param random the random number generator to use.
	 * @param chance the chance, from 0 to 1, that a pair is selected.
	 * 0 or less is NEVER, 1 or greater is ALWAYS. In the event of these
	 * values being passed in, no numbers are randomly generated.
	 * @return a new PairGroup.
	 */
	public PairGroup random(Random random, float chance)
	{
		if (chance <= 0f)
			return empty();
		else if (chance >= 1f)
			return copy();

		PairGroup out = empty(pairList.length);
		for (int i = 0; i < size; i++)
			if (random.nextFloat() < chance)
				out.add(pairList[i].x, pairList[i].y);
		
		return out;
	}

	/**
	 * Creates a new PairGroup that is a randomly-selected
	 * collection of the pairs inside this group, using a scalar describing how many total
	 * get picked as a percentage of the whole group (0 is 0%, 1 is 100%).
	 * @param random the random number generator to use.
	 * @param density the density, from 0 to 1, of the amount of pairs to select.
	 * 0 or less is NONE, 1 or greater is ALL. In the event of these
	 * specific values being passed in, no numbers are randomly generated.
	 * @return a new PairGroup.
	 */
	public PairGroup randomDensity(Random random, float density)
	{
		return randomAmount(random, (int)(size() * density));
	}

	/**
	 * Creates a new PairGroup that is a randomly-selected
	 * collection of a number of pairs inside this group.
	 * @param random the random number generator to use.
	 * @param count the amount of objects to return. If this is 0 or less,
	 * this returns a new empty set. If this is greater than or equal to {@link #size()}, this returns {@link #copy()}.
	 * In the event of these specific values being passed in, no numbers are randomly generated.
	 * @return a new PairGroup.
	 */
	public PairGroup randomAmount(Random random, int count)
	{
		if (count <= 0)
			return empty();
		if (count >= size)
			return copy();
		
		Cache c = Cache.LOCAL.get();
		int amount = Math.min(size(), count);
		c.doRandom(random, size(), amount);
		
		PairGroup out = empty(count);
		for (int i = 0; i < amount; i++)
		{
			Pair p = pairList[c.randomOrdering[i]];
			out.add(p.x, p.y);
		}
		
		return out;
	}
	
	@Override
	public PairGroupIterator iterator()
	{
		return new PairGroupIterator(this);
	}
	
	/**
	 * @return the amount of Pairs that this group contains.
	 */
	public int size()
	{
		return size;
	}

	/**
	 * @return true if this PairGroup has no pairs.
	 */
	public boolean isEmpty()
	{
		return size() == 0;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		int i = 0;
		while (i < size)
		{
			sb.append(pairList[i]);
			if (i < size - 1)
				sb.append(", ");
			i++;
		}
		sb.append(']');
		return sb.toString();
	}
	
	/**
	 * Ordered Pair integer object. 
	 */
	public static class Pair
	{
		/** X-coordinate. */
		public int x;
		/** Y-coordinate. */
		public int y;
		
		/**
		 * Creates a new Pair (0,0).
		 */
		public Pair()
		{
			this(0, 0);
		}
		
		/**
		 * Creates a new Pair.
		 * @param x the x-coordinate value.
		 * @param y the y-coordinate value.
		 */
		public Pair(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		
		@Override
		public int hashCode()
		{
			return x ^ ~y;
		}
		
		@Override
		public boolean equals(Object obj)
		{
			if (obj instanceof Pair)
				return equals((Pair)obj);
			else
				return super.equals(obj);
		}
		
		/**
		 * Sets both components.
		 * @param x the x-coordinate value.
		 * @param y the y-coordinate value.
		 */
		public void set(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		
		/**
		 * Sets both components using an existing pair.
		 * @param p the source pair.
		 */
		public void set(Pair p)
		{
			this.x = p.x;
			this.y = p.y;
		}
		
		/**
		 * Checks if this pair equals another.
		 * @param p the other pair.
		 * @return true if so, false if not.
		 */
		public boolean equals(Pair p)
		{
			return x == p.x && y == p.y;
		}

		@Override
		public String toString()
		{
			return "(" + x + ", " + y + ")";
		}
		
	}

	/**
	 * Iterator for this group.
	 * Changing the Pairs returned by this iterator will not change the contents of the group.
	 */
	public static class PairGroupIterator implements Iterator<Pair>
	{
		/** Pair group. */
		private PairGroup group;
		/** Surrogate Pair to return. */
		private Pair surrogateCurrent;
		/** Current pair. */
		private int current;
		/** Removed? */
		private boolean removed;

		public PairGroupIterator(PairGroup group)
		{
			this.surrogateCurrent = new Pair();
			this.group = group;
			this.removed = false;
			this.current = 0;
		}
		
		@Override
		public boolean hasNext()
		{
			return current < group.size;
		}

		@Override
		public Pair next() 
		{
			Pair p = group.pairList[current++];
			surrogateCurrent.x = p.x;
			surrogateCurrent.y = p.y;
			removed = false;
			return surrogateCurrent;
		}

		/**
		 * Resets this iterator.
		 */
		public void reset() 
		{
			this.removed = false;
			this.current = 0;
		}
		
		@Override
		public void remove()
		{
			if (removed) 
				throw new IllegalStateException("remove() called before next()");
			
			Pair p = group.pairList[current - 1];
			group.remove(p.x, p.y);
			current--;
			removed = true;
		}
		
	}

	/**
	 * Local cache for multiple operations.
	 */
	private static class Cache
	{
		private static final ThreadLocal<Cache> LOCAL = ThreadLocal.withInitial(()->new Cache());
		 
		private int[] orderingSource;
		private int[] randomOrdering;
		private Pair tempPair;
		
		private Cache() 
		{
			tempPair = new Pair();
		}
		
		private void doRandom(Random random, int length, int amount)
		{
			if (orderingSource == null)
			{
				orderingSource = new int[length];
				randomOrdering = new int[length];
				fillOrdering(0);
			}
			else if (orderingSource.length < length)
			{
				int[] newOrdering = new int[length];
				randomOrdering = new int[length];
				int oldLen = orderingSource.length;
				System.arraycopy(orderingSource, 0, newOrdering, 0, oldLen);
				orderingSource = newOrdering;
				fillOrdering(oldLen);
			}
			
			System.arraycopy(orderingSource, 0, randomOrdering, 0, orderingSource.length);
			for (int i = 0; i < amount; i++)
			{
				int index = randInt(random, i, length - 1);
				int temp = randomOrdering[i];
				randomOrdering[i] = randomOrdering[index];
				randomOrdering[index] = temp;
			}
		}
		
		private int randInt(Random rand, int lo, int hi)
		{
		    return rand.nextInt(hi - lo + 1) + lo;
		}
	
	
		private void fillOrdering(int start)
		{
			for (int i = start; i < orderingSource.length; i++)
				orderingSource[i] = i;
		}
		
	}

}
