package com.dml.accounting;

/**
 * 文字描述的记账摘要。可能是一句话简单描述，也可能是一个复杂的json结构。
 * 
 * @author neo
 *
 */
public class TextAccountingSummary implements AccountingSummary {

	private String text;

	public TextAccountingSummary(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

}
