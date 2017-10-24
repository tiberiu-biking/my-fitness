package com.tpo.strava.gui.event;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import com.tpo.strava.gui.main.MyFitnessUI;

public class DashboardEventBus implements SubscriberExceptionHandler {

    private final EventBus eventBus = new EventBus(this);

    public static void post(final Object event) {
        MyFitnessUI.getDashboardEventbus().eventBus.post(event);
    }

    public static void register(final Object object) {
        MyFitnessUI.getDashboardEventbus().eventBus.register(object);
    }

    public static void unregister(final Object object) {
        MyFitnessUI.getDashboardEventbus().eventBus.unregister(object);
    }

    @Override
    public final void handleException(final Throwable exception,
                                      final SubscriberExceptionContext context) {
        exception.printStackTrace();
    }
}
