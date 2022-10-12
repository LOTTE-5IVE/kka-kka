package kkakka.mainservice.auth.infrastructure.google.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.function.Function;

public class BirthConverter {

    public static String convertToAgeGroup(String birthYear) {
        try {
            final int year = LocalDateTime.now().getYear();
            final int age = year - Integer.parseInt(birthYear) + 1;
            return AgeGroup.getAgeGroup(age);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private enum AgeGroup {

        MORE_THAN_ZERO("0~9", (age) -> age < 10 && age >= 0),
        MORE_THAN_TEN("10~19", (age) -> age < 20 && age >= 10),
        MORE_THAN_TWENTY("20~29", (age) -> age < 30 && age >= 20),
        MORE_THAN_THIRTY("30~39", (age) -> age < 40 && age >= 30),
        MORE_THAN_FORTY("40~49", (age) -> age < 50 && age >= 40),
        MORE_THAN_FIFTY("50~59", (age) -> age < 60 && age >= 50),
        MORE_THAN_SIXTY("60~69", (age) -> age < 70 && age >= 60),
        MORE_THAN_SEVENTY("70~79", (age) -> age < 80 && age >= 70),
        MORE_THAN_EIGHTY("80~", (age) -> age >= 80);

        private final String ageGroup;
        private final Function<Integer, Boolean> calculateAge;

        AgeGroup(String ageGroup, Function<Integer, Boolean> calculateAge) {
            this.ageGroup = ageGroup;
            this.calculateAge = calculateAge;
        }

        public static String getAgeGroup(int age) {
            return Arrays.stream(values())
                    .filter((value1) -> value1.calculateAge.apply(age))
                    .map((value2) -> value2.ageGroup)
                    .findFirst()
                    .orElse(MORE_THAN_EIGHTY.ageGroup);
        }
    }
}
