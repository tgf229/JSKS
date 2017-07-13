package com.jsksy.app.util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.jsksy.app.R;

/**
 * 
 * <ֻ������ʾ���߲���ʾ�Ի��򣬻��ߵ�������ʾ�Ի���û���߳�>
 * <������ϸ����>
 * 
 * @author  qiuqiaohua
 * @version  [�汾��, Apr 18, 2014]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
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
     * <��ʾDialog>
     * <������ϸ����>
     * @see [�ࡢ��#��������#��Ա]
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
     * <���ضԻ���>
     * <������ϸ����>
     * @see [�ࡢ��#��������#��Ա]
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
     * <�����Ի���>
     * <������ϸ����>
     * @see [�ࡢ��#��������#��Ա]
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
     * <������ϸ����>
     * @see [�ࡢ��#��������#��Ա]
     */
    public void dismissDialog()
    {
        hideLoadingDialog();
    }
}
