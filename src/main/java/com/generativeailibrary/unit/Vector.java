package main.java.com.generativeailibrary.unit;

import java.io.IOException;

import main.java.com.generativeailibrary.api.EmbeddingProcessListener;

/**
 * 【SES AIアシスタント】
 * ベクトルクラス.
 *
 * @author 鈴木一矢.
 *
 */
public class Vector {
    /**
     * エンベディング処理の対象となる文字列.
     */
    private String rawString;
    /**
     * このベクトルの値.
     */
    private double[] value;
    /**
     * エンベディング処理リスナー.
     */
    private EmbeddingProcessListener embeddingProcessListener;

    /**
     * コンストラクタ.
     *
     * @param embeddingProcessListener エンベディング処理リスナー
     */
    public Vector(final EmbeddingProcessListener embeddingProcessListener) {
        this.embeddingProcessListener = embeddingProcessListener;
    }

    /**
     * このクラスが持つ文字列をエンベディング処理しベクトル値として格納します.
     *
     * @throws IOException
     * @throws RuntimeException
     */
    public void embedding() throws IOException, RuntimeException {
        if (this.embeddingProcessListener != null
                && this.rawString != null
                && !"".equals(this.rawString)) {
            this.value = this.embeddingProcessListener.embedding(this.rawString);
        }
    }
    /**
     * このクラスが持つ文字列を返却します.
     *
     * @return rawString
     */
    public String getRawString() {
        return rawString;
    }
    /**
     * このクラスにエンベディング処理の対象となる文字列をセットします.
     *
     * @param rawString 対象の文字列
     */
    public void setRawString(String rawString) {
        this.rawString = rawString;
    }
    /**
     * このクラスが持つベクトル値を返却します.
     *
     * @return ベクトル値
     */
    public double[] getValue() {
        return value;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (double value : this.value) {
            i++;
            builder.append(Double.toString(value));
            if (i < this.value.length) {
                builder.append(",");
            }
        }
        return builder.toString();
    }
}
