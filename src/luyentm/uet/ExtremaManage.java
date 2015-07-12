package luyentm.uet;

import java.util.ArrayList;

/*
 * Quan ly tat ca logic lien quan den mot tin hieu
 */
public class ExtremaManage {

	ArrayList<ExtremaPoint> extremaPointList = new ArrayList<ExtremaPoint>();
	ArrayList<Double> mInput = new ArrayList<Double>();

	/*
	 * Thong so dau vao la 1 signal thoi no se tu tinh ra cac diem cuc tri tu
	 * cac diem cuc tri ==> tinh ra cac ti le ben canh no luon
	 */
	public ExtremaManage(ArrayList<Double> inputSignal) {
		mInput = inputSignal;
		getExtremaPointList();
	}

	private void getExtremaPointList() {
		for (int i = CF.NUMBER_NEIGHBER; i < mInput.size() - CF.NUMBER_NEIGHBER; i++) {
			// tinh trai va tinh phai
			boolean max = true;
			boolean min = true;
			for (int r = 0; r < 20 && (max || min); r++) {
				if (mInput.get(i) > mInput.get(i - r)
						|| mInput.get(i) > mInput.get(i + r)) {
					min = false;

				}
				if (mInput.get(i) < mInput.get(i - r)
						|| mInput.get(i) < mInput.get(i + r)) {
					max = false;
				}
			}
			if (max) {
				if (CF.LOG_MINMAX) {
					System.out.println("max  :" + i);
				}
				// thay lai logic
				// cho no di tim luon cac vi tri xung quanh
				ArrayList<Double> r = getRatiosOfPoint(i, true);
				ExtremaPoint e = new ExtremaPoint(i, true, r);
				extremaPointList.add(e);
			}
			if (min) {
				ArrayList<Double> r = getRatiosOfPoint(i, false);
				ExtremaPoint e = new ExtremaPoint(i, false, r);
				extremaPointList.add(e);
				if (CF.LOG_MINMAX) {
					System.out.println("min  :" + i);
				}
			}
		}
		System.out.println("So cuc tri getExtremaPointList "
				+ extremaPointList.size());
	}

	private ArrayList<Double> getRatiosOfPoint(int i, boolean isMax) {
		ArrayList<Double> r = new ArrayList<Double>();

		double x0 = i;
		double y0 = mInput.get((int) x0);
		//
		ArrayList<Double> slopeInLeft = new ArrayList<Double>();
		ArrayList<Double> slopeInRight = new ArrayList<Double>();
		ArrayList<Double> ratio = new ArrayList<Double>();
		// lay ra M diem ben trai va M diem ban phai
		for (int m = 1; m <= CF.M; m++) {
			double x = (i - m);
			double y = mInput.get((int) x);
			// tinh ra do doc cua no
			// roi add no vao
			// neu no la Dinh thi lay duong, con lai lay am
			// do doc giua (x0,y0) va (x,y)

			double slope = Math.atan((y0 - y) / (x0 - x));
			slopeInLeft.add(slope);
			if (CF.LOG_SLOPE) {
				System.out.println("Do doc getExtremaPointList: " + slope);
			}

		}
		for (int m = 1; m <= CF.M; m++) {
			double x = (i + m);
			double y = mInput.get((int) x);
			// tinh ra do doc cua no
			// roi add no vao
			// neu no la Dinh thi lay duong, con lai lay am
			// do doc giua (x0,y0) va (x,y)

			double slope = Math.atan((y0 - y) / (x0 - x));
			slopeInRight.add(slope);
			if (CF.LOG_SLOPE) {
				System.out.println("Do doc getExtremaPointList : " + slope);
			}
		}
		for (int k = 0; k < CF.M; k++) {
			if (isMax) {
				ratio.add(Math.abs(slopeInRight.get(k) / slopeInLeft.get(k)));
			} else {
				ratio.add(slopeInRight.get(k) / slopeInLeft.get(k));
			}
			if (CF.LOG_SLOPE) {
				System.out.println("ti le di doc : " + k + " " + ratio.get(k));
			}
		}

		return ratio;
	}

	public int containsPosition(int l) {
		if (l > mInput.size() || l < 0) {
			return -1;
		}
		for (int i = 0; i < extremaPointList.size(); i++) {
			if (extremaPointList.get(i).getPosition() == l) {
				return i;
			}
		}
		return -1;
	}
	/*
	 * tra ve diem cuc tri theo position
	 */

	public ExtremaPoint getExtremaPoint(int pos) {
		if (pos < 0 || pos >= extremaPointList.size()) {
			return null;
		}
		return extremaPointList.get(pos);
	}
	
	public ArrayList<ExtremaPoint> getListEx(){
		return extremaPointList;
	}

}
