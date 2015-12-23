package com.example.lsj.workspace.utils;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.SparseArray;
import android.view.View;
import android.widget.ListView;

import com.example.lsj.workspace.utils.ABTextUtil;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-4-3
 * Time: ����1:47
 */
public class ABViewUtil {

    /**
     * ������Adapter�м�ViewHolder��ش���
     *
     * @param convertView
     * @param id
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends View> T obtainView(View convertView, int id) {
        SparseArray<View> holder = (SparseArray<View>) convertView.getTag();
        if (holder == null) {
            holder = new SparseArray<View>();
            convertView.setTag(holder);
        }
        View childView = holder.get(id);
        if (childView == null) {
            childView = convertView.findViewById(id);
            holder.put(id, childView);
        }
        return (T) childView;
    }


    /**
     * view����background drawable
     *
     * @param view
     * @param drawable
     */
    public static void setBackgroundDrawable(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(drawable);
        } else {
            view.setBackground(drawable);
        }
    }


    /**
     * ��ȡ�ؼ��ĸ߶ȣ������ȡ�ĸ߶�Ϊ0�������¼���ߴ���ٷ��ظ߶�
     *
     * @param view
     * @return
     */
    public static int getViewMeasuredHeight(View view) {
        int height = view.getMeasuredHeight();
        if (0 < height) {
            return height;
        }
        calcViewMeasure(view);
//        view.getHeight();
        return view.getMeasuredHeight();
    }

    /**
     * ��ȡ�ؼ��Ŀ�ȣ������ȡ�Ŀ��Ϊ0�������¼���ߴ���ٷ��ؿ��
     *
     * @param view
     * @return
     */
    public static int getViewMeasuredWidth(View view) {
//        int width = view.getMeasuredWidth();
//        if(0 < width){
//            return width;
//        }
        calcViewMeasure(view);
        return view.getMeasuredWidth();
    }

    /**
     * �����ؼ��ĳߴ�
     *
     * @param view
     */
    public static void calcViewMeasure(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
//        int width = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
//        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
//        view.measure(width, expandSpec);
    }

    public static int getAllListViewSectionCounts(ListView lv, List dataSource) {
        if (null == lv || ABTextUtil.isEmpty(dataSource)) {
            return 0;
        }
        return dataSource.size() + lv.getHeaderViewsCount() + lv.getFooterViewsCount();
    }


}
