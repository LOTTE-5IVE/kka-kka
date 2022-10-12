package kkakka.mainservice;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile({"local", "dev"})
@RequiredArgsConstructor
public class DataLoadRunner implements CommandLineRunner {

    private final DataLoader dataLoader;

    @Override
    public void run(String... args) throws IOException {
        final Logger logger = LoggerFactory.getLogger(DataLoadRunner.class);
        String line;
        String CSV_DELIMITER = ",";

        List<String[]> rows = new ArrayList<>();

        ClassPathResource classPathResource = new ClassPathResource("/static/product-data.csv");
        if (classPathResource.exists() == false) {
            throw new IllegalArgumentException();
        }
        try (InputStream is = new BufferedInputStream(classPathResource.getInputStream())) {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            while ((line = bufferedReader.readLine()) != null) {
                final String[] row = line.split(CSV_DELIMITER);
                rows.add(row);
            }

            dataLoader.saveData(rows);
        } catch (Exception e) {
            logger.error("===============================");
            logger.error("데이터 로드 중 에러 발생");
            logger.error("===============================");
        }
    }
}
