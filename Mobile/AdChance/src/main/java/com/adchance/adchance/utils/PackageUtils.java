package com.adchance.adchance.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.Collection;

/**
 * Created by alexandre on 19/10/13.
 */
public class PackageUtils {

    public static Collection getInstalledApps(PackageManager packageManager) {
        Collection<ApplicationInfo> list = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        return list;
    }

}
