package com.inovaitsys.inozest.customview;

/**
 * Created by Bagya on 6/12/2017.
 */
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inovaitsys.inozest.R;


public class ServiceCustomView extends LinearLayout {
    private String serviceName = "";
    private TextView serviceNameTextView;

    public ServiceCustomView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.task_view, this);
        initViews(context,null);
    }

    public ServiceCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs);
    }

    public ServiceCustomView(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
        initViews(context, attrs);
    }

    private void initViews(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.ServiceCustomView, 0, 0);

        try {
            // get the text and colors specified using the names in attrs.xml
            serviceName = a.getString(R.styleable.ServiceCustomView_serviceName);


        } finally {
            a.recycle();
        }

        View view = LayoutInflater.from(context).inflate(R.layout.task_view, this);

        //task text view
        serviceNameTextView = (TextView) view.findViewById(R.id.serviceNameTextView);
        serviceNameTextView.setText(serviceName);

    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
        if(serviceNameTextView!=null){
            serviceNameTextView.setText(serviceName);
        }
    }


}