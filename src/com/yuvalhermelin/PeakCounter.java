package com.yuvalhermelin;

import java.util.Arrays;

public class PeakCounter {
	public int countPeaks() {
		// GyroTest2out.csv
		// 64StepsInHandJogging-out.csv
		// walkingSampleData-out.csv
		// 64StepsInPocketJogging-out.csv
		CSVData data = CSVData.readCSVFile("/Users/yuvalhermelin/Downloads/walkingSampleData-out.csv", 0);
		double[] gyro3D = new double[data.numberOfRows()];
		for (int i = 1; i < data.numberOfRows(); i++) {
			gyro3D[i] = Math.sqrt((data.getRow(i)[1] * data.getRow(i)[1]) + (data.getRow(i)[2] * data.getRow(i)[2])
					+ (data.getRow(i)[3] * data.getRow(i)[3]));
		}

		double threshold = getThreshold(gyro3D);
		System.out.println(threshold);

		int peaks = 0;

		double[] time = data.getColumn(0);
		System.out.println(Arrays.toString(time));
		double previousTime = 0;

		for (int i = 1; i < gyro3D.length - 1; i++)
			if (gyro3D[i] > gyro3D[i - 1] && gyro3D[i] > gyro3D[i + 1])
				if (gyro3D[i] > threshold && (previousTime + 300) < time[i]) {
					System.out.println(previousTime + " " + time[i]);
					previousTime = time[i];
					peaks++;
				}
		return peaks;
	}

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

	public double getAdaptiveThreshold(double[] data, int startingPoint) {
		double threshold = 0;
		double mean = 0;
		for (int i = startingPoint; i < startingPoint + 50; i++)
			mean += data[i];
		// Size of the window for the adaptive threshold
		mean /= 50;

		for (int i = startingPoint; i < startingPoint + 50; i++)
			threshold += (data[i] - mean) * (data[i] - mean);
		threshold /= 49;
		threshold = Math.sqrt(threshold);

		return (mean + .5 * threshold);
	}
}
