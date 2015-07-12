package luyentm.uet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class KeypointManage {
	ArrayList<KeyPoint> mKeyPoints = new ArrayList<KeyPoint>();
	ArrayList<Double> mInput = new ArrayList<Double>();
	ArrayList<ArrayList<Double>> bigArray = new ArrayList<ArrayList<Double>>();

	public KeypointManage(ArrayList<Double> inputSignal) {
		mInput = inputSignal;
		getAllKeypoints();
	}

	private void getAllKeypoints() {

		int signal_size = mInput.size();
		double x1 = 0; // tam thoi minh de size Window la 3 => 3 gia tri nay la
		// x1,x2,x3
		double x2 = 0; // sau mo rong ra thi no se la cac so khac tuy chon,
		// thuong la de 3-5
		double x3 = 0;
		// tao ra kernel dong cho no
		// check kernel
		if ((CF.SIZE_KERNEL % 2) == 0) {
			System.out.println("Kernel must be odd interge");
			return;
		}

		final double factor = Math.pow(2, 1d / 4); // hang so tang len zicma
		double zicma = 1.6d;

		double[] kernel = new double[CF.SIZE_KERNEL];
		// test thu viec tao ra mang kernel

		/**********************************************
		 * TAO RA CAC TIN HIEU SCALE TU TIN HIEU BAN DAU
		 * 
		 **********************************************/
		zicma /= factor;

		double sum = 0;
		for (int i = 0; i < CF.NUMBER_SCALE; i++) {
			// tao 1 array de chua SIGNAL moi
			ArrayList<Double> newArray = new ArrayList<Double>();
			// tinh toan mat na nhan cua SIGNAL
			zicma = zicma * factor;
			if (CF.OUT_TO_TERMINAL) {
				System.out.println("Zicma: " + zicma);
			}
			// zicma = 1;
			// khoi tao cho kernel
			// tinh tong cho viec chuan hoa
			double sumNomal = 0;
			for (int k = 0; k < CF.SIZE_KERNEL / 2; k++) {
				kernel[k] = kernel[CF.SIZE_KERNEL - k - 1] = (1 / (Math
						.sqrt(2 * 3.14d) * zicma))
						* Math.exp(-((CF.SIZE_KERNEL / 2 - k)
								* (CF.SIZE_KERNEL / 2 - k) / (2 * zicma * zicma)));
				sumNomal += 2 * kernel[k];
			}
			kernel[CF.SIZE_KERNEL / 2] = (1 / (Math.sqrt(2 * 3.14d) * zicma))
					* Math.exp(-(0 / (2 * zicma * zicma)));
			sumNomal += kernel[CF.SIZE_KERNEL / 2];
			// chuan hoa lai du lieu
			if (CF.NORMALIZE) {
				for (int k = 0; k < CF.SIZE_KERNEL; k++) {
					kernel[k] /= sumNomal;
				}
			}
			if (CF.OUT_TO_TERMINAL) {
				for (int ki = 0; ki < CF.SIZE_KERNEL; ki++) {
					System.out.println(kernel[ki] + " ");
				}

				System.out
						.println("\n***************************************************");
			}
			// x1 = (1 / (Math.sqrt(2 * 3.14d) * zicma))
			// * Math.exp(-(1 / (2 * zicma * zicma)));
			// x2 = (1 / (Math.sqrt(2 * 3.14d) * zicma))
			// * Math.exp(-(0 / (2d * zicma * zicma)));
			// x3 = (1 / (Math.sqrt(2 * 3.14d) * zicma))
			// * Math.exp(-(1 / (2d * zicma * zicma)));

			// x1 = (1 / (Math.sqrt(2 * 3.14f) * zicma))
			// * Math.pow(2.71, -(1 / (2f * zicma * zicma)));
			// x2 = (1 / (Math.sqrt(2 * 3.14f) * zicma))
			// * Math.pow(2.71, -(0 / (2f * zicma * zicma)));
			// x3 = (1 / (Math.sqrt(2 * 3.14f) * zicma))
			// * Math.pow(2.71, -(1 / (2f * zicma * zicma)));
			// double sumfactor = x1 + x2 + x3;
			// /******************************************
			// * CAN CHO VIEC CHUAN HOA THAM SO GAUSIAN chuan hoa lai (co tong
			// * bang 1) => tam thoi de la KHONG chuan hoa CAP NHAT : phai chuan
			// * hoa ==> neu ko chuan hoa thi so lieu luon giam ==> ko the tinh
			// * DOG duoc
			// ******************************************
			// */
			// if (CF.NORMALIZE) {
			// x1 = x1 / sumfactor;
			// x2 = x2 / sumfactor;
			// x3 = x3 / sumfactor;
			// sumfactor = x1 + x2 + x3;
			// }
			//
			// if (CF.OUT_TO_TERMINAL) {
			// System.out.println("Factor: x1 = " + x1 + ",\t x2= " + x2
			// + ",\t x3= " + x3 + ",\t zicma: " + zicma + "\t Sum : "
			// + sumfactor);
			// }
			// tao ra du lieu => new "blur" signal
			// TINH RIENG CHO GIA TRI O DAU TIEN VA CUOI CUNG
			// khong xu rieng cho vao cong thuc tong quat
			// /**********************************************************
			// * Can xu ly ngoai le la cac diem khi nhan chap bi thieu phan tu,
			// xu
			// * dung giai phap la
			// */
			// newArray.add(bigArray.get(0).get(0) * (x2 + x1)
			// + bigArray.get(0).get(1) * x3);

			// for (int j = 1; j < signal_size - 1; j++) {
			// newArray.add(bigArray.get(0).get(j - 1) * x1
			// + bigArray.get(0).get(j) * x2
			// + bigArray.get(0).get(j + 1) * x3);
			// }
			// van chay tu 0 binh thuong
			// cho nay can xu ly lai them cho truong hop dau ben con lai

			/********************
			 * Them cac dong code cho cac kich ban 1: lay doi xung qua 2 ben 2:
			 * cho ben trai bang 0 3: cho ben phai bang 0
			 * 
			 */
			for (int j = 0; j < signal_size; j++) {
				sum = 0;
				// tinh tong cho no
				// Truong hop 1: lay doi xung diem qua hai ben
				for (int k = 0; k < CF.SIZE_KERNEL; k++) {
					if (j - CF.SIZE_KERNEL / 2 + k < 0) {
						// xu ly cho ben trai
						sum += kernel[k]
								* mInput.get(j + CF.SIZE_KERNEL / 2 - k);
					} else if ((j + k - CF.SIZE_KERNEL / 2) < (signal_size)) {
						// xu ly ben phai
						sum += kernel[k]
								* mInput.get(j - CF.SIZE_KERNEL / 2 + k);
					} else {
						// lay diem 1 cach binh thuong
						sum += kernel[k]
								* mInput.get(j + CF.SIZE_KERNEL / 2 - k);
					}
				}
				newArray.add(sum);
			}
			// them vao big array
			bigArray.add(newArray);
		}
		/**
		 * TA DA CA CAC DU LIEU DUOC SCALE BAY GIO SE TINH RA HIEU CUA NO
		 */
		ArrayList<ArrayList<Double>> DogData = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < CF.NUMBER_SCALE - 1; i++) {
			// set hieu cua 1 thang de tao 2 DOG
			ArrayList<Double> dog = new ArrayList<Double>();
			for (int j = 0; j < signal_size; j++) {
				dog.add(bigArray.get(i).get(j) - bigArray.get(i + 1).get(j));
			}
			// them no vao du lieu moi
			DogData.add(dog);
		}
		/*******
		 * IN RA DE TEST THU XEM DU LIEU NHU THE NAO
		 */
		System.out
				.println("==================DU LIEU SIGNAL SCALE====================");
		if (false) {
			for (int i = 0; i < CF.NUMBER_SCALE; i++) {
				for (int j = 0; j < signal_size; j++) {
					System.out.print(bigArray.get(i).get(j) + "\t");
				}
				System.out.println("");
			}
		}
		/*
		 * IN DU LIEU CUA THANG DOG XEM THE NAO
		 */
		System.out
				.println("==================DU LIEU SIGNAL KHI TINH DOG====================");
		if (CF.OUT_TO_TERMINAL) {
			for (int i = 0; i < DogData.size(); i++) {

				for (int j = 0; j < DogData.get(i).size(); j++) {
					System.out.print(DogData.get(i).get(j) + "\t");
				}
				System.out.println("");
			}
		}

		// tinh KEYPOINTS

		for (int i = 1; i < DogData.size() - 1; i++) {
			for (int j = 1; j < DogData.get(i).size() - 1; j++) {
				double x = DogData.get(i).get(j);
				if (x > DogData.get(i - 1).get(j - 1)) {
					// co kha nang max
					if (x > DogData.get(i - 1).get(j)
							&& x > DogData.get(i - 1).get(j + 1)
							&& x > DogData.get(i).get(j - 1)
							&& x > DogData.get(i).get(j + 1)
							&& x > DogData.get(i + 1).get(j - 1)
							&& x > DogData.get(i + 1).get(j)
							&& x > DogData.get(i + 1).get(j + 1)) {
						// max
						// them no vao nhu la 1 keypoint
						mKeyPoints.add(new KeyPoint(i, j));
					}
				} else {
					// co khac nang min
					if (x < DogData.get(i - 1).get(j)
							&& x < DogData.get(i - 1).get(j + 1)
							&& x < DogData.get(i).get(j - 1)
							&& x < DogData.get(i).get(j + 1)
							&& x < DogData.get(i + 1).get(j - 1)
							&& x < DogData.get(i + 1).get(j)
							&& x < DogData.get(i + 1).get(j + 1)) {
						// max
						mKeyPoints.add(new KeyPoint(i, j));
					}
				}
			}
		}
		/*
		 * SORT theo keypoint
		 */
		Collections.sort(mKeyPoints, new Comparator<KeyPoint>() {

			@Override
			public int compare(KeyPoint o1, KeyPoint o2) {
				if (o1.getPosition() > o2.getPosition()) {
					return 1;
				} else {
					return -1;
				}
			}
		});
		if (CF.LOG_KEYPOINT) {
			for (KeyPoint kq : mKeyPoints) {
				System.out.println("Key Point detected ===> Scale: "
						+ kq.getScale() + ", position: " + kq.getPosition());
			}
		}

	}

	public ArrayList<KeyPoint> getKeypoints() {
		return mKeyPoints;
	}

}
