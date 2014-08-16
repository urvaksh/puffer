package com.codeaspect.puffer.enums;

public enum NumericSign {
	NONE(0) {

		@Override
		public String getNumericValue(String text) {
			return text;
		}

		@Override
		public boolean isNegitave(String text) {
			return false;
		}

		@Override
		public String getString(String absValue, boolean isNeg, Side side,
				char padChar, int length) {
			return absValue;
		}

	},
	
	DEFAULT(0){

		@Override
		public String getNumericValue(String text) {
			return text;
		}

		@Override
		public boolean isNegitave(String text) {
			return false;
		}

		@Override
		public String getString(String absValue, boolean isNeg, Side side,
				char padChar, int length) {
			if(isNeg){
				String paddedString = side.pad(absValue, length-1, padChar);
				return new StringBuffer("-").append(paddedString).toString();
			}else{
				return side.pad(absValue, length, padChar);
			}
		}
	},
	
	REQUIRED(1){

		@Override
		public String getNumericValue(String text) {
			return text.substring(1);
		}

		@Override
		public boolean isNegitave(String text) {
			return text.charAt(0)=='-';
		}

		@Override
		public String getString(String absValue, boolean isNeg, Side side,
				char padChar, int length) {
			return new StringBuilder(isNeg?"-":"+").append(side.pad(absValue, length-1, padChar)).toString();
		}
		
	},
	
	BRACKET_NOTATION(2){

		@Override
		public String getNumericValue(String text) {
			if(isNegitave(text)){
				return text.substring(1, text.length()-1);
			}else{
				return text;
			}
		}

		@Override
		public boolean isNegitave(String text) {
			return text.startsWith("[") && text.endsWith("]");
		}

		@Override
		public String getString(String absValue, boolean isNeg, Side side,
				char padChar, int length) {
			if(isNeg){
				return new StringBuilder("[")
						.append(side.pad(absValue, length - 2, padChar))
						.append("]").toString();
			}else{
				return side.pad(absValue, length, padChar);
			}
		}
		
	};

	private int numChars;

	private NumericSign(int numChars) {
		this.numChars = numChars;
	}

	public int getNumberOfCharacters() {
		return numChars;
	}

	public abstract String getNumericValue(String text);

	public abstract boolean isNegitave(String text);

	public abstract String getString(String absValue, boolean isNeg, Side side,
			char padChar, int length);

	public String getString(String numericValue, Side side, char padChar,
			int length) {
		boolean isNegitave = numericValue.startsWith("-");
		String absValue = isNegitave ? numericValue.substring(1) : numericValue;
		return getString(absValue,isNegitave, side, padChar, length);
	}
}
