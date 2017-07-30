package com.gautamastudios.gingerswiper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gautamastudios.gingerswiper.R;
import com.gautamastudios.gingerswiper.view.SwipeView;

import java.util.List;

public class SwipeListViewAdapter extends BaseAdapter {

    private final Context mContext;
    private List<String> mDataSource;
    private View.OnTouchListener mOnTouchListener;

    public SwipeListViewAdapter(Context context, List<String> dataSource, View.OnTouchListener onTouchListener) {
        mContext = context;
        mDataSource = dataSource;
        mOnTouchListener = onTouchListener;
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SwipeView view = (SwipeView) LayoutInflater.from(mContext).inflate(R.layout.list_view_item, null);
        if (view != convertView) {
            view.init();
            view.setOnTouchListener(mOnTouchListener);
        }
        TextView txtTitle = (TextView) view.findViewById(R.id.title);
        txtTitle.setText(getItem(position).toString());
        return view;
    }

}
