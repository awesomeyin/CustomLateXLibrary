package com.example.customlatexlibrary;

import androidx.annotation.Nullable;

import java.io.Closeable;

/**
 * Created by hao on 2017/9/25.
 */

public class IOUtil {
    /**
     * 关闭资源。
     *
     * @param closeable 资源
     * @return 若成功关闭资源则返回 true，否则返回 false
     */
    public static boolean close(@Nullable Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 关闭资源。
     *
     * @param closeable 资源
     */
    public static void closeQuietly(@Nullable Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
