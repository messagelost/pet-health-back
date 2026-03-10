package com.jacob.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

@Slf4j
@Component
public class ImageUtils {

    /**

     * 剪裁成正方形

     */

    public static BufferedImage getSquare(BufferedImage bi) {

        int init_width = bi.getWidth();

        int init_height = bi.getHeight();

        if (init_width != init_height){

            int width_height = 0;

            int x = 0;

            int y = 0;

            if (init_width > init_height) {

                width_height = init_height;//原图是宽大于高的长方形

                x = (init_width-init_height)/2;

                y = 0;

            } else {

                width_height = init_width;//原图是高大于宽的长方形

                y = (init_height-init_width)/2;

                x = 0;

            }

            bi = bi.getSubimage(x, y, width_height, width_height);

        }

        return bi;

    }

}
