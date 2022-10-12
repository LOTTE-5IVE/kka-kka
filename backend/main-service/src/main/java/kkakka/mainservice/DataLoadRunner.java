package kkakka.mainservice;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
@Profile({"local", "dev"})
@RequiredArgsConstructor
public class DataLoadRunner implements CommandLineRunner {

    private final DataLoader dataLoader;

    @Override
    public void run(String... args) {
        final Logger logger = LoggerFactory.getLogger(DataLoadRunner.class);
        String line;
        String CSV_DELIMITER = ",";

        List<String[]> rows = new ArrayList<>();

        try (
                final FileInputStream fileInputStream = new FileInputStream(
                        new ClassPathResource("/static/product-data.csv").getFile());
                final BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(fileInputStream))
        ) {
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
