package kkakka.mainservice.common.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NoOffsetPageInfo {

    private boolean isLastPage;
    private int pageSize;
    private int curSize;

    public static NoOffsetPageInfo from(boolean isLastPage, int pageSize, int curSize) {
        return new NoOffsetPageInfo(isLastPage, pageSize, curSize);
    }
}
