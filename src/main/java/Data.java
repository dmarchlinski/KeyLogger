import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class Data {

    Data(String log) throws IOException {
        Upload(log);
    }

    private static void Upload(String log) throws IOException {

        // Access database
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(new FileInputStream("Key.json")))
                .build();

        FirebaseApp.initializeApp(options);

        Firestore db = FirestoreClient.getFirestore();

        // Upload log to database
        Map<String, String> data = new HashMap<>();
        data.put("log", log);
        String hex = String.format("%x", (int) (Math.random() * 2147483647));
        ApiFuture<WriteResult> result = db.collection("user").document("data").update(hex, data);
        System.out.println(result);
    }
}