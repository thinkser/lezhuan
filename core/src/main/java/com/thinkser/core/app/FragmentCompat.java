package com.thinkser.core.app;

import android.app.Fragment;
import android.os.Build.VERSION;

public class FragmentCompat {
    static final FragmentCompat.FragmentCompatImpl IMPL;

    public FragmentCompat() {
    }

    public static void setMenuVisibility(Fragment f, boolean visible) {
        IMPL.setMenuVisibility(f, visible);
    }

    public static void setUserVisibleHint(Fragment f, boolean deferStart) {
        IMPL.setUserVisibleHint(f, deferStart);
    }

    static {
        if (VERSION.SDK_INT >= 15) {
            IMPL = new FragmentCompat.ICSMR1FragmentCompatImpl();
        } else if (VERSION.SDK_INT >= 14) {
            IMPL = new FragmentCompat.ICSFragmentCompatImpl();
        } else {
            IMPL = new FragmentCompat.BaseFragmentCompatImpl();
        }

    }

    static class ICSMR1FragmentCompatImpl extends FragmentCompat.ICSFragmentCompatImpl {
        ICSMR1FragmentCompatImpl() {
        }

        public void setUserVisibleHint(Fragment f, boolean deferStart) {
            FragmentCompatICSMR1.setUserVisibleHint(f, deferStart);
        }
    }

    static class ICSFragmentCompatImpl extends FragmentCompat.BaseFragmentCompatImpl {
        ICSFragmentCompatImpl() {
        }

        public void setMenuVisibility(Fragment f, boolean visible) {
            FragmentCompatICS.setMenuVisibility(f, visible);
        }
    }

    static class BaseFragmentCompatImpl implements FragmentCompat.FragmentCompatImpl {
        BaseFragmentCompatImpl() {
        }

        public void setMenuVisibility(Fragment f, boolean visible) {
        }

        public void setUserVisibleHint(Fragment f, boolean deferStart) {
        }
    }

    interface FragmentCompatImpl {
        void setMenuVisibility(Fragment var1, boolean var2);

        void setUserVisibleHint(Fragment var1, boolean var2);
    }
}
