package luyentm.uet;

import java.util.ArrayList;

public class ExtremaPoint {
	//
	private int mPosition;// chua toa do cua no tren tin hieu goc
	private boolean mIsMax; // true la max, false la min
	private ArrayList<Double> mRatios = new ArrayList<Double>();// luu tru cac
																// ti le xung
																// quanh

	public ExtremaPoint(int pos, boolean isMax, ArrayList<Double> ratios) {
		mPosition = pos;
		mIsMax = isMax;
		mRatios = ratios;
	}

	public int getPosition() {
		return mPosition;
	}

	public boolean isMax() {
		return mIsMax;

	}

	public ArrayList<Double> getRatios() {
		return mRatios;
	}

}
