package luyentm.uet;

public class CF {
	// CF = configs globals variable

	// Ly thuyet la tuy chon Thuc the chi nen de 3 hay 5 ve do rong thoi, phai
	// la so le

	// DuÌ€ng cho debug ra thÃ´ng tin
	public static final boolean OUT_TO_FILE = true;
	public static final boolean OUT_TO_TERMINAL = false;
	public static final boolean LOG_KEYPOINT = false;
	public static final boolean LOG_MINMAX = false;
	public static final boolean LOG_SLOPE = false;

	public static final boolean LOG_DESCRIPTOR = false;
	public static final boolean UI_GRAPH = false;
	// Tham sÃ´Ì� : tÃªn file xuÃ¢Ì�t ra
	public static final String NAME_FILE_OUT = "infomations";
	// TuÌ€y choÌ£n : chuÃ¢Ì‰n hoÌ�a caÌ�c tham sÃ´Ì� cuÌ‰a ma trÃ¢Ì£n Gaussian Blur

	// tam thoi thi thay khi chuan hoa thi co it keypoint hon nhieu
	public static final boolean NORMALIZE = false;

	//
	// public static final String inputFileName = "input.txt";
	public static String inputFileName = "input.txt";
	public static int N = 4; // so luong thang cuc tri lay xung quanh, can
	public static int M = 4; // so luong diem lay xung quanh
	public static int NUMBER_NEIGHBER = 20;
	public static int NUMBER_SCALE = 6;
	public static int SIZE_KERNEL = 5; // => thuc nghiem cho thay 5 scale
	
	// minh dat ten moi cho cai folder ma minh muon tao
	public static String NAME_OUT_DIRECTORY = "descriptor";

	// la qua tuyet voi

	public static void setConfigs(int n, int m, int nn, int ns, int sk) {
		// neu khong set thi no se la mac dinh
		N = n;
		M = m;
		NUMBER_NEIGHBER = nn;
		NUMBER_SCALE = ns;
		SIZE_KERNEL = sk;
	}

}
