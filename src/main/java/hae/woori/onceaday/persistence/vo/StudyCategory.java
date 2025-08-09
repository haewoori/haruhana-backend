package hae.woori.onceaday.persistence.vo;

public enum StudyCategory {

	CERTIFICATE("자격증"),
	HOBBY("취미생활");

	private final String category;

	StudyCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}
}
