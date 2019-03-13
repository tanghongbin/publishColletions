package com.android_base.core.common.net.entry;

import java.io.Serializable;

/**
 *
 */
public class Paramter<KEY,VALUE> implements Serializable{
    private KEY key;
    private VALUE value;

    public Paramter() {
    }

    public Paramter(KEY key, VALUE value) {
        this.key = key;
        this.value = value;
    }

    public KEY getKey() {
        return key;
    }

    public void setKey(KEY key) {
        this.key = key;
    }

    public VALUE getValue() {
        return value;
    }

    public void setValue(VALUE value) {
        this.value = value;
    }
}
