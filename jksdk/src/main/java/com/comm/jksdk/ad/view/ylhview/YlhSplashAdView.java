package com.comm.jksdk.ad.view.ylhview;

import android.content.Context;
import android.support.constraint.ConstraintLayout;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.comm.jksdk.R;
import com.comm.jksdk.utils.DisplayUtil;

/**
 * 开屏广告view<p>
 *
 * @author zixuefei
 * @since 2019/11/14 21:53
 */
public class YlhSplashAdView extends YLHAdView {
    private ConstraintLayout splashContainer;

    protected YlhSplashAdView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.ylh_splash_ad_view;
    }

    @Override
    public void initView() {
        splashContainer = findViewById(R.id.splash_ad_container);
        RequestOptions requestOptions = new RequestOptions()
                .optionalTransform(new RoundedCorners(DisplayUtil.dp2px(mContext, 3)))
                .error(R.color.returncolor);//图片加载失败后，显示的图片
    }

}
