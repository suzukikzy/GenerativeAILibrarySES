package main.java.com.generativeailibrary.api;

import java.io.IOException;

/**
 * 【SES AIアシスタント】
 * エンベディング処理リスナー.
 *
 * @author 鈴木一矢.
 *
 */
public interface EmbeddingProcessListener {
    /**
     * 引数に入力された文字列をエンベディング処理しベクトル値を返却します.
     *
     * @param input 入力
     * @return ベクトル値
     * @throws IOException 
     * @throws RuntimeException
     */
    public double[] embedding(final String inputString) throws IOException, RuntimeException;
}
