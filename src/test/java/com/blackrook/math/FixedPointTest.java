package com.blackrook.math;

public class FixedPointTest 
{
	@SuppressWarnings("unused")
	public static void main(String[] args) 
	{
		FixedPoint.floatToFixed1616(0f);
		FixedPoint.floatToFixed1616(1f);
		FixedPoint.floatToFixed1616(1.5f);
		FixedPoint.floatToFixed1616(1.9999847f);
		
		FixedPoint.floatToFixed1616(-1f);         // -65536
		FixedPoint.floatToFixed1616(-1.5f);       // -98304
		FixedPoint.floatToFixed1616(-1.9999847f); // -131071
		
		FixedPoint.fixed1616ToFloat(0);           // 0.0
		FixedPoint.fixed1616ToFloat(65536);       // 1.0
		FixedPoint.fixed1616ToFloat(98304);       // 1.5
		FixedPoint.fixed1616ToFloat(131071);      // 1.9999847
		
		FixedPoint.fixed1616ToFloat(-65536);      // -1.0
		FixedPoint.fixed1616ToFloat(-98304);      // -1.5
		FixedPoint.fixed1616ToFloat(-131071);     // -1.9999847
		
		int fixed2p0 = FixedPoint.floatToFixed1616(2.0f);
		int fixed0p5 = FixedPoint.floatToFixed1616(0.5f); 
		int fixedn2p0 = FixedPoint.floatToFixed1616(-2.0f);
		int fixedn0p5 = FixedPoint.floatToFixed1616(-0.5f); 
		int fixed1p0 = FixedPoint.floatToFixed1616(1.0f); 
		int fixedn1p0 = FixedPoint.floatToFixed1616(-1.0f); 
		
		FixedPoint.fixed1616ToFloat(FixedPoint.add1616(fixed2p0, fixed0p5));   // 2.5
		FixedPoint.fixed1616ToFloat(FixedPoint.add1616(fixed2p0, fixedn0p5));  // 1.5
		FixedPoint.fixed1616ToFloat(FixedPoint.add1616(fixedn2p0, fixed0p5));  // -1.5
		FixedPoint.fixed1616ToFloat(FixedPoint.add1616(fixedn2p0, fixedn0p5)); // -2.5
		
		FixedPoint.fixed1616ToFloat(FixedPoint.sub1616(fixed2p0, fixed0p5));   // 1.5
		FixedPoint.fixed1616ToFloat(FixedPoint.sub1616(fixed2p0, fixedn0p5));  // 2.5
		FixedPoint.fixed1616ToFloat(FixedPoint.sub1616(fixedn2p0, fixed0p5));  // -2.5
		FixedPoint.fixed1616ToFloat(FixedPoint.sub1616(fixedn2p0, fixedn0p5)); // -1.5

		FixedPoint.fixed1616ToFloat(FixedPoint.mul1616(fixed2p0, fixed2p0));   // 4.0
		FixedPoint.fixed1616ToFloat(FixedPoint.mul1616(fixed2p0, fixed0p5));   // 1.0
		FixedPoint.fixed1616ToFloat(FixedPoint.mul1616(fixed0p5, fixed0p5));   // 0.25
		FixedPoint.fixed1616ToFloat(FixedPoint.mul1616(fixed2p0, fixedn2p0));  // -4.0
		FixedPoint.fixed1616ToFloat(FixedPoint.mul1616(fixed2p0, fixedn0p5));  // -1.0
		FixedPoint.fixed1616ToFloat(FixedPoint.mul1616(fixed0p5, fixedn0p5));  // -0.25
		
		FixedPoint.fixed1616ToFloat(FixedPoint.mul1616(fixedn2p0, fixed2p0));   // -4.0
		FixedPoint.fixed1616ToFloat(FixedPoint.mul1616(fixedn2p0, fixed0p5));   // -1.0
		FixedPoint.fixed1616ToFloat(FixedPoint.mul1616(fixedn0p5, fixed0p5));   // -0.25
		FixedPoint.fixed1616ToFloat(FixedPoint.mul1616(fixedn2p0, fixedn2p0));  // 4.0
		FixedPoint.fixed1616ToFloat(FixedPoint.mul1616(fixedn2p0, fixedn0p5));  // 1.0
		FixedPoint.fixed1616ToFloat(FixedPoint.mul1616(fixedn0p5, fixedn0p5));  // 0.25
		
	}

}
