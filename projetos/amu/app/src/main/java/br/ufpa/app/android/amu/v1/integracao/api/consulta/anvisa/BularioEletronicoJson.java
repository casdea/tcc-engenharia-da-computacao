package br.ufpa.app.android.amu.v1.integracao.api.consulta.anvisa;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BularioEletronicoJson {

	private List<BularioEletronicoConteudoJson> content;
	private int totalElements;
	private int totalPages;
	private boolean last;
	private int numberOfElements;
	private boolean first;
	private int size;
	private int number;

	public List<BularioEletronicoConteudoJson> getContent() {
		return content;
	}

	public void setContent(List<BularioEletronicoConteudoJson> content) {
		this.content = content;
	}

	public int getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@NonNull
	@Override
	public String toString() {
		return "BularioEletronicoJson [totalElements=" + totalElements + ", totalPages="
				+ totalPages + ", last=" + last + ", numberOfElements=" + numberOfElements + ", first=" + first
				+ ", size=" + size + ", number=" + number + "]";
	}

}
