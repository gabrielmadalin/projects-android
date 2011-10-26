package com.josuvladimir.util;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.ImageView;

public class Carousel extends Gallery {

	private int minZoom = 500;
	private int maxZoom = 0;
	private Camera mCamera = new Camera();
	private int mCarouselCenter;
	private int mWidth;
	private int mImageViewId;
	
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

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mCarouselCenter = getViewCenter(this);
		mWidth = getLeft() + getWidth();
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	protected boolean getChildStaticTransformation(View child, Transformation transformation) {
		ImageView imageView = (ImageView) child.findViewById(mImageViewId);
		int viewCenter = getViewCenter(child);
		transformation.clear();
		transformation.setTransformationType(Transformation.TYPE_MATRIX);
		float zoomPercent = (Math.abs(viewCenter - mCarouselCenter)) / (float) mWidth;
		float currentZoom = maxZoom - (maxZoom - minZoom) * zoomPercent;
		transformView(imageView, transformation, currentZoom);
		return true;
	}

	private void transformView(ImageView view, Transformation transformation, float currentZoom) {
		mCamera.save();
		Matrix matrix = transformation.getMatrix();
		int width = view.getWidth();
		int height = view.getHeight();
		mCamera.translate(0, 0, currentZoom);
		mCamera.getMatrix(matrix);
		matrix.preTranslate(-width/2, -height/2);
		matrix.postTranslate(width/2, height/2);
		mCamera.restore();
	}

	private int getViewCenter(View view) {
		int center = view.getLeft() + view.getWidth() / 2;
		return center;
	}

	public void setMinZoom(int minZoom) {
		this.minZoom = minZoom;
	}

	public int getMinZoom() {
		return minZoom;
	}

	public void setMaxZoom(int maxZoom) {
		this.maxZoom = maxZoom;
	}

	public int getMaxZoom() {
		return maxZoom;
	}

	public int getImageViewId() {
		return mImageViewId;
	}

	public void setImageViewId(int mImageViewId) {
		this.mImageViewId = mImageViewId;
	}
}