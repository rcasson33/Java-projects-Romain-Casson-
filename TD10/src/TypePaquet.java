public enum TypePaquet {
	__WRONG__(0), __HELLO__(3), __MESSAGE__(6), __CONNECT__(3), __ACCEPT__(3), __END__(3);

	public final int nFields;

	private TypePaquet(int n) {
		this.nFields = n;
	}

	public static TypePaquet typeOf(String word) {
		try {
			return TypePaquet.valueOf(word);
		} catch (RuntimeException e) {
			return __WRONG__;
		}
	}
}
