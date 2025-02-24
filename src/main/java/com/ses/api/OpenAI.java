package main.java.com.ses.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 【SES AIアシスタント】
 * OpenAIクラス.
 *
 * @author 鈴木一矢.
 *
 */
public class OpenAI implements EmbeddingProcessListener {
    /**
     * OpenAI APIのエンベディング処理のエンドポイント.
     */
    private static final String EMBEDDING_API_URL = "https://api.openai.com/v1/embeddings";

    /**
     * OpenAIのAPIキー.
     */
    private final String apiKey;

    public OpenAI(final String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public double[] embedding(final String inputString) throws IOException {
        // OpenAIのエンベディング処理APIを実行する
        URL url = new URL(EMBEDDING_API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + this.apiKey);
        conn.setDoOutput(true);

        // JSON リクエストボディ
        String jsonBody = "{\"input\": \"" + inputString + "\", \"model\": \"text-embedding-ada-002\"}";
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // レスポンスを読み取る
        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HTTP ERROR CODE: " + responseCode);
        }

        StringBuilder response;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
        }

        conn.disconnect();

        // JSON をパースしてエンベディングを取得
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(response.toString());

        // "data" -> "embedding" の順にアクセスしてエンベディングを取得
        JsonNode embeddingArray = jsonResponse.get("data").get(0).get("embedding");

        // double[] 配列に変換
        double[] vactorValue = new double[embeddingArray.size()];
        for (int i = 0; i < embeddingArray.size(); i++) {
            vactorValue[i] = embeddingArray.get(i).asDouble();
        }
        return vactorValue;
    }
}
