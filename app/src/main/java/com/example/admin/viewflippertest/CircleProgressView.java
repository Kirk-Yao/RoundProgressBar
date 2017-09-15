package com.example.admin.viewflippertest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by admin on 2017/9/15.
 */

public class CircleProgressView extends View{

    private Paint textPaint,inCirclePaint,outCirclePaint;

    private int textSize = 100;
    private int inCircleRadius = 200;
    private int outCircleWidth = 30;
    private int inCircleColor = Color.LTGRAY;
    private int outCircleColor = Color.BLACK;
    private int textColor = Color.BLACK;

    private String progressText = "Start";

    private float progress = 0;
    private boolean isStart = false;
    private RectF circleRect;
    private int viewWidth;
    private int viewHeight;
    private int setRadius = inCircleRadius;

    public CircleProgressView(Context context) {
        this(context,null);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(attrs);
        initPaint();
    }

    private void initAttrs(AttributeSet attributeSet){
        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet,R.styleable.CircleProgressView);
        inCircleRadius = typedArray.getDimensionPixelSize(R.styleable.CircleProgressView_inCircleRadius,inCircleRadius);
        inCircleColor = typedArray.getColor(R.styleable.CircleProgressView_inCircleColor,inCircleColor);
        outCircleWidth = typedArray.getDimensionPixelSize(R.styleable.CircleProgressView_progressWidth,outCircleWidth);
        outCircleColor = typedArray.getColor(R.styleable.CircleProgressView_progressColor,outCircleColor);
        textColor = typedArray.getColor(R.styleable.CircleProgressView_progressTextColor, textColor);
        textSize = typedArray.getDimensionPixelSize(R.styleable.CircleProgressView_progressTextSize,textSize);
        typedArray.recycle();
    }

    private void initPaint(){
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);

        inCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        inCirclePaint.setStyle(Paint.Style.FILL);
        inCirclePaint.setColor(inCircleColor);

        outCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outCirclePaint.setColor(outCircleColor);
        outCirclePaint.setStyle(Paint.Style.STROKE);
        outCirclePaint.setStrokeWidth(outCircleWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float centerX = getMeasuredWidth()/2;
        float centerY = getMeasuredHeight()/2;

        //画内圆
        canvas.drawCircle(centerX,centerY,inCircleRadius,inCirclePaint);

        //进度圆弧
        if (circleRect == null){
            circleRect = new RectF((int)(centerX - inCircleRadius),(int)(centerY - inCircleRadius),
                    (int)(centerX+inCircleRadius),(int)(centerY+inCircleRadius));
        }
        if (isStart){
            float swipeProgress = progress / 100f * 360;
            canvas.drawArc(circleRect,-90,swipeProgress,false,outCirclePaint);

            progressText = progress + "%";
            //文字
            canvas.drawText(progressText,centerX - textPaint.measureText(progressText) / 2,
                    centerY - (textPaint.ascent() + textPaint.descent()) / 2,textPaint);
        } else {
            canvas.drawCircle(centerX,centerY,inCircleRadius + outCircleWidth/2,outCirclePaint);
            //文字
            canvas.drawText(progressText,centerX - textPaint.measureText(progressText) / 2,
                    centerY - (textPaint.ascent() + textPaint.descent()) / 2,textPaint);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        Log.d("getWidth() on: ", viewWidth+"");
    }

    public void setProgress(float progress){

        if (progress < 0){
            progress = 0;
        }
        isStart = true;
        //到100自动停止
        if (progress >= 100){
            progress = 100;
            progressText = progress + "%";
            isStart = false;
        }
        this.progress = progress;

        postInvalidate();
    }

    public float getProgress(){
        return progress;
    }

    public void progressFinish(){
        isStart = false;
        progressText = "Done";
    }

    public void setTextColor(int textColor){
        this.textColor = textColor;
    }

    public void setTextSize(int textSize){
        this.textSize = textSize;
        textPaint.setTextSize(textSize);
    }

    public int getTextSize(){
        return textSize;
    }

    public void setInCircleRadius(int radius){
        inCircleRadius = radius;
    }

    public void setInCircleColor(int color){
        inCircleColor = color;
        inCirclePaint.setColor(inCircleColor);
    }

    public void setOutCircleWidth(int width){
        outCircleWidth = width;
    }

    public void setOutCircleColor(int color){
        outCircleColor = color;
        outCirclePaint.setColor(outCircleColor);
    }
}
