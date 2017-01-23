public class Test {
	public static void main(String[] args) {
    	MiniPID controller = new MiniPID(0.01,0,0.01);
      	controller.setOutputLimits(1.0);
      	System.out.println(controller.getOutput(-170,-180));
    }
}