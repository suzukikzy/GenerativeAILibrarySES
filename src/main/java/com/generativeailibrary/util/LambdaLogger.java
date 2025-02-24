package main.java.com.generativeailibrary.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.amazonaws.services.lambda.runtime.logging.LogLevel;

import main.java.com.generativeailibrary.unit.OriginalDateTime;

/**
 * Log出力クラス.
 *
 * @author 鈴木一矢
 *
 */
public class LambdaLogger {
    /**
     * 引数で受け取ったメッセージをコンソールにDEBUGレベルで出力する
     * @param message
     */
    public static void debugLogMessage(String message) {
        logMessage(LogLevel.DEBUG.name(), message);
    }
    /**
     * 引数で受け取ったメッセージをコンソールにINFOレベルで出力する
     * @param message
     */
    public static void infoLogMessage(String message) {
        logMessage(LogLevel.INFO.name() + " ", message);
    }
    /**
     * 引数で受け取ったメッセージをコンソールにWARNレベルで出力する
     * @param message
     */
    public static void warnLogMessage(String message) {
        logMessage(LogLevel.WARN.name(), message);
    }
    /**
     * 引数で受け取ったメッセージをコンソールにERRORレベルで出力する
     * @param message
     */
    public static void errorLogMessage(String message) {
        logMessage(LogLevel.ERROR.name(), message);
    }
    /**
     * 引数で受け取ったオブジェクトのフィールド名と値をコンソールにDEBUGレベルで出力する
     * @param object
     */
    public static void debug(Object object) {
        log(LogLevel.DEBUG.name(), object);
    }
    /**
     * 引数で受け取ったオブジェクトのフィールド名と値をコンソールにINFOレベルで出力する
     * @param object
     */
    public static void info(Object object) {
        log(LogLevel.INFO.name() + " ", object);
    }
    /**
     * 引数で受け取ったオブジェクトのフィールド名と値をコンソールにWARNレベルで出力する
     * @param object
     */
    public static void warn(Object object) {
        log(LogLevel.WARN.name(), object);
    }
    /**
     * 引数で受け取ったオブジェクトのフィールド名と値をコンソールにERRORレベルで出力する
     * @param object
     */
    public static void error(Object object) {
        log(LogLevel.ERROR.name(), object);
    }

    // ==================================
    //	内部利用メソッド
    // ==================================
    /**
     * オブジェクトのフィールドをキーバリュー形式でStringにし表示する
     * 
     * @param level ログレベル
     * @param object オブジェクト
     */
    private static void log(String level, Object object) {
        StringBuilder stringBuilder = new StringBuilder();
        // 現在日時をコンソールにログ表示
        stringBuilder.append(object.getClass().getSimpleName()).append("\n{\n");
        // objectのクラスとそのすべての親クラスに対して
        Class<?> objClass = object.getClass();
        boolean isFirstField = true;
        while (objClass != null && objClass != Object.class) { // Objectクラスに達するまでループ
            Field[] fields = objClass.getDeclaredFields();
            for (Field field : fields) {
                // staticフィールドでない場合のみ処理
                if (!Modifier.isStatic(field.getModifiers())) {
                    field.setAccessible(true); // プライベートフィールドへのアクセスを許可
                    try {
                        Object value = field.get(object);
                        if (value != null) {
                            if (!isFirstField) {
                                stringBuilder.append(",\n");
                            } else {
                                isFirstField = false;
                            }
                            stringBuilder.append("    ").append(field.getName()).append(": ").append(value);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            objClass = objClass.getSuperclass(); // 親クラスに移動
        }
        stringBuilder.append("\n}");
        StringBuilder logBuilder = new StringBuilder("[" + level +"]" + new OriginalDateTime().toString() + ": " + stringBuilder.toString());
        System.out.println(logBuilder);
    }

    /**
     * ログメッセージをコンソールに出力する
     * 
     * @param level ログレベル
     * @param message メッセージ
     */
    private static void logMessage(String level, String message) {
        StringBuilder logBuilder = new StringBuilder("[" + level +"]" + new OriginalDateTime().toString() + ": " + message);
        System.out.println(logBuilder);
    }
}
