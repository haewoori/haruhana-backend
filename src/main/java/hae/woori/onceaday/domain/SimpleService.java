package hae.woori.onceaday.domain;

public interface SimpleService<I, O> {

	O run(I input);
}
