package com.jacob.web.system;

import com.alibaba.fastjson.JSONObject;
import com.jacob.common.model.base.ResponseVO;
import com.jacob.common.utils.ImageUtils;
import com.jacob.common.utils.OrcUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/common")
public class SysCommonController {

    private static final String UPLOAD_DIR = "D:/upload/";
    // 允许上传的图片格式
    private static final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp");
    private static final String TESSERACT_DIR = "D:/graduate_project/tesseract-main/tessdata";
    private static final String API_KEY = "tvhDL47Q6B5JxTF1RydN2R5g";
    private static final String SECRET_KEY = "8gIeWseokMHC8RMIFXMip9WWVHToN0vz";

    @PostMapping("/upload/file")
    public ResponseVO<String> uploadFile(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseVO.error("文件不能为空");
        }
        try {
            // 原始文件名
            String originalName = file.getOriginalFilename();
            // 获取后缀
            String suffix = originalName.substring(originalName.lastIndexOf("."));
            // 新文件名
            String fileName = System.currentTimeMillis() + suffix;
            // 存储路径
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 保存文件
            File dest = new File(UPLOAD_DIR + fileName);
            file.transferTo(dest);
            // 返回访问地址
            String url = "uploads/" + fileName;
            return ResponseVO.success(url);
        } catch (Exception e) {
            log.error("上传失败", e);
            return ResponseVO.error("上传失败");
        }
    }

    @PostMapping("/upload/squareImg")
    public ResponseVO<String> uploadSquareImg(@RequestParam("file") MultipartFile file) {
        // 校验文件是否为空
        if (file.isEmpty()) {
            return ResponseVO.error("文件不能为空");
        }

        try {
            // 获取文件名和后缀，校验图片格式
            String originalName = file.getOriginalFilename();
            if (originalName == null || originalName.lastIndexOf(".") == -1) {
                return ResponseVO.error("无效的图片文件，缺少后缀名");
            }
            // 获取小写后缀（兼容大小写）
            String suffix = originalName.substring(originalName.lastIndexOf(".") + 1).toLowerCase();
            if (!ALLOWED_IMAGE_TYPES.contains(suffix)) {
                return ResponseVO.error("仅支持上传jpg/jpeg/png/gif/bmp格式的图片");
            }

            // 生成新文件名，构建存储路径
            String fileName = System.currentTimeMillis() + "." + suffix;
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) {
                boolean mkdirSuccess = dir.mkdirs();
                if (!mkdirSuccess) {
                    log.error("创建上传目录失败：{}", UPLOAD_DIR);
                    return ResponseVO.error("创建上传目录失败，请检查目录权限");
                }
            }
            File destFile = new File(UPLOAD_DIR + fileName);

            // 读取图片并裁剪为正方形
            // 读取MultipartFile为BufferedImage
            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            if (originalImage == null) {
                return ResponseVO.error("无法读取图片文件，可能是损坏或非标准图片格式");
            }
            // 4.2 调用ImageUtils裁剪为正方形
            BufferedImage squareImage = ImageUtils.getSquare(originalImage);
            // 4.3 将裁剪后的图片写入目标文件
            ImageIO.write(squareImage, suffix, destFile);

            // 5. 构建访问URL并返回
            String url = "uploads/" + fileName;
            log.info("图片上传并裁剪成功，存储路径：{}，访问URL：{}", destFile.getAbsolutePath(), url);
            return ResponseVO.success(url);

        } catch (IOException e) {
            log.error("图片上传/裁剪失败", e);
            return ResponseVO.error("图片上传失败：IO异常，请检查文件格式或权限");
        } catch (Exception e) {
            log.error("图片上传未知异常", e);
            return ResponseVO.error("图片上传失败：" + e.getMessage());
        }

    }

    @PostMapping("/upload/ocr")
    public ResponseVO<JSONObject> ocrScan(@RequestParam("file") MultipartFile file) throws IOException {
        JSONObject jsonObject = OrcUtils.ocrRegularScan(file);
        return ResponseVO.success(jsonObject);
    }
}
