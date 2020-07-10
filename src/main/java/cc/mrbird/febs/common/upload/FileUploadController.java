package cc.mrbird.febs.common.upload;

import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 文件上传公共接口
 */
@Slf4j
@Validated
@Controller
@RequestMapping("/upload")
public class FileUploadController extends BaseController {

    @Value("${image.path.upload}")
    private String realpath;

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/file")
    @ResponseBody
    public FebsResponse fileUpload(@RequestParam("file") MultipartFile file) {
        if (file != null) {
            String fileName = file.getOriginalFilename();
            fileName = DateUtil.getDateFormat(new Date(), "yyyyMMdd") + File.separator + UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."));
            String storePath = realpath + File.separator + fileName;
            log.info("server store path={}", storePath);
            try {
                File fileDes = new File(storePath);
                if (!fileDes.getParentFile().exists()) {
                    fileDes.getParentFile().mkdirs();
                }
                file.transferTo(fileDes);
                return new FebsResponse().success().data("/uploadfile/"+fileName);
            } catch (Exception e) {
                System.out.println("文件上传失败");
                e.printStackTrace();
            }
        }
        return new FebsResponse().fail();
    }
}
