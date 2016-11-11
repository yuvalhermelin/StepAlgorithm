package com.yuvalhermelin;

public class PeakCounter {
	private static final double MIN_TIME_BETWEEN_PEAKS = 300;
	private static final String FILE_PATH = "/Users/yuvalhermelin/Downloads/testData/Neha/Free_Condition.csv";

	/***
	 * Count the number of steps in the data given by FILE_PATH
	 * 
	 * @return The number of steps
	 */
	public int countSteps() {
		final int ROWS_TO_SKIP = 0;
		CSVData data = CSVData.readCSVFile(FILE_PATH, ROWS_TO_SKIP);

		// This segment of the code gets only all the accelerometer data, from
		// the whole data file
		// and puts it into its own variable.
		double[] accelerometer3D = new double[data.numberOfRows()];
		for (int i = 1; i < data.numberOfRows(); i++) {
			accelerometer3D[i] = Math.sqrt((data.getRow(i)[1] * data.getRow(i)[1])
					+ (data.getRow(i)[2] * data.getRow(i)[2]) + (data.getRow(i)[3] * data.getRow(i)[3]));
		}

		double threshold = getThreshold(accelerometer3D);

		// Get the time corresponding with each accelerometer data input, from
		// the main data variable.
		double[] time = data.getColumn(0);

		int steps = countPeaks(time, accelerometer3D, threshold);

		return steps;
	}

	/***
	 * Count the number of peaks (point which is HIGHER than BOTH its
	 * neighbors).
	 * 
	 * @param time
	 *            The time corresponding to each value in the accelerometer data
	 * @param accelerometer3D
	 *            Accelerometer data (x,y, and z), put together into one vector
	 * @param threshold
	 *            The calculated threshold which peaks MUST be above
	 * @return The number of peaks found in accelerometer3D
	 */
	public int countPeaks(double[] time, double[] accelerometer3D, double threshold) {
		int peaks = 0;
		double previousTime = 0;

		// Count the number of peaks (point which is HIGHER than BOTH its
		// neighbors), and make
		// sure that every peak is at least MIN_TIME_BETWEEN_PEAKS away from its
		// neighboring peaks.
		for (int i = 1; i < accelerometer3D.length - 1; i++)
			if (accelerometer3D[i] > accelerometer3D[i - 1] && accelerometer3D[i] > accelerometer3D[i + 1]) {
				if (accelerometer3D[i] > threshold && (previousTime + MIN_TIME_BETWEEN_PEAKS) < time[i]) {
					previousTime = time[i];
					peaks++;
				}
			}
		return peaks;
	}

	/***
	 * Meant to get a threshold for the data as a whole, consisting of half the
	 * first derivitation above the mean. This value (of half the first
	 * derivitation) was decided upon experimentally.
	 * 
	 * @param data
	 *            The data to get the threshold of.
	 * @return Half the first derivitation, above the mean (threshold)
	 */
	public double getThreshold(double[] data) {
		double threshold = 0;
		double mean = 0;
		for (int i = 0; i < data.length; i++)
			mean += data[i];
		mean /= data.length;
		for (int i = 0; i < data.length; i++)
			threshold += (data[i] - mean) * (data[i] - mean);
		threshold /= data.length - 1;
		threshold = Math.sqrt(threshold);

		return (mean + .5 * threshold);
	}
}
