package configuration;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import controllers.LoginController;
import models.LobbyModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Database {


    private static final String PRIVATEKEYLOCATION = "src/main/resources/json/iipsene-iipsene-firebase-adminsdk-r6i1a-e26d7fdf16.json";
    private static final String DATABASEURL = "iipsene-iipsene.iam.gserviceaccount.com";

    private Firestore db;


    public Database() {

        try {
            FileInputStream serviceAccount =
                    new FileInputStream(PRIVATEKEYLOCATION);


            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(DATABASEURL)
                    .build();

            FirebaseApp.initializeApp(options);
            this.db = FirestoreClient.getFirestore();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Firestore getFirestoreDatabase() {
        return this.db;
    }

    public DocumentSnapshot getFirebaseDocument(String firebaseEl ) throws ExecutionException, InterruptedException,NullPointerException {
        try {
            System.out.println("help me");
            DocumentReference docRef = LoginController.database.getFirestoreDatabase().collection(LobbyModel.lobbycode).document(firebaseEl);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();
            return document;
        }
        catch (NullPointerException notvalue){
            notvalue.printStackTrace();
        }
        return null;
    }

}