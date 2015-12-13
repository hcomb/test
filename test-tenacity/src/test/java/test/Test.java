package test;

public class Test {

	public static void main(String[] args) {
		AlwaysSucceed command = new AlwaysSucceed();
		String result = command.execute();
		System.out.println(result);
	}
}
