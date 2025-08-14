package hae.woori.onceaday.domain.study.vo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "스터디 카드 모집 상태 필터")
public enum AvailabilityFilter {

    ANY("전체"),
    OPEN("모집중"),
    CLOSED("모집완료");

    private final String label;

    AvailabilityFilter(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public boolean isAny()   { return this == ANY; }
    public boolean isOpen()  { return this == OPEN; }
    public boolean isClosed(){ return this == CLOSED; }
}
