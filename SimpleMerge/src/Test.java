
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("dfksajl;fd");
		boolean flag = false;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				System.out.println(i + " " + j);
				if (i == 5 && j == 2) {
					flag = true;
					break;
				}
			}
			if (flag)
				break;
		}
	}

}
