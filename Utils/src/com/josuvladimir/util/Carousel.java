package com.josuvladimir.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;

public class Carousel extends Gallery {

	public Carousel(Context context) {
		super(context);
		setStaticTransformationsEnabled(true);
	}

	public Carousel(Context context, AttributeSet attrs) {
		super(context, attrs);
		setStaticTransformationsEnabled(true);
	}

	public Carousel(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setStaticTransformationsEnabled(true);
	}

	@SuppressWarnings("unused")
	@Override
	protected boolean getChildStaticTransformation(View child, Transformation transformation) {
		int viewCenter = getViewCenter(child);
		int width = child.getWidth();
		getCarouselCenter();
		transformation.clear();
		transformation.setTransformationType(Transformation.TYPE_MATRIX);
		
		
//		return super.getChildStaticTransformation(child, t);
		return true;
	}

	private int getCarouselCenter() {
		return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
	}

	private int getViewCenter(View view) {
		int center = view.getLeft() + view.getWidth() / 2;
		return center;
	}
}