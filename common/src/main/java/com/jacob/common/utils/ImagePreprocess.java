package com.jacob.common.utils;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ImagePreprocess {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static String preprocess(String inputPath) {

        Mat img = Imgcodecs.imread(inputPath);

        // 灰度化
        Mat gray = new Mat();
        Imgproc.cvtColor(img, gray, Imgproc.COLOR_BGR2GRAY);

        // 二值化
        Mat binary = new Mat();
        Imgproc.threshold(gray, binary, 150, 255, Imgproc.THRESH_BINARY);

        String output = "processed.png";
        Imgcodecs.imwrite(output, binary);

        return output;
    }
}