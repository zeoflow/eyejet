package com.zeoflow.eyejet;

import com.zeoflow.eyejet.arch.core.internal.FastSafeIterableMap;

import java.lang.annotation.Annotation;
import java.util.Stack;

public class EyejetStore<T>
{

    private final FastSafeIterableMap<Annotation, FastSafeIterableMap<String, EyejetField<T>>> caches = new FastSafeIterableMap<>();
    private final FastSafeIterableMap<Annotation, Stack<EyejetLifecycleObserver>> observers = new FastSafeIterableMap<>();

    public EyejetStore()
    {

    }

    /**
     * initializes the [EyejetField] hash map by a scope.
     */
    public void initializeFieldScopeMap(Annotation annotation)
    {
        synchronized (this)
        {
            this.caches.putIfAbsent(annotation, new FastSafeIterableMap<>());
        }
    }

    /**
     * gets [EyejetField] hash map by a scope.
     */
    public FastSafeIterableMap<String, EyejetField<T>> getFieldScopeMap(Annotation annotation)
    {
        synchronized (this)
        {
            return caches.get(annotation).getValue();
        }
    }

    /**
     * initializes the [EyejetLifecycleObserver] stack by a scope.
     */
    public void initializeObserverScopeStack(Annotation annotation)
    {
        synchronized (this)
        {
            this.observers.putIfAbsent(annotation, new Stack<>());
        }
    }

    /**
     * checks a [EyejetLifecycleObserver] is already cached or not.
     */
    public boolean checkContainsEyejetLifecycleObserver(Annotation annotation, String lifecycleOwner)
    {
        synchronized (this)
        {
            if (!this.observers.contains(annotation))
            {
                return false;
            }
            boolean contained = false;
            for (EyejetLifecycleObserver observer : this.observers.get(annotation).getValue())
            {
                if (observer.lifecycleOwner.equals(lifecycleOwner))
                {
                    contained = true;
                    break;
                }
            }
            return contained;
        }
    }

    /**
     * gets [EyejetLifecycleObserver] stack by a scope.
     */
    public Stack<EyejetLifecycleObserver> getLifecycleObserverStack(Annotation annotation)
    {
        synchronized (this)
        {
            return observers.get(annotation).getValue();
        }
    }

    /**
     * clears a field on the scope cache storage.
     */
    public void clearField(Annotation annotation, String key)
    {
        synchronized (this)
        {
            this.getFieldScopeMap(annotation).remove(key);
        }
    }

}
