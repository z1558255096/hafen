package com.moil.hafen.common.utils.base64;

import com.moil.hafen.common.exception.FebsException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * Base64图片处理
 * @author chenlong
 *
 */
@SuppressWarnings("restriction")
public class BASE64ImageUtil {
    private static Logger logger = LoggerFactory.getLogger(BASE64ImageUtil.class);

    /**
     * TODO
     * 将BASE64格式图片转成 MultipartFile
     * @param base64 
     * @return
     * @throws FebsException
     */
    public static MultipartFile base64ToMultipart(String base64) throws FebsException {
        if (StringUtils.isBlank(base64)) {
            return null;
        }
        try {
            String[] baseStrs = base64.split(",");
            byte[] b = Base64.decode(baseStrs[1]);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            return new BASE64DecodedMultipartFile(b, baseStrs[0]);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new FebsException("文件转码失败");
        }

    }

}
