import java.io.*;

class Main {
	private static final int MAX = 200;
	private static int R = 5;

	public static final String BEHAVIOR_TYPE_CIRCLE = "circle";
	public static final String BEHAVIOR_TYPE_ELASTIC = "elastic";
	public static final String BEHAVIOR_TYPE_STUCK = "stuck";

	public static void main (String args[]) {
		if (args.length > 0) {
			String fileName = args[0];
			String behaviourType = BEHAVIOR_TYPE_STUCK;;
			int radius = R;
			if (args.length > 1) {
				behaviourType = args[1];	
			}

			if (args.length > 2) {
				radius = Integer.parseInt(args[2]);	
			}
			// System.out.println(fileName);

			try {
				// Assuming default encoding
				FileWriter fileWriter = new FileWriter(fileName);

				// Always wrap FileWriter in BufferredWriter
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

				// Write number of lines in the first line
				bufferedWriter.write(MAX + "\n");

				double x, z;
				for (int i = 0; i < MAX; i++) {
					switch (behaviourType) {
						case BEHAVIOR_TYPE_STUCK:
							x = 0;
							z = 0;

							break;

						case BEHAVIOR_TYPE_ELASTIC:
							x = radius * Math.sin(Math.PI * i / MAX);
							z = 0;

							break;

						case BEHAVIOR_TYPE_CIRCLE:
							x = radius * Math.sin(Math.PI * i / MAX);
							z = radius * Math.cos(Math.PI * i / MAX);;

							break;

						default:
							x = 0;
							z = 0;
					}

					bufferedWriter.write(String.valueOf(x) + "," + String.valueOf(z) + "\n");
				}

				bufferedWriter.close();

			} catch (IOException ex) {

			}

			return;
		} 
		
		System.out.println("Choose a file name!");
	}	
}