package luyentm.uet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/*
 * Main Class of Detected keypoint and extract description for keypoint
 */

public class MainSIFT1D {
	//

	// luu lai cac gia tri cua cuc tri
	// 2 thang nay nen chuyen thanh 1 doi tuong rieng
	// public static ArrayList<Integer> extremas = new ArrayList<Integer>();
	// danh dau la max/min
	// public static ArrayList<Boolean> extremaMax = new ArrayList<Boolean>();
	// luu lai cac vecto cua cac cac cuc tri tuong ung voi vi tri cua cucu tri
	// public static ArrayList<ArrayList<Double>> arrayRatio = new
	// ArrayList<ArrayList<Double>>();

	public static void main(String[] args) {
		// // dem thoi gian xem mat bao lau
		long time = System.currentTimeMillis();
		// // load len danh sach file
		// load configs len
		LoadConfigs load = new LoadConfigs("configs");
		findFileAndGenDes("."); // duyet qua cac folder hien tai
		JOptionPane.showMessageDialog(null, "Hoan thanh trong: "
				+ (double) (System.currentTimeMillis() - time) / 1000);
	}

	// day la ham de qui de minh duyet qua tat ca cac folder
	public static void findFileAndGenDes(String name) {
		File folder = new File(name);
		File[] listFile = folder.listFiles();
		for (File file : listFile) {
			if (file.isDirectory()
					&& !file.getName().equals(CF.NAME_OUT_DIRECTORY)) {
				// tao file ra tuong ung trong kia
				new File("./" + CF.NAME_OUT_DIRECTORY + "/"
						+ file.getPath().substring(1, file.getPath().length()))
						.mkdirs();
				findFileAndGenDes(file.getPath());
			} else {
				// day la khi no khong phai la file nua, thi doc no thoi
				// cho nay chi dung cho minh thoi nen khong can xet nhieu lam gi
				// coi nhu trong moi folder nay chi toan co cac file du lieu
				// thoi
				findKeypoint(file.getPath());
			}
		}

	}

	public static void findKeypoint(String inputFileName) {

		// String inputFileName = "smaller_input.txt";
		// lay du lieu len
		LoadData1D gen = new LoadData1D(inputFileName);
		// nhet du lieu la du lieu dau vaof
		ArrayList<Double> inputSignal = gen.getData();
		// Lay ra toan bo cac diem cuc tri cua day ti hieu nay
		ExtremaManage extremaManage = new ExtremaManage(inputSignal);
		// lay ra tat ca cac diem keypoint
		KeypointManage keypointManage = new KeypointManage(inputSignal);
		// truyen vao day
		ArrayList<KeyPoint> mKeyPoints = keypointManage.getKeypoints();
		System.out.println("so luong keypoint: " + mKeyPoints.size());

		// ///////////////////////////////////////////////////////////////////////
		// bat dau gan descriptor cho keypoint

		for (int i = 0; i < mKeyPoints.size(); i++) {
			// tim 2 cuc dai ben trai
			int kq = 0;
			// luu lai cac gia tri nao nam xung quanh
			Integer[] listDes = new Integer[CF.N + 1];
			for (int l = mKeyPoints.get(i).getPosition(); l > 0
					&& (kq < CF.N / 2); l--) {
				int xpos = extremaManage.containsPosition(l); // no se tra ve vi
																// tri trong
																// list extrema
				if (xpos > 0) {
					if (CF.LOG_MINMAX) {
						System.out.println("Thay cuc tri ben trai : " + l
								+ " thuoc keypoint : "
								+ mKeyPoints.get(i).getPosition());
					}
					// luu lai gia tri do
					listDes[kq] = xpos;
					kq++;
				}
			}
			if (kq == (CF.N / 2)) {
				if (CF.LOG_KEYPOINT) {
					System.out.println("ben trai du min/max");
				}
			} else {
				if (CF.LOG_KEYPOINT) {
					System.out.println("ben trai khong du minmax");
				}
				continue;
			}
			// tim ben phai
			// tai sao cong them 1, de tranh bi trung duc tri o giua neu 1
			// keypoint = extrema
			for (int r = mKeyPoints.get(i).getPosition() + 1; r < inputSignal
					.size() && (kq < CF.N); r++) {

				int xpos = extremaManage.containsPosition(r);
				if (xpos > 0) {
					if (CF.LOG_MINMAX) {
						System.out.println("Thay cuc tri ben phai : " + r
								+ " thuoc keypoint : "
								+ mKeyPoints.get(i).getPosition());
					}
					// vi vi tri la duy nhat cho nen get the nay ko sai
					listDes[kq] = xpos;
					kq++;
				}
			}
			if (kq == CF.N) {
				if (CF.LOG_KEYPOINT) {
					System.out.println("ben phai du keypoint");
				}
			} else {
				if (CF.LOG_KEYPOINT) {
					System.out.println("ben phai khong du keypoint");
				}
				continue;
			}
			// check lai phat nua cho chac an
			if (kq == CF.N) {
				ArrayList<Double> descripton = new ArrayList<Double>();
				for (int j = 0; j < CF.N; j++) {
					ExtremaPoint e = extremaManage.getExtremaPoint(listDes[j]);
					if (e == null) {
						descripton.add(null);
					} else {
						for (int n = 0; n < CF.M; n++) {
							// System.out.println(listDes[j]);
							descripton.add(e.getRatios().get(n));
						}
					}
				}
				// set no cho keypoint
				mKeyPoints.get(i).setDescription(descripton);
			} else {
				// neu ko thi set cho no la null
				mKeyPoints.get(i).setDescription(null);
			}

		}
		/*
		 * IN RA CAC GIA TRI CUA KEYPOINT DESCRIPTOR
		 */
		for (int q = 0; q < mKeyPoints.size(); q++) {
			if (CF.LOG_DESCRIPTOR) {
				System.out.println("Des keypoint i= " + q + "| "
						+ mKeyPoints.get(q).descriptionToString());
			}
		}

		if (CF.UI_GRAPH) {
			GraphPanel p = new GraphPanel(extremaManage.getListEx(), mKeyPoints);
		}

		// doan nay se xuat ra file
		if (CF.OUT_TO_FILE) {
			PrintWriter outFile;
			try {
//				String nameOutFile = "./" + CF.NAME_OUT_DIRECTORY + "/"
//						+ inputFileName.substring(1, inputFileName.length());
				PrintWriter nameOutFile = new PrintWriter("./" + CF.NAME_OUT_DIRECTORY + "/" + 
				        inputFileName.substring(0, inputFileName.length()));
				outFile = new PrintWriter(nameOutFile);
				for (int q = 0; q < mKeyPoints.size(); q++) {
					String outDes = mKeyPoints.get(q).descriptionToString();
					if (!outDes.equals("null")) {
						outFile.print(mKeyPoints.get(q).descriptionToString());
						outFile.println("");
					}

				}
				outFile.close();
			} catch (FileNotFoundException e) {
				System.out.println("Tao file bi loi");
			}
		}

	}

}
