package cn.osc.controller;

import cn.osc.constants.Constants;
import cn.osc.core.RedisCache;
import cn.osc.result.Result;
import cn.osc.util.Base64;
import com.baomidou.mybatisplus.extension.api.R;
import com.google.code.kaptcha.Producer;

import io.swagger.annotations.Api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 验证码操作处理
 * 
 * 
 */
@Api(tags = "验证码")
@Slf4j
@CrossOrigin
@RestController
public class CaptchaController {
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisCache redisCache;
    // 验证码类型
    private String captchaType="math";

    /**
     * 生成验证码
     */
    //@ApiOperation("生成验证码")
    @GetMapping("/api/getCode")
    public Result getCode(HttpServletResponse response) throws IOException
    {
        // 保存验证码信息
        String uuid = UUID.randomUUID().toString();
        String verifyKey = Constants.CAPTCHA_CODE_KEY  + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        // 生成验证码
        if ("math".equals(captchaType))
        {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        }
        else if ("char".equals(captchaType))
        {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.SECONDS);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try
        {
            ImageIO.write(image, "jpg", os);
        }
        catch (IOException e)
        {
            return Result.error(e.getMessage());
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("uuid", uuid);
        map.put("img", Base64.encode(os.toByteArray()));
        Result result = Result.success("",map);
        log.info("验证码已发出");
        return result;
    }
}
