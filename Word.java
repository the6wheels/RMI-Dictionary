package test2;

import java.io.Serializable;
import java.util.Objects;

public class Word implements Serializable {
	private static final long serialVersionUID = 12351313553L;
	private String wword;
	private String defofword;

	public Word(String wword, String defofword) {
		this.wword = wword;
		this.defofword = defofword;
	}

	public String getwword() {
		return wword;
	}

	public void setwword(String wword) {
		this.wword = wword;
	}

	public String getdefofword() {
		return defofword;
	}

	public void setdefofword(String defofword) {
		this.defofword = defofword;
	}

	@Override
	public String toString() {
		return "Word{" + "wword='" + wword + '\'' + ", defofword='" + defofword + '\'' + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Word word = (Word) o;
		return Objects.equals(wword, word.wword) && Objects.equals(defofword, word.defofword);
	}

	@Override
	public int hashCode() {
		return Objects.hash(wword, defofword);
	}
}
