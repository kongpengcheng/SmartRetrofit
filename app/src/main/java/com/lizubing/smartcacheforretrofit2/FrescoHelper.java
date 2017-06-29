package com.lizubing.smartcacheforretrofit2;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
/**
 * Created by Harry.Kong.
 * Time 2017/5/5.
 * Email kongpengcheng@aliyun.com.
 * Description:
 */
public class FrescoHelper {
	//默认加载图片和失败图片
  public static Drawable sPlaceholderDrawable;
  public static Drawable sErrorDrawable;
	/**
	 * 图像选项类
	 * @param isRound 是否圆角
	 * @param radius  圆角角度
	 * @return
	 */
	public static GenericDraweeHierarchy getImageViewHierarchy(Resources resources, boolean isRound, float radius) {
		GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(resources);
		builder.setFailureImage(resources.getDrawable(R.mipmap.ic_launcher));
		builder.setPlaceholderImage(resources.getDrawable(R.mipmap.ic_launcher));
		builder.setFadeDuration(300);
		if (isRound) {
			RoundingParams roundingParams = RoundingParams.fromCornersRadius(radius);
			builder.setRoundingParams(roundingParams);
		}
		return builder.build();
	}
	/**
	 * 图像选项类
	 * @param resources  Resources
	 * @param isRound 是否圆角
	 * @param radius 圆角角度
	 */
	public static GenericDraweeHierarchy getImageProgHierarchy(Resources resources, boolean isRound, float radius) {
		GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(resources);
		builder.setFailureImage(resources.getDrawable(R.mipmap.ic_launcher));
		builder.setPlaceholderImage(resources.getDrawable(R.mipmap.ic_launcher));
//		builder.setProgressBarImage(new CustomProgressbarDrawable());
		builder.setFadeDuration(300);
		if (isRound) {
			RoundingParams roundingParams = RoundingParams.fromCornersRadius(radius);
			builder.setRoundingParams(roundingParams);
		}
		return builder.build();
	}

	/**
	 * 图像选项类
	 * @param uri 图片路径
	 * @param oldController DraweeView.getoldcontroller
	 * @param controllerListener 监听
	 * @return
	 */
	public static DraweeController getImageViewController(String uri, DraweeController oldController,
			ControllerListener<ImageInfo> controllerListener) {
		PipelineDraweeControllerBuilder builder = Fresco.newDraweeControllerBuilder();
		if (!TextUtils.isEmpty(uri)) {
//			Logger.d("StringUtils.utf8Encode(uri)"+StringUtils.utf8Encode(uri));
			builder.setUri(Uri.parse(uri));
		}
		if (oldController != null) {
			builder.setOldController(oldController);
		}
		if (controllerListener != null) {
			builder.setControllerListener(controllerListener);
		}
		return builder.build();
	}

	/**
	 * 加载图片
	 * @param draweeView SimpleDraweeView
	 * @param uri  地址url
	 * @param isRound 是否是圆角
	 * @param radius 圆角的角弧度
	 */
	public static void displayImageview(SimpleDraweeView draweeView, String uri,boolean isRound, float radius) {
		draweeView.setHierarchy(getImageViewHierarchy(MyApplication.getInstance().getResources(), isRound, radius));
		draweeView.setController(getImageViewController(uri, draweeView.getController(), null));
	}
}
