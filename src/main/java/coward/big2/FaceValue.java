package coward.big2;

public enum FaceValue {

	ACE__(1), //
	N2___(2), //
	N3___(3), //
	N4___(4), //
	N5___(5), //
	N6___(6), //
	N7___(7), //
	N8___(8), //
	N9___(9), //
	N10__(10), //
	JACK_(11), //
	QUEEN(12), //
	KING_(13), //
	;

	private int value;

	private FaceValue(int value) {
		this.value = value;
	}

	public static int big2rank(FaceValue faceValue) {
		switch (faceValue) {
		case N2___:
			return 15;
		case ACE__:
			return 14;
		default:
			return faceValue.value;
		}
	}

}
