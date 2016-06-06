package com.jsksy.app.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.jsksy.app.JSKSYApplication;
import com.jsksy.app.R;
import com.jsksy.app.callback.DialogCallBack;

/**
 * 
 * <弹出框公共类> <功能详细描述>
 * 
 * @author cyf
 * @version [版本�? 2014-3-24]
 * @see [相关�?方法]
 * @since [产品/模块版本]
 */
public class DialogUtil
{
    
    /**
     * 
     * <版本更新>
     * <功能详细描述>
     * @param context
     * @param title
     * @param content 要显示的内容为数�?     * @param left
     * @param right
     * @param isUpdate
     * @param callBack
     * @return
     * @see [类�?�?方法、类#成员]
     */
    public static Dialog showUpdateDialog(Context context, String title, String[] content, String left, String right,
        final String isUpdate, final DialogCallBack callBack)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.two_button_list_dialog, null);
        ListView contentTV = (ListView)layout.findViewById(R.id.dialog_content);
        TextView titleTV = (TextView)layout.findViewById(R.id.dialog_title_tv);
        Button confirm = (Button)layout.findViewById(R.id.dialog_confirm_bt);
        Button cancel = (Button)layout.findViewById(R.id.dialog_cancel_bt);
        if (content.length > 1)
        {
            contentTV.setVisibility(View.VISIBLE);
        }
        else
        {
            contentTV.setVisibility(View.GONE);
        }
        confirm.setText(left);
        cancel.setText(right);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.two_button_list_dialog_item, content);
        contentTV.setAdapter(adapter);
        titleTV.setText(title);
        final Dialog dialog = new Dialog(context, R.style.main_dialog);
        dialog.setContentView(layout);
        confirm.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                dialog.dismiss();
                callBack.dialogBack();
            }
        });
        cancel.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                if ("1".equals(isUpdate))
                {
                    dialog.dismiss();
                    JSKSYApplication.jsksyApplication.onTerminate();
                }
                else
                {
                    dialog.dismiss();
                }
                
            }
        });
        dialog.setOnKeyListener(getOnKeyListener(isUpdate));
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
    
    private static OnKeyListener getOnKeyListener(final String isUpdate)
    {
        OnKeyListener key = new DialogInterface.OnKeyListener()
        {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
            {
                switch (keyCode)
                {
                    case KeyEvent.KEYCODE_BACK:
                        if ("1".equals(isUpdate))//强制更新
                        {
                            return true;
                        }
                        else
                        {
                            return false;
                        }
                    default:
                        break;
                }
                return false;
            }
        };
        return key;
    }
    
}
