package test.java.com.ses.unit;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import main.java.com.ses.api.OpenAI;
import main.java.com.ses.unit.Vector;

class VectorTest {
    @Test
    void test() throws IOException {
        // OpenAIクライアントを生成.
        // テストする際は実際のAPIキーを入力して実行すること。また、APIキーはコミットしないこと！
        OpenAI openAiClient = new OpenAI("dummy_api_key");
        // エンベディング処理を実行し、結果を返却する.
        Vector vector = new Vector(openAiClient);
        vector.setRawString("こんにちは");
        vector.embedding();
        System.out.println(vector);
    }
}
