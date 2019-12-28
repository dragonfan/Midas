package com.jk.adsdkdemo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.jk.adsdkdemo.utils.LogUtils;
import com.qq.e.ads.nativ.MediaView;
import com.qq.e.ads.nativ.widget.NativeAdContainer;
import com.xnad.sdk.MidasAdSdk;
import com.xnad.sdk.ad.entity.AdInfo;
import com.xnad.sdk.ad.entity.MidasSelfRenderAd;
import com.xnad.sdk.ad.outlistener.AdOutChargeListener;
import com.xnad.sdk.ad.outlistener.AdSelfRenderListener;
import com.xnad.sdk.config.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: ${PROJECT_NAME}
 * @Package: ${PACKAGE_NAME}
 * @ClassName: ${NAME}
 * @Description: 自渲染广告
 * @Author: fanhailong
 * @CreateDate: ${DATE} ${TIME}
 * @UpdateUser: 更新者：
 * @UpdateDate: ${DATE} ${TIME}
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class SelfRenderAcitvity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = SelfRenderAcitvity.class.getSimpleName();

    private Button requestBt, preloadBt;
    private FrameLayout mContainer;
    private EditText positionEt;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_img_fake_video);
        mContainer = findViewById(R.id.container);
        requestBt = findViewById(R.id.button_request_ad);
        requestBt.setOnClickListener(this);
        positionEt = findViewById(R.id.et_position_id);
        positionEt.setText("6711347261");
        spinner = findViewById(R.id.spinner);
        preloadBt = findViewById(R.id.button_preloading_ad);
        preloadBt.setOnClickListener(this);

        String[] ctype = new String[]{"success_page_ad_1", "success_page_ad_2", "success_page_ad_3", "newlist_1_1", "homepage_ad_1", "homepage_ad_2", "lock_screen_advertising", "external_advertising_ad_1", "external_big_image_02"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ctype);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    String position = null;

    //在生命周期调用相应的方法
    MidasSelfRenderAd midasSelfRenderAd;

    @Override
    protected void onResume() {
        super.onResume();
        if (midasSelfRenderAd != null) {
            midasSelfRenderAd.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (midasSelfRenderAd != null) {
            midasSelfRenderAd.destroy();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_preloading_ad:  //预加载广告
            case R.id.button_request_ad: //预加载广告
//                position = spinner.getSelectedItem().toString();
                position = positionEt.getText().toString().trim();
                if (TextUtils.isEmpty(position)) {
                    Toast.makeText(getApplicationContext(), "accept->输入的位置不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                MidasAdSdk.getAdsManger().loadMidasSelfRenderAd(this, position, new AdSelfRenderListener<AdInfo>() {

                    @Override
                    public void adSuccess(AdInfo info) {
                        MidasSelfRenderAd midasSelfRenderAd = (MidasSelfRenderAd) info.getMidasAd();
                        if (Constants.AdSourceType.ChuanShanJia.equals(midasSelfRenderAd.getAdSource())) {
                            cshBindView(midasSelfRenderAd);
                        } else {
                            ylhBindView(midasSelfRenderAd);
                        }
                    }

                    @Override
                    public void adError(AdInfo info, int errorCode, String errorMsg) {
                        LogUtils.e(TAG, "DEMO>>>adError： " + errorMsg);
                    }
                });
                break;
        }
    }

    private void ylhBindView(MidasSelfRenderAd midasSelfRenderAd) {
        TextView title;
        MediaView mediaView;
        RelativeLayout adInfoContainer;
        TextView name;
        TextView desc;
        ImageView logo;
        ImageView poster;
        Button download;
        Button ctaButton;
        NativeAdContainer container;
//        AQuery logoAQ;
        View btnsContainer;
        Button btnPlay;
        Button btnPause;
        Button btnStop;
        CheckBox btnMute;
        FrameLayout customContainer;

        View view = LayoutInflater.from(this).inflate(R.layout.news_item_ylh, null);
        mediaView = view.findViewById(R.id.gdt_media_view);
//        adInfoContainer = view.findViewById(R.id.ad_info);
        logo = view.findViewById(R.id.img_logo);
        poster = view.findViewById(R.id.img_poster);
        name = view.findViewById(R.id.text_title);
        desc = view.findViewById(R.id.text_desc);
        download = view.findViewById(R.id.btn_download);
        ctaButton = view.findViewById(R.id.btn_cta);
        container = view.findViewById(R.id.native_ad_container);
        btnsContainer = view.findViewById(R.id.video_btns_container);
        btnPlay = view.findViewById(R.id.btn_play);
        btnPause = view.findViewById(R.id.btn_pause);
        btnStop = view.findViewById(R.id.btn_stop);
        btnMute = view.findViewById(R.id.btn_mute);
        customContainer = view.findViewById(R.id.custom_container);

        Glide.with(this).load(midasSelfRenderAd.getIconUrl()).into(logo);
        name.setText(midasSelfRenderAd.getTitle());
        desc.setText(midasSelfRenderAd.getDescription());
        Glide.with(this).load(midasSelfRenderAd.getImageUrl()).into(poster);
        //是否是视频
        if (midasSelfRenderAd.getMidasAdPatternType() == 2) {
            poster.setVisibility(View.INVISIBLE);
            mediaView.setVisibility(View.VISIBLE);
            btnsContainer.setVisibility(View.VISIBLE);
        } else {
            poster.setVisibility(View.VISIBLE);
            mediaView.setVisibility(View.INVISIBLE);
            btnsContainer.setVisibility(View.GONE);
        }
        //优量汇点击的时间一定要在NativeAdContainer容器里面
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(customContainer);
        clickableViews.add(download);
        //优量汇的viewGroup一定要是NativeAdContainer对象
        midasSelfRenderAd.bindViewToAdListener(this, container, clickableViews, null, new AdOutChargeListener<AdInfo>(){

            @Override
            public void adSuccess(AdInfo info) {
                LogUtils.e("adSuccess");
            }

            @Override
            public void adError(AdInfo info, int errorCode, String errorMsg) {
                LogUtils.e("adError");
            }

            @Override
            public void adExposed(AdInfo info) {
                LogUtils.e("adExposed");
            }

            @Override
            public void adClicked(AdInfo info) {
                LogUtils.e("adClicked");
            }
        });
        mContainer.removeAllViews();
        mContainer.addView(view);
    }

    private void cshBindView(MidasSelfRenderAd midasSelfRenderAd) {
        ImageView mIcon;
        Button mCreativeButton;
        TextView mTitle;
        TextView mDescription;
        TextView mSource;
        Button mStopButton;
        Button mRemoveButton;
        ImageView mLargeImage;
        ViewGroup container;

        View view = LayoutInflater.from(this).inflate(R.layout.news_item_csj, null);


        container = view.findViewById(R.id.container);
        mTitle = (TextView) view.findViewById(R.id.tv_listitem_ad_title);
        mDescription = (TextView) view.findViewById(R.id.tv_listitem_ad_desc);
        mSource = (TextView) view.findViewById(R.id.tv_listitem_ad_source);
        mLargeImage = (ImageView) view.findViewById(R.id.iv_listitem_image);
        mIcon = (ImageView) view.findViewById(R.id.iv_listitem_icon);
        mCreativeButton = (Button) view.findViewById(R.id.btn_listitem_creative);
        mStopButton = (Button) view.findViewById(R.id.btn_listitem_stop);
        mRemoveButton = (Button) view.findViewById(R.id.btn_listitem_remove);

        mContainer.removeAllViews();
        mContainer.addView(container);

        // TODO: 2019/12/24 app 自己渲染

        //可以被点击的view, 也可以把convertView放进来意味item可被点击
        List<View> clickViewList = new ArrayList<>();
        clickViewList.add(view);
        //触发创意广告的view（点击下载或拨打电话）
        List<View> creativeViewList = new ArrayList<>();
        creativeViewList.add(mCreativeButton);
        //如果需要点击图文区域也能进行下载或者拨打电话动作，请将图文区域的view传入
//            creativeViewList.add(convertView);
        //重要! 这个涉及到广告计费，必须正确调用。convertView必须使用ViewGroup。

        midasSelfRenderAd.bindViewToAdListener(this, container, clickViewList, creativeViewList, new AdOutChargeListener<AdInfo>(){

            @Override
            public void adSuccess(AdInfo info) {
                LogUtils.e("adSuccess");
            }

            @Override
            public void adError(AdInfo info, int errorCode, String errorMsg) {
                LogUtils.e("adError");
            }

            @Override
            public void adExposed(AdInfo info) {
                LogUtils.e("adExposed");
            }

            @Override
            public void adClicked(AdInfo info) {
                LogUtils.e("adClicked");
            }
        });
        mTitle.setText(midasSelfRenderAd.getTitle());
        mDescription.setText(midasSelfRenderAd.getDescription());
        mSource.setText(midasSelfRenderAd.getSource() == null ? "广告来源" : midasSelfRenderAd.getSource());
        String iconUrl = midasSelfRenderAd.getIconUrl();
        if (!TextUtils.isEmpty(iconUrl)) {
            Glide.with(this).load(iconUrl).into(mIcon);
        }
        List<String> imgs = midasSelfRenderAd.getImageList();
        if (imgs != null && imgs.size() > 0) {
            Glide.with(this).load(imgs.get(0)).into(mLargeImage);
        }
//        Button adCreativeButton = adViewHolder.mCreativeButton;
//        switch (ad.getInteractionType()) {
//            case TTAdConstant.INTERACTION_TYPE_DOWNLOAD:
//                //如果初始化ttAdManager.createAdNative(getApplicationContext())没有传入activity 则需要在此传activity，否则影响使用Dislike逻辑
//                if (mContext instanceof Activity) {
//                    ad.setActivityForDownloadApp((Activity) mContext);
//                }
//                adCreativeButton.setVisibility(View.VISIBLE);
//                adViewHolder.mStopButton.setVisibility(View.VISIBLE);
//                adViewHolder.mRemoveButton.setVisibility(View.VISIBLE);
//                bindDownloadListener(adCreativeButton, adViewHolder, ad);
//                //绑定下载状态控制器
//                bindDownLoadStatusController(adViewHolder, ad);
//                break;
//            case TTAdConstant.INTERACTION_TYPE_DIAL:
//                adCreativeButton.setVisibility(View.VISIBLE);
//                adCreativeButton.setText("立即拨打");
//                adViewHolder.mStopButton.setVisibility(View.GONE);
//                adViewHolder.mRemoveButton.setVisibility(View.GONE);
//                break;
//            case TTAdConstant.INTERACTION_TYPE_LANDING_PAGE:
//            case TTAdConstant.INTERACTION_TYPE_BROWSER:
////                    adCreativeButton.setVisibility(View.GONE);
//                adCreativeButton.setVisibility(View.VISIBLE);
//                adCreativeButton.setText("查看详情");
//                adViewHolder.mStopButton.setVisibility(View.GONE);
//                adViewHolder.mRemoveButton.setVisibility(View.GONE);
//                break;
//            default:
//                adCreativeButton.setVisibility(View.GONE);
//                adViewHolder.mStopButton.setVisibility(View.GONE);
//                adViewHolder.mRemoveButton.setVisibility(View.GONE);
//                TToast.show(mContext, "交互类型异常");
//        }
    }
}
