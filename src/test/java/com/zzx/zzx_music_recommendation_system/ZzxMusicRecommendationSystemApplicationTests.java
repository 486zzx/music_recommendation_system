package com.zzx.zzx_music_recommendation_system;

import com.github.chen0040.tensorflow.classifiers.models.cifar10.Cifar10AudioClassifier;
import com.github.chen0040.tensorflow.classifiers.utils.FileUtils;
import net.minidev.json.writer.JsonReader;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class ZzxMusicRecommendationSystemApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) throws IOException {
        Cifar10AudioClassifier classifier = new Cifar10AudioClassifier();
        classifier.load_model();

        // List<String> paths = FileUtils.getAudioFiles();
        List<String> paths= FileUtils.getAudioFiles();

        Collections.shuffle(paths);

        for (String path : paths) {
            System.out.println("Predicting " + path + " ...");
            File f = new File(path);
            String label = classifier.predict_audio(f);

            System.out.println("Predicted: " + label);
        }
    }

}
