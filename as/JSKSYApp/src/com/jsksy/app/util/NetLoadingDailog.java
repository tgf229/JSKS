package com.jsksy.app.util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.jsksy.app.R;

/**
 * 
 * <只控制显示或者不显示对话框，或者单纯的显示对话框没有线程>
 * <功能详细描述>
 * 
 * @author  qiuqiaohua
 * @version  [版本号, Apr 18, 2014]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class NetLoadingDailog
{
    
    private Dialog mDialog;
    
    private Context context;
    
    private static NetLoadingDailog loadingDailog = null;
    
    private boolean isLDShow = false;
    
    public NetLoadingDailog(Context context)
    {
        this.context = context;
    }
    
    /**
     * 
     * <显示Dialog>
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    public void loading()
    {
        try
        {
            if (isLDShow)
            {
                hideLoadingDialog();
            }
            createDialog();
            mDialog.show();
            mDialog.setCanceledOnTouchOutside(false);
            isLDShow = true;
        }
        catch (Exception e)
        {
            if (isLDShow && mDialog != null)
            {
                hideLoadingDialog();
            }
        }
    }
    
    /**
     * 
     * <隐藏对话框>
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    private void hideLoadingDialog()
    {
        isLDShow = false;
        if (mDialog != null && mDialog.isShowing())
        {
            mDialog.dismiss();
        }
    }
    
    /**
     * 
     * <创建对话框>
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    private void createDialog()
    {
        mDialog = null;
        mDialog = new Dialog(context, R.style.loading_dialog);
        //        mDialog.setOnDismissListener(onDismissListener);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        mDialog.setContentView(view);
    }
    
    /**
     * 
     * <dismiss Dialog>
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    public void dismissDialog()
    {
        hideLoadingDialog();
    }
}
