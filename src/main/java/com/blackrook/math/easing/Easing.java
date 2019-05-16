/*******************************************************************************
 * Copyright (c) 2019 Black Rook Software
 * This program and the accompanying materials are made available under 
 * the terms of the MIT License, which accompanies this distribution.
 ******************************************************************************/
package com.blackrook.math.easing;

import com.blackrook.math.util.Utils;

/**
 * An enumeration of base easing types for actions. 
 * @author Matthew Tropiano
 */
public enum Easing implements EasingType
{
	LINEAR
	{
		@Override
		public double getScaling(double inputScalar)
		{
			inputScalar = Utils.clampValue(inputScalar, 0f, 1f);
			return inputScalar;
		}
	},
	
	SQUARED_EASE_IN
	{
		@Override
		public double getScaling(double inputScalar)
		{
			inputScalar = Utils.clampValue(inputScalar, 0f, 1f);
			return 1f - (Math.pow(1f-inputScalar, 2));
		}
	},
	
	SQUARED_EASE_OUT
	{
		@Override
		public double getScaling(double inputScalar)
		{
			inputScalar = Utils.clampValue(inputScalar, 0f, 1f);
			return Math.pow(inputScalar, 2);
		}
	},
	
	SQUARED_EASE_IN_AND_OUT
	{
		@Override
		public double getScaling(double inputScalar)
		{
			inputScalar = Utils.clampValue(inputScalar, 0f, 1f);
			inputScalar = inputScalar * 2;
            if (inputScalar < 1) {
                return Math.pow(inputScalar, 2) / 2;
            }
            inputScalar -= 2;
            return (Math.pow(inputScalar, 2) + 2) / 2;
		}
	},
	
	CUBIC_EASE_IN
	{
		@Override
		public double getScaling(double inputScalar)
		{
			inputScalar = Utils.clampValue(inputScalar, 0f, 1f);
			return 1f - (Math.pow(1f-inputScalar, 3));
		}
	},
	
	CUBIC_EASE_OUT
	{
		@Override
		public double getScaling(double inputScalar)
		{
			inputScalar = Utils.clampValue(inputScalar, 0f, 1f);
			return Math.pow(inputScalar, 3);
		}
	},
	
	CUBIC_EASE_IN_AND_OUT
	{
		@Override
		public double getScaling(double inputScalar)
		{
			inputScalar = Utils.clampValue(inputScalar, 0f, 1f);
			inputScalar = inputScalar * 2;
            if (inputScalar < 1) {
                return Math.pow(inputScalar, 3) / 2;
            }
            inputScalar -= 2;
            return (Math.pow(inputScalar, 3) + 2) / 2;
		}
	},
	
	BOUNCE
	{
		@Override
		public double getScaling(double inputScalar)
		{
			inputScalar = Utils.clampValue(inputScalar, 0f, 1f);
            double s = 7.5625f;
            double p = 2.75f;
            double out = 0f;
	        if (inputScalar < (1 / p))
	        {
	            out = s * inputScalar * inputScalar;
	        } 
	        else
	        {
	            if (inputScalar < (2 / p))
	            {
	                inputScalar -= (1.5 / p);
	                out = s * inputScalar * inputScalar + .75f;
	            } 
	            else
	            {
	                if (inputScalar < (2.5 / p))
	                {
	                    inputScalar -= (2.25 / p);
	                    out = s * inputScalar * inputScalar + .9375f;
	                } 
	                else
	                {
	                    inputScalar -= (2.625 / p);
	                    out = s * inputScalar * inputScalar + .984375f;
	                }
	            }
	        }
	        return out;
		}
	},
	
	ELASTIC
	{
		@Override
		public double getScaling(double inputScalar)
		{
			inputScalar = Utils.clampValue(inputScalar, 0f, 1f);
            if (inputScalar == 0 || inputScalar == 1)
            {
                return inputScalar;
            }
            double p = 0.3f;
            double s = p / 4;
            return (Math.pow(2, -10 * inputScalar) * Math.sin((inputScalar - s) * (2 * Math.PI) / p) + 1);
		}
	},
	
	BACK_IN
	{
		@Override
		public double getScaling(double inputScalar)
		{
			inputScalar = Utils.clampValue(inputScalar, 0f, 1f);
            double s = 1.70158f;
            return inputScalar * inputScalar * ((s + 1) * inputScalar - s);
		}
	},
	
	BACK_OUT
	{
		@Override
		public double getScaling(double inputScalar)
		{
			inputScalar = Utils.clampValue(inputScalar, 0f, 1f);
            inputScalar = inputScalar - 1;
            double s = 1.70158f;
            return inputScalar * inputScalar * ((s + 1) * inputScalar + s) + 1;
		}
	},
	;

}
