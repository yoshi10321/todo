package com.github.yoshi10321.todoapp.event;

import com.squareup.otto.Bus;

/**
 * Created by a12711 on 2015/05/12.
 */
public class BusHolder {

    static Bus mBus = new Bus();

    public static Bus get() {
        return mBus;
    }
}
