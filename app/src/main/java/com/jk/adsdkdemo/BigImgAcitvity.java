package com.jk.adsdkdemo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.comm.jksdk.MidasAdSdk;
import com.comm.jksdk.ad.entity.AdInfo;
import com.comm.jksdk.ad.entity.MidasSelfRenderAd;
import com.comm.jksdk.ad.listener.SelfRenderAdListener;
import com.comm.jksdk.ad.listener.SelfRenderChargeListener;
import com.comm.jksdk.utils.CollectionUtils;
import com.jk.adsdkdemo.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
  *
  * @ProjectName:    ${PROJECT_NAME}
  * @Package:        ${PACKAGE_NAME}
  * @ClassName:      ${NAME}
  * @Description:  大图类广告
  * @Author:         fanhailong
  * @CreateDate:     ${DATE} ${TIME}
  * @UpdateUser:     更新者：
  * @UpdateDate:     ${DATE} ${TIME}
  * @UpdateRemark:   更新说明：
  * @Version:        1.0
 */
public class BigImgAcitvity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = BigImgAcitvity.class.getSimpleName();

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
        spinner = findViewById(R.id.spinner);
        preloadBt = findViewById(R.id.button_preloading_ad);
        preloadBt.setOnClickListener(this);

        String[] ctype = new String[]{"success_page_ad_1", "success_page_ad_2", "success_page_ad_3", "newlist_1_1", "homepage_ad_1","homepage_ad_2", "lock_screen_advertising", "external_advertising_ad_1", "external_big_image_02"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ctype);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    View adView;
    String position = null;
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
                MidasAdSdk.getAdsManger().loadMidasSelfRenderAd(this, position, new SelfRenderAdListener<AdInfo>() {

                    @Override
                    public void adSuccess(AdInfo info) {
                        MidasSelfRenderAd midasSelfRenderAd = (MidasSelfRenderAd) info.getMidasAd();
                        bindView(midasSelfRenderAd);
                    }

                    @Override
                    public void adError(AdInfo info, int errorCode, String errorMsg) {
                        LogUtils.e(TAG, "DEMO>>>adError： "+errorMsg);
                    }
                });
                break;
        }
    }

    private void bindView(MidasSelfRenderAd midasSelfRenderAd) {
        ImageView mIcon;
        Button mCreativeButton;
        TextView mTitle;
        TextView mDescription;
        TextView mSource;
        Button mStopButton;
        Button mRemoveButton;
        ImageView mLargeImage;
        ViewGroup container;

        View view  = LayoutInflater.from(this).inflate(R.layout.news_item, null);


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

        midasSelfRenderAd.setAdListener(new SelfRenderChargeListener<AdInfo>(){

            @Override
            public ViewGroup getViewGroup() {
                return container;
            }

            @Override
            public List<View> getClickViewList() {
                return clickViewList;
            }

            @Override
            public List<View> getCreativeViewList() {
                return creativeViewList;
            }

            @Override
            public void adCreativeClick(AdInfo info) {
                LogUtils.e("adCreativeClick");
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
        String imgeUrl = midasSelfRenderAd.getImageUrl();
        if (!TextUtils.isEmpty(imgeUrl)) {
            Glide.with(this).load(imgeUrl).into(mIcon);
        }
        List<String> imgs = midasSelfRenderAd.getImageList();
        if (!CollectionUtils.isEmpty(imgs)) {
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
