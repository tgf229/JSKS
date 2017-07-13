package com.jsksy.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jsksy.app.R;


public class PullToRefreshView extends LinearLayout
{
    //	private static final String TAG = "PullToRefreshView";
    // refresh states
    private static final int PULL_TO_REFRESH = 2;
    
    private static final int RELEASE_TO_REFRESH = 3;
    
    private static final int REFRESHING = 4;
    
    // pull state
    private static final int PULL_UP_STATE = 0;
    
    private static final int PULL_DOWN_STATE = 1;
    
    /**
     * last y
     */
    private int mLastMotionY;
    
    /**
     * lock
     */
    //	private boolean mLock;
    /**
     * header view
     */
    private View mHeaderView;
    
    /**
     * footer view
     */
    private View mFooterView;
    
    /**
     * list or grid
     */
    private AdapterView<?> mAdapterView;
    
    /**
     * scrollview
     */
    private ScrollView mScrollView;
    
    /**
     * header view height
     */
    private int mHeaderViewHeight;
    
    /**
     * footer view height
     */
    private int mFooterViewHeight;
    
    /**
     * header view image
     */
    private ImageView mHeaderImageView;
    
    /**
     * footer view image
     */
    private ImageView mFooterImageView;
    
    /**
     * header tip text
     */
    private TextView mHeaderTextView;
    
    /**
     * footer tip text
     */
    private TextView mFooterTextView;
    
    /**
     * header refresh time
     */
    //	private TextView mHeaderUpdateTextView;
    /**
     * footer refresh time
     */
    // private TextView mFooterUpdateTextView;
    /**
     * header progress bar
     */
    private ProgressBar mHeaderProgressBar;
    
    /**
     * footer progress bar
     */
    private ProgressBar mFooterProgressBar;
    
    /**
     * layout inflater
     */
    private LayoutInflater mInflater;
    
    /**
     * header view current state
     */
    private int mHeaderState;
    
    /**
     * footer view current state
     */
    private int mFooterState;
    
    /**
     * pull state,pull up or pull down;PULL_UP_STATE or PULL_DOWN_STATE
     */
    private int mPullState;
    
    /**
     * ��Ϊ���µļ�??�ı��ͷ����
     */
    private RotateAnimation mFlipAnimation;
    
    /**
     * ��Ϊ����ļ�??��ת
     */
    private RotateAnimation mReverseFlipAnimation;
    
    /**
     * footer refresh listener
     */
    //	private OnFooterRefreshListener mOnFooterRefreshListener;
    /**
     * footer refresh listener
     */
    private OnHeaderRefreshListener mOnHeaderRefreshListener;
    
    /**
     * last update time
     */
    //	private String mLastUpdateTime;
    /**
    /**
     * last x
     */
    private int mLastMotionX;
    
    /**
     * lock
     */
    private boolean mLock;
    
