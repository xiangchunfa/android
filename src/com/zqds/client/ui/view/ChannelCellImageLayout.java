/*
 * ChannelCellImageLayout.java
 * classes : com.sohu.sohuvideo.ui.view.ChannelCellImageLayout
 * @author xiangyutian
 * V 4.5.0
 * Create at 2014-3-31 下午8:26:10
 */
package com.zqds.client.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * 16:9图片
 * 
 * @author xiangyutian <br/>
 *         create at 2014-3-31 下午8:26:10
 */
public class ChannelCellImageLayout extends RelativeLayout {

    public ChannelCellImageLayout(Context context) {
        super(context);
    }

    public ChannelCellImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        int childWidthSize = getMeasuredWidth();
        int childHeightSize = childWidthSize;
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}