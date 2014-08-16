package com.codeaspect.puffer.enums;

import org.apache.commons.lang.StringUtils;

public enum Side {

	LEFT{
		@Override
		public String pad(String data, int len, char paddingCharacter) {
			return StringUtils.leftPad(data, len, paddingCharacter);
		}
		
	},
	RIGHT{
		@Override
		public String pad(String data, int len, char paddingCharacter) {
			return StringUtils.rightPad(data, len, paddingCharacter);
		}
		
	};
	
	public abstract String pad(String data, int len, char paddingCharacter);
}
