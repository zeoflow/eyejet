package com.zeoflow.eyejet;

import androidx.lifecycle.LifecycleObserver;

import java.lang.annotation.Annotation;

/**
 * EyejetLifecycleObserver is an onDestroy observer class.
 */
public class EyejetLifecycleObserver implements LifecycleObserver
{
    public Annotation annotation;
    public String lifecycleOwner;

    public EyejetLifecycleObserver(Annotation annotation, String lifecycleOwner)
    {
        this.annotation = annotation;
        this.lifecycleOwner = lifecycleOwner;
    }

    public static EyejetLifecycleObserver init(Annotation annotation, String lifecycleOwner)
    {
        return new EyejetLifecycleObserver(annotation, lifecycleOwner);
    }
    /**
     * when lifecycle owner is destroyed, the value data
     * will be cleared on the lifecycle stack on the [EyejetStore].
     */
//    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
//    public void onDestroy() {
//        Eyejet.onDestroyObserver(annotation);
//    }
}