    public PullToRefreshView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }
    
    public PullToRefreshView(Context context)
    {
        super(context);
        init();
    }
    
    /**
     * init
     * 
     * @param context
     */
    private void init()
    {
        //????���ó�vertical
        setOrientation(LinearLayout.VERTICAL);
        // Load all of the animations we need in code rather than through XML
        mFlipAnimation =
            new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mFlipAnimation.setInterpolator(new LinearInterpolator());
        mFlipAnimation.setDuration(250);
        mFlipAnimation.setFillAfter(true);
        mReverseFlipAnimation =
            new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
        mReverseFlipAnimation.setDuration(250);
        mReverseFlipAnimation.setFillAfter(true);
        
        mInflater = LayoutInflater.from(getContext());
        // header view �ڴ����,��֤�ǵ�????��ӵ�linearlayout�����϶�
        addHeaderView();
    }
    
    private void addHeaderView()
    {
        // header view
        mHeaderView = mInflater.inflate(R.layout.refresh_header, this, false);
        
        mHeaderImageView = (ImageView)mHeaderView.findViewById(R.id.pull_to_refresh_image);
        mHeaderTextView = (TextView)mHeaderView.findViewById(R.id.pull_to_refresh_text);
        //		mHeaderUpdateTextView = (TextView) mHeaderView
        //				.findViewById(R.id.pull_to_refresh_updated_at);
        mHeaderProgressBar = (ProgressBar)mHeaderView.findViewById(R.id.pull_to_refresh_progress);
        // header layout
        measureView(mHeaderView);
        mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, mHeaderViewHeight);
        // ����topMargin��??Ϊ����header View�߶�,������������??????
        params.topMargin = -(mHeaderViewHeight);
        // mHeaderView.setLayoutParams(params1);
        addView(mHeaderView, params);
        
    }
    
    private void addFooterView()
    {
        // footer view
        mFooterView = mInflater.inflate(R.layout.refresh_footer, this, false);
        mFooterView.setVisibility(View.GONE);
        mFooterImageView = (ImageView)mFooterView.findViewById(R.id.pull_to_load_image);
        mFooterTextView = (TextView)mFooterView.findViewById(R.id.pull_to_load_text);
        mFooterProgressBar = (ProgressBar)mFooterView.findViewById(R.id.pull_to_load_progress);
        // footer layout
        measureView(mFooterView);
        mFooterViewHeight = mFooterView.getMeasuredHeight();
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, mFooterViewHeight);
        // int top = getHeight();
        // params.topMargin
        // =getHeight();//������getHeight()==0,����onInterceptTouchEvent()������getHeight()�Ѿ���????����??;
        // getHeight()????ʱ??�ḳ??��??���о�һ??
        // ���������Բ�????��ֱ����??ֻҪAdapterView�ĸ߶���MATCH_PARENT,��ôfooter view�ͻᱻ��ӵ�????,����??
        addView(mFooterView, params);
    }
    
    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        // footer view �ڴ���ӱ�֤��ӵ�linearlayout�е�????
        addFooterView();
        initContentAdapterView();
    }
    
    /**
     * init AdapterView like ListView,GridView and so on;or init ScrollView
     * 
     */
    private void initContentAdapterView()
    {
        int count = getChildCount();
        if (count < 3)
        {
            throw new IllegalArgumentException(
                "This layout must contain 3 child views,and AdapterView or ScrollView must in the second position!");
        }
        View view = null;
        for (int i = 0; i < count - 1; ++i)
        {
            view = getChildAt(i);
            if (view instanceof AdapterView<?>)
            {
                mAdapterView = (AdapterView<?>)view;
            }
            if (view instanceof ScrollView)
            {
                // finish later
                mScrollView = (ScrollView)view;
            }
        }
        if (mAdapterView == null && mScrollView == null)
        {
            throw new IllegalArgumentException("must contain a AdapterView or ScrollView in this layout!");
        }
    }
    
    private void measureView(View child)
    {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null)
        {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0)
        {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        }
        else
        {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent e)
    {
        int y = (int)e.getRawY();
        int x = (int)e.getRawX();
        switch (e.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                // ��������down�¼�,��¼y����
                mLastMotionY = y;
                mLastMotionX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                
                int deltaY = y - mLastMotionY;
                
                int deltaX = x - mLastMotionX;
                //X�����Y���ƶ�������Ǻ��򻬶�������Ϊ��??
                if (Math.abs(deltaX) > Math.abs(deltaY))
                {
                    return false;
                }
                else
                {
                    // deltaY > 0 ��������??< 0��������??
                    if (isRefreshViewScroll(deltaY))
                    {
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return false;
    }
    
    /*
     * �����onInterceptTouchEvent()������û����??��onInterceptTouchEvent()����??return
     * false)����PullToRefreshView ����View����??����������ķ�������??����PullToRefreshView�Լ�����??
     */
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (mLock)
        {
            return true;
        }
        int y = (int)event.getRawY();
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                // onInterceptTouchEvent�Ѿ���¼
                // mLastMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - mLastMotionY;
                if (mPullState == PULL_DOWN_STATE)
                {
                    // PullToRefreshViewִ������
                    headerPrepareToRefresh(deltaY);
                    // setHeaderPadding(-mHeaderViewHeight);
                }
                else if (mPullState == PULL_UP_STATE)
                {
                    // PullToRefreshViewִ������
                    //                                  footerPrepareToRefresh(deltaY);
                }
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                int topMargin = getHeaderTopMargin();
                if (mPullState == PULL_DOWN_STATE)
                {
                    if (topMargin >= 0)
                    {
                        // ????ˢ��
                        headerRefreshing();
                    }
                    else
                    {
                        // ��û��ִ��ˢ�£���������
                        setHeaderTopMargin(-mHeaderViewHeight);
                    }
                }
                else if (mPullState == PULL_UP_STATE)
                {
                    if (Math.abs(topMargin) >= mHeaderViewHeight + mFooterViewHeight)
                    {
                        // ????ִ��footer ˢ��
                        //                  footerRefreshing();
                    }
                    else
                    {
                        // ��û��ִ��ˢ�£���������
                        setHeaderTopMargin(-mHeaderViewHeight);
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }
    
    /**
     * �Ƿ�Ӧ�õ��˸�View,��PullToRefreshView����
     * 
     * @param deltaY
     *            , deltaY > 0 ��������??< 0��������??
     * @return
     */
    private boolean isRefreshViewScroll(int deltaY)
    {
        if (mHeaderState == REFRESHING || mFooterState == REFRESHING)
        {
            return false;
        }
        //����ListView��GridView
        if (mAdapterView != null)
        {
            // ��view(ListView or GridView)���������
            if (deltaY > 0)
            {
                
                View child = mAdapterView.getChildAt(0);
                if (child == null)
                {
                    // ���mAdapterView��û����??����??
                    return false;
                }
                if (mAdapterView.getFirstVisiblePosition() == 0 && child.getTop() == 0)
                {
                    mPullState = PULL_DOWN_STATE;
                    return true;
                }
                int top = child.getTop();
                int padding = mAdapterView.getPaddingTop();
                if (mAdapterView.getFirstVisiblePosition() == 0 && Math.abs(top - padding) <= 8)
                {//����֮ǰ??�����ж�,�����ڲ�??��û�ҵ�ԭ��
                    mPullState = PULL_DOWN_STATE;
                    return true;
                }
                
            }
            else if (deltaY < 0)
            {
                View lastChild = mAdapterView.getChildAt(mAdapterView.getChildCount() - 1);
                if (lastChild == null)
                {
                    // ���mAdapterView��û����??����??
                    return false;
                }
                // ????????��view��BottomС�ڸ�View�ĸ߶�˵��mAdapterView������û��������view,
                // ���ڸ�View�ĸ߶�˵��mAdapterView�Ѿ���������??
                //                if (lastChild.getBottom() <= getHeight()
                //                    && mAdapterView.getLastVisiblePosition() == mAdapterView.getCount() - 1)
                //                {
                //                    mPullState = PULL_UP_STATE;
                //                    return true;
                //                }
            }
        }
        // ����ScrollView
        if (mScrollView != null)
        {
            // ��scroll view���������
            View child = mScrollView.getChildAt(0);
            if (deltaY > 0 && mScrollView.getScrollY() == 0)
            {
                mPullState = PULL_DOWN_STATE;
                return true;
            }
            //            else if (deltaY < 0 && child.getMeasuredHeight() <= getHeight() + mScrollView.getScrollY())
            //            {
            //                mPullState = PULL_UP_STATE;
            //                return true;
            //            }
        }
        return false;
    }
    
    /**
     * header ׼��ˢ��,��ָ�ƶ�����,��û����??
     * 
     * @param deltaY
     *            ,��ָ�����ľ�??
     */
    private void headerPrepareToRefresh(int deltaY)
    {
        int newTopMargin = changingHeaderViewTopMargin(deltaY);
        // ��header view��topMargin>=0ʱ��˵���Ѿ���ȫ��ʾ����??�޸�header view ����ʾ״??
        if (newTopMargin >= 0 && mHeaderState != RELEASE_TO_REFRESH)
        {
            mHeaderTextView.setText(R.string.pull_to_refresh_release_label);
            //			mHeaderUpdateTextView.setVisibility(View.VISIBLE);
            mHeaderImageView.clearAnimation();
            mHeaderImageView.startAnimation(mFlipAnimation);
            mHeaderState = RELEASE_TO_REFRESH;
        }
        else if (newTopMargin < 0 && newTopMargin > -mHeaderViewHeight)
        {// �϶�ʱû����??
            mHeaderImageView.clearAnimation();
            mHeaderImageView.startAnimation(mFlipAnimation);
            // mHeaderImageView.
            mHeaderTextView.setText(R.string.pull_to_refresh_pull_label);
            mHeaderState = PULL_TO_REFRESH;
        }
    }
    
    /**
     * footer ׼��ˢ��,��ָ�ƶ�����,��û����??�ƶ�footer view�߶�ͬ�����ƶ�header view
     * �߶���һ��������ͨ���޸�header view��topmargin��??����??
     * 
     * @param deltaY
     *            ,��ָ�����ľ�??
     */
    private void footerPrepareToRefresh(int deltaY)
    {
        int newTopMargin = changingHeaderViewTopMargin(deltaY);
        // ���header view topMargin �ľ���??���ڻ����header + footer �ĸ�??
        // ˵��footer view ��ȫ��ʾ�����ˣ��޸�footer view ����ʾ״??
        if (Math.abs(newTopMargin) >= (mHeaderViewHeight + mFooterViewHeight) && mFooterState != RELEASE_TO_REFRESH)
        {
            mFooterTextView.setText(R.string.pull_to_refresh_footer_release_label);
            mFooterImageView.clearAnimation();
            mFooterImageView.startAnimation(mFlipAnimation);
            mFooterState = RELEASE_TO_REFRESH;
        }
        else if (Math.abs(newTopMargin) < (mHeaderViewHeight + mFooterViewHeight))
        {
            mFooterImageView.clearAnimation();
            mFooterImageView.startAnimation(mFlipAnimation);
            mFooterTextView.setText(R.string.pull_to_refresh_footer_pull_label);
            mFooterState = PULL_TO_REFRESH;
        }
    }
    
    /**
     * �޸�Header view top margin��??
     * 
     * @param deltaY
     */
    private int changingHeaderViewTopMargin(int deltaY)
    {
        LayoutParams params = (LayoutParams)mHeaderView.getLayoutParams();
        float newTopMargin = params.topMargin + deltaY * 0.3f;
        //�����������????����,��Ϊ��ǰ������Ȼ���ͷ���ֱָ������,�������ˢ�¸�������,��л����yufengzungzhe��ָ??
        //��ʾ�������������һ�ξ�??Ȼ��ֱ������
        if (deltaY > 0 && mPullState == PULL_UP_STATE && Math.abs(params.topMargin) <= mHeaderViewHeight)
        {
            return params.topMargin;
        }
        //ͬ��??��������????����,������ָ���������ʱ????��bug
        if (deltaY < 0 && mPullState == PULL_DOWN_STATE && Math.abs(params.topMargin) >= mHeaderViewHeight)
        {
            return params.topMargin;
        }
        params.topMargin = (int)newTopMargin;
        mHeaderView.setLayoutParams(params);
        invalidate();
        return params.topMargin;
    }
    
    /**
     * header refreshing
     * 
     */
    public void headerRefreshing()
    {
        mHeaderState = REFRESHING;
        setHeaderTopMargin(0);
        mHeaderImageView.setVisibility(View.GONE);
        mHeaderImageView.clearAnimation();
        mHeaderImageView.setImageDrawable(null);
        mHeaderProgressBar.setVisibility(View.VISIBLE);
        mHeaderTextView.setText(R.string.pull_to_refresh_refreshing_label);
        if (mOnHeaderRefreshListener != null)
        {
            mOnHeaderRefreshListener.onHeaderRefresh(this);
        }
    }
    
    /**
     * footer refreshing
     * 
     */
    private void footerRefreshing()
    {
        mFooterState = REFRESHING;
        int top = mHeaderViewHeight + mFooterViewHeight;
        setHeaderTopMargin(-top);
        mFooterImageView.setVisibility(View.GONE);
        mFooterImageView.clearAnimation();
        mFooterImageView.setImageDrawable(null);
        mFooterProgressBar.setVisibility(View.VISIBLE);
        mFooterTextView.setText(R.string.pull_to_refresh_footer_refreshing_label);
        //		if (mOnFooterRefreshListener != null) {
        //			mOnFooterRefreshListener.onFooterRefresh(this);
        //		}
    }
    
    /**
     * ����header view ��topMargin��??
     * 
     * @param topMargin
     *            ��Ϊ0ʱ��˵��header view �պ���ȫ��ʾ����????mHeaderViewHeightʱ��˵����ȫ����??
     */
    private void setHeaderTopMargin(int topMargin)
    {
        LayoutParams params = (LayoutParams)mHeaderView.getLayoutParams();
        params.topMargin = topMargin;
        mHeaderView.setLayoutParams(params);
        invalidate();
    }
    
    /**
     * header view ��ɸ��º�ָ���ʼ״??
     * 
     */
    public void onHeaderRefreshComplete()
    {
        setHeaderTopMargin(-mHeaderViewHeight);
        mHeaderImageView.setVisibility(View.VISIBLE);
        mHeaderImageView.setImageResource(R.drawable.ic_pulltorefresh_arrow);
        mHeaderTextView.setText(R.string.pull_to_refresh_pull_label);
        mHeaderProgressBar.setVisibility(View.GONE);
        // mHeaderUpdateTextView.setText("");
        mHeaderState = PULL_TO_REFRESH;
    }
    
    /**
     * Resets the list to a normal state after a refresh.
     * 
     * @param lastUpdated
     *            Last updated at.
     */
    public void onHeaderRefreshComplete(CharSequence lastUpdated)
    {
        setLastUpdated(lastUpdated);
        onHeaderRefreshComplete();
    }
    
    /**
     * footer view ��ɸ��º�ָ���ʼ״??
     */
    public void onFooterRefreshComplete()
    {
        setHeaderTopMargin(-mHeaderViewHeight);
        mFooterImageView.setVisibility(View.VISIBLE);
        mFooterImageView.setImageResource(R.drawable.ic_pulltorefresh_arrow_up);
        mFooterTextView.setText(R.string.pull_to_refresh_footer_pull_label);
        mFooterProgressBar.setVisibility(View.GONE);
        // mHeaderUpdateTextView.setText("");
        mFooterState = PULL_TO_REFRESH;
    }
    
    /**
     * Set a text to represent when the list was last updated.
     * 
     * @param lastUpdated
     *            Last updated at.
     */
    public void setLastUpdated(CharSequence lastUpdated)
    {
        //		if (lastUpdated != null) {
        //			mHeaderUpdateTextView.setVisibility(View.VISIBLE);
        //			mHeaderUpdateTextView.setText(lastUpdated);
        //		} else {
        //			mHeaderUpdateTextView.setVisibility(View.GONE);
        //		}
    }
    
    /**
     * ��ȡ��ǰheader view ��topMargin
     * 
     */
    private int getHeaderTopMargin()
    {
        LayoutParams params = (LayoutParams)mHeaderView.getLayoutParams();
        return params.topMargin;
    }
    
    //	/**
    //	 * lock
    //	 * 
    //	 */
    //	private void lock() {
    //		mLock = true;
    //	}
    //
    //	/**
    //	 * unlock
    //	 * 
    //	 */
    //	private void unlock() {
    //		mLock = false;
    //	}
    
    /**
     * set headerRefreshListener
     * 
     * @param headerRefreshListener
     */
    public void setOnHeaderRefreshListener(OnHeaderRefreshListener headerRefreshListener)
    {
        mOnHeaderRefreshListener = headerRefreshListener;
    }
    
    //	public void setOnFooterRefreshListener(
    //			OnFooterRefreshListener footerRefreshListener) {
    //		mOnFooterRefreshListener = footerRefreshListener;
    //	}
    //
    //	/**
    //	 * Interface definition for a callback to be invoked when list/grid footer
    //	 * view should be refreshed.
    //	 */
    //	public interface OnFooterRefreshListener {
    //		public void onFooterRefresh(PullToRefreshView view);
    //	}
    
    /**
     * Interface definition for a callback to be invoked when list/grid header
     * view should be refreshed.
     */
    public interface OnHeaderRefreshListener
    {
        public void onHeaderRefresh(PullToRefreshView view);
    }
}
