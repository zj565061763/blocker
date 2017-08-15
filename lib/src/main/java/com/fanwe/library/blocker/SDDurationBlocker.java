package com.fanwe.library.blocker;

/**
 * 可以根据时间间隔来拦截事件的类
 */
public class SDDurationBlocker
{
    /**
     * 默认拦截间隔
     */
    public static final long DEFAULT_BLOCK_DURATION = 500;

    /**
     * 拦截间隔
     */
    private long mBlockDuration;
    /**
     * 最后一次保存的触发拦截时间
     */
    private long mLastBlockTime;
    /**
     * 是否自动保存最后一次触发拦截的时间，默认自动保存
     */
    private boolean mAutoSaveLastBlockTime = true;

    public SDDurationBlocker()
    {
        this(DEFAULT_BLOCK_DURATION);
    }

    public SDDurationBlocker(long blockDuration)
    {
        super();
        setBlockDuration(blockDuration);
    }

    /**
     * 获得拦截间隔（毫秒）
     *
     * @return
     */
    public long getBlockDuration()
    {
        return mBlockDuration;
    }

    /**
     * 获得最后一次保存的触发拦截时间
     *
     * @return
     */
    public long getLastBlockTime()
    {
        return mLastBlockTime;
    }

    /**
     * 保存最后一次触发拦截的时间
     */
    public synchronized void saveLastBlockTime()
    {
        mLastBlockTime = System.currentTimeMillis();
    }

    /**
     * 设置是否自动保存最后一次触发拦截的时间，默认自动保存
     *
     * @param autoSaveLastBlockTime true-自动保存
     */
    public synchronized void setAutoSaveLastBlockTime(boolean autoSaveLastBlockTime)
    {
        mAutoSaveLastBlockTime = autoSaveLastBlockTime;
    }

    /**
     * 设置拦截间隔
     *
     * @param blockDuration 拦截间隔（毫秒）
     */
    public synchronized void setBlockDuration(long blockDuration)
    {
        if (blockDuration < 0)
        {
            blockDuration = 0;
        }
        mBlockDuration = blockDuration;
    }

    /**
     * 当前是否处于拦截的间隔之内
     *
     * @return true-是
     */
    public synchronized boolean isInBlockDuration()
    {
        long duration = System.currentTimeMillis() - mLastBlockTime;
        return duration < mBlockDuration;
    }

    /**
     * 触发拦截
     *
     * @return true-拦截掉
     */
    public synchronized boolean block()
    {
        if (isInBlockDuration())
        {
            // 拦截掉
            return true;
        }

        if (mAutoSaveLastBlockTime)
        {
            saveLastBlockTime();
        }
        return false;
    }
}
