package luyentm.uet;

import java.util.ArrayList;

public class KeyPoint {
	private int mScale; // thuoc scale thu bao nhieu
	private int mPosition; // vi tri thu bao nhieu
	private ArrayList<Double> mDescripton;

	public KeyPoint(int scale, int position) {
		mScale = scale;
		mPosition = position;
	}

	public int getScale() {
		return mScale;
	}

	public int getPosition() {
		return mPosition;
	}

	public ArrayList<Double> getDescription() {
		return mDescripton;
	}

	public void setDescription(ArrayList<Double> des) {
		mDescripton = des;
	}

	public int getCount() {
		if (mDescripton == null) {
			return 0;
		} else {
			return mDescripton.size();

		}
	}

	public String descriptionToString() {
		String des = "";
		if (mDescripton == null) {
			return "null";
		}
		for (int i = 0; i < mDescripton.size(); i++) {
			if (mDescripton.get(i).toString().equals("NaN")) {
				des += 0.00 + "  ";
			} else if (mDescripton.get(i).toString().equals("Infinity")) {
				des += 1.00 + "  ";
			} else {
				des += mDescripton.get(i) + "  ";
			}
		}
		return des;
	}
}
