package com.comm.jksdk.ad.view.chjview;

import android.content.Context;
import android.support.constraint.ConstraintLayout;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.comm.jksdk.R;
import com.comm.jksdk.utils.DisplayUtil;

/**
 * 穿山甲开屏广告view<p>
 *
 * @author zixuefei
 * @since 2019/11/15 11:31
 */
public class ChjSplashAdView extends CHJAdView {
    private ConstraintLayout splashContainer;

    public ChjSplashAdView(Context context) {
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
