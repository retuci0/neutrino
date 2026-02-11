package com.github.retucio.neutrino;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class EventBus {

    private final Map<Class<?>, List<ListenerMethod>> listeners = new HashMap<>();

    public void subscribe(Object listener) {
        Class<?> targetClass;

        if (listener instanceof Class<?> clazz) {
            targetClass = clazz;
            listener = null;
        } else {
            targetClass = listener.getClass();
        }

        for (Method method : targetClass.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(EventListener.class)) continue;
            if (method.getParameterCount() != 1) continue;

            Class<?> eventType = method.getParameterTypes()[0];
            method.setAccessible(true);

            if (listener == null && !Modifier.isStatic(method.getModifiers())) continue;

            int priority =  method.getDeclaredAnnotation(EventListener.class).priority();

            listeners.computeIfAbsent(eventType, k -> new ArrayList<>())
                    .add(new ListenerMethod(listener, method, priority));

            for (List<ListenerMethod> list : listeners.values()) {
                list.sort((a, b)
                        -> Integer.compare(b.priority(), a.priority()));
            }
        }
    }

    public void unsubscribe(Object listener) {
        for (List<ListenerMethod> list : listeners.values()) {
            list.removeIf(lm -> lm.instance == listener);
        }
    }

    public <T extends Event> T post(T event) {
        List<ListenerMethod> list = listeners.get(event.getClass());
        if (list != null) {
            for (ListenerMethod lm : new ArrayList<>(list)) {
                try {
                    lm.method.invoke(lm.instance, event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return event;
    }

    public boolean isSubscribed(Object listener) {
        for (List<ListenerMethod> list : listeners.values()) {
            for (ListenerMethod lm : list) {
                if (lm.instance == listener) {
                    return true;
                }
            }
        }
        return false;
    }

    private record ListenerMethod(
            Object instance,
            Method method,
            int priority
    ) {}
}