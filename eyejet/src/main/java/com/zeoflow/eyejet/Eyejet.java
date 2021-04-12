package com.zeoflow.eyejet;

import androidx.annotation.MainThread;
import androidx.lifecycle.LifecycleOwner;

import com.zeoflow.eyejet.annotation.EyejetScope;
import com.zeoflow.eyejet.annotation.ShareProperty;
import com.zeoflow.eyejet.arch.core.internal.FastSafeIterableMap;
import com.zeoflow.eyejet.annotation.ShareType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Eyejet is scoped data holder with custom scopes that are lifecycle aware.
 * <p>
 * Simplifies sharing fields and communication between
 * Android components with custom scopes that are lifecycle aware.
 * <p>
 * Making easier to communicate and data flow with each other
 * component like Activity, Fragment, Services, etc.
 * <p>
 * And using custom scopes that are lifecycle aware makes
 * developers can designate scoped data holder on their taste.
 */
public class Eyejet
{

    @SuppressWarnings("rawtypes")
    public static EyejetStore store = new EyejetStore();

    /**
     * Eyejet synchronizes the EyejetField that has the same scope and same key.
     * Also pushes a lifecycleOwner to the Eyejet lifecycle stack.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T extends LifecycleOwner> void shareLifecycle(Object scopeOwner, T lifecycleOwner)
    {

        for (Annotation annotation : scopeOwner.getClass().getAnnotations())
        {
            if (!checkAnnotatedEyejetScope(annotation)) continue;

            for (Field field : scopeOwner.getClass().getDeclaredFields())
            {
                if (EyejetField.class.isAssignableFrom(field.getType()))
                {
                    store.initializeFieldScopeMap(annotation);

                    ShareProperty shareProperty = field.getAnnotation(ShareProperty.class);
                    if (shareProperty == null)
                    {
//                        throw new IllegalArgumentException("The EyejetField Type (" + field.getName() +
//                                ") should have a @SharedProperty annotation inside " + scopeOwner.getClass().getName() + ".");
                        continue;
                    }
                    if (!Modifier.isPublic(field.getModifiers()))
                    {
                        throw new IllegalArgumentException("The EyejetField Type (" + field.getName() +
                                ") should be public inside " + scopeOwner.getClass().getName() + ".");
                    }

                    String value = shareProperty.value();
                    FastSafeIterableMap<String, EyejetField<T>> annotationsScope = store.getFieldScopeMap(annotation);
                    if (annotationsScope.contains(value))
                    {
                        EyejetField eyejetField = new EyejetField<>(annotationsScope.get(value).getValue().getValue());
                        eyejetField.annotation = annotation;
                        eyejetField.key = value;
                        eyejetField.autoClear = shareProperty.shareType()[0];
                        eyejetField.initialized = true;
                        lifecycleOwner.getLifecycle().addObserver(eyejetField);
                        field.setAccessible(true);
                        try
                        {
                            field.set(scopeOwner, eyejetField);
                        } catch (IllegalAccessException e)
                        {
                            e.printStackTrace();
                        }
                        annotationsScope.putIfAbsent(value, eyejetField);
                        if (shareProperty.shareType()[0] == ShareType.ON_FIRST_USE)
                        {
                            store.clearField(annotation, value);
                            System.out.println("heerWeAre");
                        }
                    } else
                    {
                        try
                        {
                            EyejetField<T> declaredField = (EyejetField<T>) field.get(scopeOwner);
                            assert declaredField != null;
                            lifecycleOwner.getLifecycle().addObserver(declaredField);
                            declaredField.annotation = annotation;
                            declaredField.key = value;
                            declaredField.autoClear = shareProperty.shareType()[0];
                            declaredField.initialized = true;
                            annotationsScope.putIfAbsent(value, declaredField);
                        } catch (IllegalAccessException e)
                        {
                            e.printStackTrace();
                        }
                    }

                    store.initializeObserverScopeStack(annotation);

                    EyejetLifecycleObserver observer = EyejetLifecycleObserver.init(
                            annotation,
                            lifecycleOwner.toString()
                    );
                    if (!store.checkContainsEyejetLifecycleObserver(
                            annotation,
                            lifecycleOwner.toString()
                    )
                    )
                    {
                        lifecycleOwner.getLifecycle().addObserver(observer);
                        store.getLifecycleObserverStack(annotation).push(observer);
                    }

                }
            }
        }
    }

    /**
     * checks the ScopeOwner class is annotated @EyejetScope annotation.
     */
    public static boolean checkAnnotatedEyejetScope(Annotation annotation)
    {
        for (Annotation s : annotation.annotationType().getAnnotations())
        {
            if (s.annotationType().getName().equals(EyejetScope.class.getName()))
            {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @MainThread
    public static <T> void updateValue(EyejetField<T> eyejetField)
    {
        FastSafeIterableMap<String, EyejetField<T>> fieldScope = store.getFieldScopeMap(eyejetField.annotation);
        fieldScope.remove(eyejetField.key);
        fieldScope.putIfAbsent(eyejetField.key, eyejetField);
    }
}
